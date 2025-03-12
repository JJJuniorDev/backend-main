package Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Model.Allergia;
import Repository.AllergiaRepository;

@RestController
@RequestMapping("/api/allergie")
public class AllergiaController {

    @Autowired
    private AllergiaRepository allergiaRepository;

    @PostMapping
    public Allergia aggiungiAllergia(@RequestBody Allergia allergia) {
        return allergiaRepository.save(allergia);
    }
}
