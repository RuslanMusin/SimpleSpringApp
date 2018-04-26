package utils;

import org.springframework.security.acls.model.Permission;
import org.springframework.security.acls.model.Sid;

public interface AclSecurityUtil {
    public void addPermission(AbstractSecureObject securedObject, Permission permission, Class clazz);
    public void addPermission(AbstractSecureObject securedObject, Sid recipient, Permission permission, Class clazz);
    public void deletePermission(AbstractSecureObject securedObject, Sid recipient, Permission permission);
}
