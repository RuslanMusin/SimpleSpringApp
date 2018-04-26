package utils;

import database.entity.Book;
import database.entity.User;
import org.springframework.security.access.annotation.Secured;

import java.util.Collection;

public interface CustomerService {
    @Secured({"ROLE_CUSTOMER","AFTER_ACL_READ"})
    public User getCustomer(long id);

    @Secured({"ROLE_CUSTOMER","ACL_OBJECT_ADMIN"})
    public void modifyBankAccount(Book bankAccount);

    @Secured({"ROLE_CUSTOMER","AFTER_ACL_COLLECTION_READ"})
    public Collection getCustomerBankAccounts();
}