    package com.example.FleetFlow.serviceInterfaces.services;

    import com.example.FleetFlow.DTO.RequestVehiculeDTO;
    import com.example.FleetFlow.DTO.ResponceVehiculeDTO;
    import com.example.FleetFlow.Mapper.VehiculeMapper;
    import com.example.FleetFlow.enums.VehiculeStatut;
    import com.example.FleetFlow.models.Chauffeur;
    import com.example.FleetFlow.models.Vehicule;
    import com.example.FleetFlow.repositories.ChauffeurRepository;
    import com.example.FleetFlow.repositories.VehculeRepository;
    import com.example.FleetFlow.serviceInterfaces.VehculeService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;
    import org.springframework.stereotype.Service;


    @Service
    @RequiredArgsConstructor
    public class VehiculeServiceImpl implements VehculeService {

       final private VehculeRepository vehculeRepository;
       final private VehiculeMapper vehiculeMapper;
       final private ChauffeurRepository chauffeurRepository;


        public ResponceVehiculeDTO ajouterVehicule(RequestVehiculeDTO dto){
            Vehicule vehicule = vehiculeMapper.toEntity(dto);
            vehicule.setStatut(VehiculeStatut.DISPONIBLE);
            Chauffeur chauffeur = chauffeurRepository.findById(dto.getChauffeurId())
                    .orElseThrow(()->new RuntimeException("Chauffeur not found with id: " + dto.getChauffeurId()));
            vehicule.setChauffeur(chauffeur);
            return vehiculeMapper.toDTO(vehculeRepository.save(vehicule));
        }

        public ResponceVehiculeDTO modifierVehicule(Long id , RequestVehiculeDTO dto){
            Vehicule vehicule = vehculeRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Vehicule not found with id: " + id));

            vehicule.setMatricule(dto.getMatricule());
            vehicule.setCapacite(dto.getCapacite());
            vehicule.setStatut(dto.getStatut());
            vehicule.setType(dto.getType());

            return vehiculeMapper.toDTO(vehculeRepository.save(vehicule)) ;
        }

        public boolean supprimzeVehicule(Long id){
                vehculeRepository.deleteById(id);
                return true;
        }
        public Page<ResponceVehiculeDTO> listerVehicule(Pageable pageable) {
            return vehculeRepository.findAll(pageable)
                    .map(vehiculeMapper::toDTO);
        }

    public Page<ResponceVehiculeDTO> findbystatut(VehiculeStatut statut,Pageable pageable){
            return vehculeRepository.findByStatut(statut,pageable)
                    .map(vehiculeMapper::toDTO);
    }

    public Page<ResponceVehiculeDTO>findgreteCapacitythan(int capacity, Pageable pageable){
            return vehculeRepository.findByCapaciteGreaterThan(capacity,pageable)
                                .map(vehiculeMapper::toDTO);

    }

    }
