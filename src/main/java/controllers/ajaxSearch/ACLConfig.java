package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.annotation.SecuredAnnotationSecurityMetadataSource;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.access.intercept.AfterInvocationManager;
import org.springframework.security.access.intercept.AfterInvocationProviderManager;
import org.springframework.security.access.intercept.aopalliance.MethodSecurityInterceptor;
import org.springframework.security.access.intercept.aspectj.AspectJMethodSecurityInterceptor;
import org.springframework.security.access.method.MethodSecurityMetadataSource;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.acls.AclEntryVoter;
import org.springframework.security.acls.AclPermissionCacheOptimizer;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.afterinvocation.AclEntryAfterInvocationCollectionFilteringProvider;
import org.springframework.security.acls.afterinvocation.AclEntryAfterInvocationProvider;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.AclService;
import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.DefaultFilterInvocationSecurityMetadataSource;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.security.acls.domain.BasePermission.READ;
import static org.springframework.security.acls.domain.BasePermission.WRITE;

@Configuration
public class ACLConfig {

    @Autowired
    DataSource dataSource;

    @Bean
    public EhCacheBasedAclCache aclCache() {
        return new EhCacheBasedAclCache(aclEhCacheFactoryBean().getObject(), permissionGrantingStrategy(), aclAuthorizationStrategy());
    }

    @Bean
    public EhCacheFactoryBean aclEhCacheFactoryBean() {
        EhCacheFactoryBean ehCacheFactoryBean = new EhCacheFactoryBean();
        ehCacheFactoryBean.setCacheName("aclCache");
//        ehCacheFactoryBean.setCacheManager(new net.sf.ehcache.CacheManager());
        ehCacheFactoryBean.setCacheManager(aclCacheManager());
        return ehCacheFactoryBean;
    }

    @Bean
    public net.sf.ehcache.CacheManager aclCacheManager() {
        return new net.sf.ehcache.CacheManager();
    }

    @Bean
    public PermissionGrantingStrategy permissionGrantingStrategy() {
        return new DefaultPermissionGrantingStrategy(new ConsoleAuditLogger());
    }

    @Bean
    public AclAuthorizationStrategy aclAuthorizationStrategy() {
        return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

    @Bean
    public MethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        AclPermissionEvaluator permissionEvaluator = new AclPermissionEvaluator(aclService());
        expressionHandler.setPermissionEvaluator(permissionEvaluator);
        expressionHandler.setPermissionCacheOptimizer(new AclPermissionCacheOptimizer(aclService()));
        return expressionHandler;
    }

    @Bean
    public LookupStrategy lookupStrategy() {
        return new BasicLookupStrategy(dataSource, aclCache(), aclAuthorizationStrategy(), new ConsoleAuditLogger());
    }

    @Bean
    public JdbcMutableAclService aclService() {
        return new JdbcMutableAclService(dataSource, lookupStrategy(), aclCache());
    }


    @Bean
    public MethodSecurityInterceptor methodSecurityInterceptor(){
        AspectJMethodSecurityInterceptor interceptor = new AspectJMethodSecurityInterceptor();
        interceptor.setAccessDecisionManager(accessDecisionManager());
        interceptor.setAfterInvocationManager(afterInvocationManager());
        interceptor.setSecurityMetadataSource(methodDefinitionSource());

        return interceptor;
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters
                = Arrays.asList(
                new RoleVoter(),
                readVoter()
                );
        return new UnanimousBased(decisionVoters);
    }

    @Bean
    public AfterInvocationManager afterInvocationManager(){
        AfterInvocationProviderManager manager =  new AfterInvocationProviderManager();
        manager.setProviders(Arrays.asList(afterAclRead(),afterAclCollectionRead()));
        return manager;
    }

    @Bean
    public AclEntryAfterInvocationCollectionFilteringProvider afterAclCollectionRead(){
        return new AclEntryAfterInvocationCollectionFilteringProvider(aclService(),Arrays.asList(READ,WRITE));

    }

    @Bean
    public AclEntryAfterInvocationProvider afterAclRead(){
        return new AclEntryAfterInvocationProvider(aclService(),Arrays.asList(READ,WRITE));

    }

    @Bean
    public MethodSecurityMetadataSource methodDefinitionSource(){
        return new SecuredAnnotationSecurityMetadataSource();
    }

    @Bean
    public AclEntryVoter readVoter(){
        return new AclEntryVoter(aclService(),
                "ACL_OBJECT_READ",
                new Permission[]{READ,WRITE}
                );
    }


}