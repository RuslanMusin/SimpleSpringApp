package utils;

import org.apache.log4j.Logger;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


public class AclSecurityUtilImpl implements AclSecurityUtil {

    private static Logger logger = Logger.getLogger(AclSecurityUtil.class);

    private MutableAclService mutableAclService;

    public void setMutableAclService(MutableAclService mutableAclService) {
        this.mutableAclService = mutableAclService;
    }

    public void addPermission(AbstractSecureObject secureObject, Permission permission, Class clazz) {

        addPermission(secureObject, new PrincipalSid(getUsername()), permission, clazz);
    }

    public void addPermission(AbstractSecureObject securedObject, Sid recipient, Permission permission, Class clazz) {
        MutableAcl acl;
        ObjectIdentity oid = new ObjectIdentityImpl(clazz.getCanonicalName(), securedObject.getId());

        try {
            acl = (MutableAcl) mutableAclService.readAclById(oid);
        } catch (NotFoundException nfe) {
            acl = mutableAclService.createAcl(oid);
        }

        acl.insertAce(acl.getEntries().size(), permission, recipient, true);
        mutableAclService.updateAcl(acl);

        if (logger.isDebugEnabled()) {
            logger.debug("Added permission " + permission + " for Sid " + recipient + " securedObject " + securedObject);
        }
    }

    public void deletePermission(AbstractSecureObject securedObject, Sid recipient, Permission permission, Class clazz) {
        ObjectIdentity oid = new ObjectIdentityImpl(clazz.getCanonicalName(), securedObject.getId());
        MutableAcl acl = (MutableAcl) mutableAclService.readAclById(oid);

        // Remove all permissions associated with this particular recipient (string equality used to keep things simple)
        AccessControlEntry[] entries = acl.getEntries();

        for (int i = 0; i < entries.length; i++) {
            if (entries[i].getSid().equals(recipient) && entries[i].getPermission().equals(permission)) {
                acl.deleteAce(i);
            }
        }

        mutableAclService.updateAcl(acl);

        if (logger.isDebugEnabled()) {
            logger.debug("Deleted securedObject " + securedObject + " ACL permissions for recipient " + recipient);
        }
    }

    protected String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) auth.getPrincipal()).getUsername();
        } else {
            return auth.getPrincipal().toString();
        }
    }
}
