    package com.example.FleetFlow.serviceInterfaces.services;

    import com.example.FleetFlow.DTO.ResponceClientDTO;
    import com.example.FleetFlow.DTO.RequestClientDTO;
    import com.example.FleetFlow.Mapper.ClientMapper;
    import com.example.FleetFlow.models.Client;
    import com.example.FleetFlow.repositories.ClientRepository;
    import com.example.FleetFlow.serviceInterfaces.ClientService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
    import org.springframework.stereotype.Service;

    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.Pageable;


    @Service
    @RequiredArgsConstructor
    public class ClientServiceImpl implements ClientService {

        private final ClientRepository clientRepository;
        private final ClientMapper mapper;
        private final BCryptPasswordEncoder passwordEncoder;

        public ResponceClientDTO ajouterClient(RequestClientDTO client){
            if(clientRepository.findByEmail(client.getEmail()).isPresent()){
                throw new RuntimeException("Email already exists!");
            }
            Client newClient = mapper.toEntity(client);

            newClient.setUsername(client.getUsername());
            newClient.setEmail(client.getEmail());
            newClient.setPhone(client.getPhone());
            newClient.setAge(client.getAge());
            newClient.setPassword(passwordEncoder.encode(client.getPassword()));


            return mapper.toDTO(clientRepository.save(newClient));
        }

        public boolean deleteClient(Long id){
            Client client = clientRepository.findById(id).orElseThrow(()-> new RuntimeException("Client not found with id :"+id));
            clientRepository.delete(client);
            return true;
        }


            public Page<ResponceClientDTO> afficherClients(Pageable pageable){
                return clientRepository.findAll(pageable)
                        .map(client -> {
                            ResponceClientDTO dto = mapper.toDTO(client);
                            dto.setNombreLivraison(client.getLivraisonList().size());
                            return dto;
                        });
                }


        public Client updateClient(Long id, Client newData){
            Client client = clientRepository.findById(id).orElseThrow(()-> new RuntimeException("Client not found with id :"+id));
                client.setUsername(newData.getUsername());
                client.setEmail(newData.getEmail());
                client.setPhone(newData.getPhone());
                return clientRepository.save(client);
        }
    }
