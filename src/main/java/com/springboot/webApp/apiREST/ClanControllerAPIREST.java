package com.springboot.webApp.apiREST;

import com.springboot.webApp.model.Clan;
import com.springboot.webApp.repository.ClanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ClanControllerAPIREST {

    @Autowired
    private ClanRepository clanRepository;

    @GetMapping("/clans")
    public List<Clan> getAllClans(){
        return clanRepository.findAll();
    }

    //POST http://localhost:8080/clans body {"description": "description1",
    //            "language": "language1",
    //            "country": "country1"}
    @PostMapping("/clans")
    public Clan createClan(@Valid @RequestBody Clan clan){
        return clanRepository.save(clan);
    }

    @PutMapping("/clans/{clanId}")
    public Clan updateClan(@PathVariable Long clanId, @Valid @RequestBody Clan clanRequest){
        return clanRepository.findById(clanId).map(clan -> {
            clan.setName(clanRequest.getName());
            clan.setDescription(clanRequest.getDescription());
            clan.setLanguage(clanRequest.getLanguage());
            return clanRepository.save(clan);
        }).orElseThrow(()->new ResourceNotFoundException("ClanId"+clanId+" not found"));
    }

    @DeleteMapping("/clans/{clanId}")
    public ResponseEntity<?> deleteClan(@PathVariable Long clanId){
        return clanRepository.findById(clanId).map(clan->{
            clanRepository.delete(clan);
            return ResponseEntity.ok().build();
        }).orElseThrow(()->new ResourceNotFoundException("Clan id"+clanId+" not found"));
    }
}
