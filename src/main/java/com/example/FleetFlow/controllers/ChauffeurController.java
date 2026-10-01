package com.example.FleetFlow.controllers;

import com.example.FleetFlow.DTO.ResponceChauffeurDTO;
import com.example.FleetFlow.DTO.RequestChauffeurDTO;

import com.example.FleetFlow.models.Chauffeur;
import com.example.FleetFlow.serviceInterfaces.ChauffeurService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/chauffeurs")
@RequiredArgsConstructor
public class ChauffeurController {

   final private ChauffeurService chauffeurService;

    @PostMapping("/ajouterChauffeur")
    public ResponseEntity<ResponceChauffeurDTO> saveChauffeur(@Valid @RequestBody RequestChauffeurDTO chauffeur){
       return  ResponseEntity.ok(chauffeurService.ajouterChauffeur(chauffeur));
    }

    @GetMapping("/afficherChauffeurs")
    public ResponseEntity<Page<ResponceChauffeurDTO>> displayChauffeurs(
            @RequestParam (defaultValue = "1") int pageNumber,
            @RequestParam (defaultValue = "5") int pagrSize,
            @RequestParam (defaultValue = "username") String sortBY,
            @RequestParam (defaultValue = "asc") String  sortDer
    ){
        Sort sort = sortDer.equalsIgnoreCase("asc") ? Sort.by(sortBY).ascending() : Sort.by(sortBY).descending();
        Page<ResponceChauffeurDTO> rs = chauffeurService.displayAllChauffeurs(PageRequest.of(pageNumber-1,pagrSize,sort));
        return ResponseEntity.ok(rs);
    }

    @DeleteMapping("/supprimerChauffeur/{id}")
    public boolean deleteChauffeur(@PathVariable  Long id){
        chauffeurService.deleteChauffeur(id);
        return true;
    }

    @PutMapping("/modifierChauffeur/{id}")
    public Chauffeur updateChauffeur(@PathVariable Long id,@RequestBody Chauffeur chauffeur){
        return chauffeurService.updateChauffeur(id,chauffeur);
    }

    @GetMapping("/afficherChaffeursDisponible")
    public ResponseEntity<Page<ResponceChauffeurDTO>> findByIsDisponible(
            @RequestParam (defaultValue = "1") int pageNumber,
            @RequestParam (defaultValue = "5") int pagrSize,
            @RequestParam (defaultValue = "username") String sortBY,
            @RequestParam (defaultValue = "asc") String  sortDer
    )
    {
        Sort sort = sortDer.equalsIgnoreCase("asc") ? Sort.by(sortBY).ascending() : Sort.by(sortBY).descending();
        Page<ResponceChauffeurDTO> rs = chauffeurService.findByDisponibility(PageRequest.of(pageNumber-1,pagrSize,sort));
        return ResponseEntity.ok(rs);
    }

    @GetMapping("/afficherChauffeursByPermis")
    public ResponseEntity<Page<ResponceChauffeurDTO>> displayChauffeurs(
            @RequestParam (defaultValue = "1") int pageNumber,
            @RequestParam (defaultValue = "5") int pagrSize,
            @RequestParam (defaultValue = "username") String sortBY,
            @RequestParam (defaultValue = "asc") String  sortDer,
            @RequestParam String permisType ,
            @RequestParam Boolean isDisponible
    )
    {
        Sort sort = sortDer.equalsIgnoreCase("asc") ? Sort.by(sortBY).ascending() : Sort.by(sortBY).descending();
        Page<ResponceChauffeurDTO> rs = chauffeurService.findByPermisTypeDisponible(permisType,isDisponible,PageRequest.of(pageNumber-1,pagrSize,sort));
        return ResponseEntity.ok(rs);
    }

    @GetMapping("/afficherChauffeurByNom")
    public ResponseEntity<Page<ResponceChauffeurDTO>> displayChauffeursByNom
            (       @RequestParam String nom,
                    @RequestParam (defaultValue = "1") int pageNumber,
                    @RequestParam (defaultValue = "5") int pagrSize,
                    @RequestParam (defaultValue = "username") String sortBY,
                    @RequestParam (defaultValue = "asc") String  sortDer
            ){
        Sort sort = sortDer.equalsIgnoreCase("asc") ? Sort.by(sortBY).ascending() : Sort.by(sortBY).descending();
        Page<ResponceChauffeurDTO> rs = chauffeurService.displayChauffeursByNom(nom,PageRequest.of(pageNumber-1,pagrSize,sort));
        return ResponseEntity.ok(rs);
    }

//    @GetMapping("/afficherToutlesChaufeurs")
//    public ResponseEntity<Page<ResponceChauffeurDTO>> getAllChauffeurPagination(Pageable pageable){
//        Page<ResponceChauffeurDTO> rs = chauffeurService.displayAllchauferusPagination(pageable);
//       return ResponseEntity.ok(rs);
//    }
}
