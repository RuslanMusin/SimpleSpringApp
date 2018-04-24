package utils;

import database.entity.Book;
import database.entity.User;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Component
public class RepoSpecification {

    public Specification<Book> getBookSpecByName(final String bookName) {
        return (r, cq, cb) -> cb.like(cb.lower(r.get("name")), bookName.toLowerCase() +"%");
    }
}
