package Repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import Model.Farmaco;
import Model.FarmacoInUso;

import java.util.List;

public interface FarmacoRepository extends MongoRepository<Farmaco, String> {


	Farmaco findByNomeFarmaco(String nomeFarmaco);
	
    // Metodo per cercare farmaci per nome (case-insensitive)
    List<Farmaco> findByNomeFarmacoContainingIgnoreCase(String nome);

    // Metodo per cercare farmaci per categoria
    Page<Farmaco> findByCategoria(String categoria, Pageable pageable);

}
