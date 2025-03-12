package Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import Model.Allergia;

public interface AllergiaRepository extends MongoRepository<Allergia, String> {
}