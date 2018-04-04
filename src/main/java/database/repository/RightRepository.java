package database.repository;

import database.entity.Right;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RightRepository extends CrudRepository<Right, Integer> {}
