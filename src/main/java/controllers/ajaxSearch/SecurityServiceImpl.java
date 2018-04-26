package utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.Sid;

public class SecurityServiceImpl implements SecurityService {

    @Autowired
    @Qualifier("aclSecurity")
    private AclSecurityUtil aclSecurityUtil;

    public void setCustomerPermissions(Customer customer) {
        Sid sid = new PrincipalSid(customer.getUser().getUsername());
        Sid sidAdmin = new GrantedAuthoritySid("ROLE_CLERK");

        aclSecurityUtil.addPermission(customer, sid, BasePermission.ADMINISTRATION, Customer.class);
        aclSecurityUtil.addPermission(customer, sidAdmin, BasePermission.ADMINISTRATION, Customer.class);
    }
}
