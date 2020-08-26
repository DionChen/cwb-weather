package webapp.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import webapp.entities.Weather;

@Repository
public interface PersonRepository extends JpaRepository<Weather,Integer> {

}
