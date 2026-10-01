package com.example.FleetFlow.serviceInterfaces.services;


import com.example.FleetFlow.DTO.ResponceChauffeurDTO;
import com.example.FleetFlow.DTO.RequestChauffeurDTO;
import com.example.FleetFlow.Mapper.ChauffeurMapper;
import com.example.FleetFlow.enums.Role;
import com.example.FleetFlow.models.Chauffeur;
import com.example.FleetFlow.repositories.ChauffeurRepository;
import com.example.FleetFlow.serviceInterfaces.ChauffeurService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
@RequiredArgsConstructor
public class ChauffeurServiceImpl implements ChauffeurService {
    private final ChauffeurMapper mapper;
    private final ChauffeurRepository chauffeurRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public ResponceChauffeurDTO ajouterChauffeur(RequestChauffeurDTO chauffeur){
        if(chauffeurRepository.findByEmail(chauffeur.getEmail()).isPresent()){
            throw new RuntimeException("Email already exists!");
        }
        Chauffeur chauffeur1 = mapper.toEntity(chauffeur);

        chauffeur1.setUsername(chauffeur.getUsername());
        chauffeur1.setEmail(chauffeur.getEmail());
        chauffeur1.setPassword(passwordEncoder.encode(chauffeur.getPassword()));
        chauffeur1.setRole(Role.CHAUFFEUR);
        chauffeur1.setIsDisponible(true);
        ResponceChauffeurDTO dto = mapper.toDTO(chauffeurRepository.save(chauffeur1));
        dto.setUsername(chauffeur.getUsername());
        return dto;
    }

    public boolean deleteChauffeur(Long id){
        Chauffeur chauffeur = chauffeurRepository.findById(id).orElseThrow(()-> new RuntimeException("Chauffeur not found with id :"+id));
        chauffeurRepository.delete(chauffeur);
        return true;
    }

    public Page<ResponceChauffeurDTO> displayAllChauffeurs(Pageable pageable){
        return chauffeurRepository.findAll(pageable)
                .map(chauffeur -> {
                    ResponceChauffeurDTO dto = mapper.toDTO(chauffeur);
                    dto.setNombreVehicules(chauffeur.getVichelList().size());
                    dto.setNombreLivraisons(chauffeur.getLivraisonList().size());
                    return dto;
                        }
                );
    }

    public Chauffeur updateChauffeur (Long id, Chauffeur newData){
        Chauffeur chauffeur = chauffeurRepository.findById(id).orElseThrow(()-> new RuntimeException("Chauffeur not found with id :"+id));
            chauffeur.setUsername(newData.getUsername());
            chauffeur.setPhone(newData.getPhone());
            chauffeur.setIsDisponible(newData.getIsDisponible());
            chauffeur.setPermisType(newData.getPermisType());
            return chauffeurRepository.save(chauffeur);
    }

    public Page<ResponceChauffeurDTO> findByDisponibility(Pageable pageable){
        return chauffeurRepository.findByIsDisponibleTrue(pageable)
                .map(mapper::toDTO);
    }

    public Page<ResponceChauffeurDTO> findByPermisTypeDisponible(String permisType, Boolean isDisponible,Pageable pageable){
     return chauffeurRepository.findByPermisTypeAndIsDisponible(permisType,isDisponible,pageable)
             .map(mapper::toDTO);
    }

    public Page<ResponceChauffeurDTO> displayChauffeursByNom(String nom, Pageable pageable){
        return  chauffeurRepository.findByUsername(nom,pageable)
                .map(mapper::toDTO);

    }

//    @Override
//    public Page<ResponceChauffeurDTO> displayAllchauferusPagination(Pageable pageable) {
//            return chauffeurRepository.findAll(pageable)
//                    .map(mapper::toDTO);
//    }


}