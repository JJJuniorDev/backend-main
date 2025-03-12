package Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import DTO.FarmacoDTO;
import Model.Farmaco;
import Repository.FarmacoRepository;
import Repository.FarmacoRepositoryImpl;

@RestController
@RequestMapping("/api/pazienti/farmaci")
public class FarmacoController {

	@Autowired
    private FarmacoRepository farmacoRepository;
	 @Autowired
	private FarmacoRepositoryImpl farmacoRepositoryImpl;
	 
	 @GetMapping("/{id}")
	 public ResponseEntity<FarmacoDTO> getFarmacoById(@PathVariable String id) {
	     Optional<Farmaco> farmaco = farmacoRepository.findById(id);
	     if (farmaco.isPresent()) {
	    	 FarmacoDTO farmacoDTO = new FarmacoDTO(farmaco.get());
	    	  return ResponseEntity.ok(farmacoDTO);
	    	  } else {
	         return ResponseEntity.notFound().build();
	     }
	 }
	 
	  // Metodo per cercare un farmaco per nome
    @GetMapping("/cerca/{nome}")
    public List<Farmaco> cercaFarmaco(@PathVariable String nome) {
        return farmacoRepository.findByNomeFarmacoContainingIgnoreCase(nome);
    }

    // Metodo per cercare farmaci per categoria
    @GetMapping("/categoria/{categoria}")
    public Map<String, Object> cercaFarmaciPerCategoria(@PathVariable String categoria,   @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "4") int elementiPerPagina) {
    	 // Stampa per debug
        System.out.println("Pagina corrente (richiesta): " + pagina);
        System.out.println("Elementi per pagina (richiesta): " + elementiPerPagina);
    	
    	// Creare un oggetto Pageable
        PageRequest pageable = PageRequest.of(pagina, elementiPerPagina);

        // Eseguire la query con paginazione
        Page<Farmaco> farmaciPage = farmacoRepository.findByCategoria(categoria, pageable);

        // Preparare la risposta
        Map<String, Object> risposta = new HashMap<>();
        risposta.put("farmaci", farmaciPage.getContent()); // Lista dei farmaci nella pagina corrente
        risposta.put("paginaCorrente", farmaciPage.getNumber());
        risposta.put("totaleElementi", farmaciPage.getTotalElements());
        risposta.put("totalePagine", farmaciPage.getTotalPages());

        System.out.println("Pagina corrente: " + pagina);
        System.out.println("Elementi per pagina: " + elementiPerPagina);
        return risposta;
    }

    // Metodo per ottenere tutte le categorie uniche dei farmaci
    @GetMapping("/categorie")
    public ResponseEntity<List<String>> ottieniCategorie() {
    	   List<String> categorie= farmacoRepositoryImpl.findDistinctCategoria();
        System.out.println(categorie); // Debug
        return ResponseEntity.ok(categorie);
    }
}
