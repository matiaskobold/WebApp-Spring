package com.springboot.webApp.apiREST;

import com.springboot.webApp.model.Clan;
import com.springboot.webApp.repository.ClanRepository;
import jdk.internal.util.Preconditions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class ClanControllerAPIREST {

    @Autowired
    private ClanRepository clanRepository;

    @RequestMapping("/test")
    public @ResponseBody String greeting() {
        return "Hello, World";
    }

    @GetMapping("/clans")
    public List<Clan> getAllClans(){
        return clanRepository.findAll();
    }

    @GetMapping("/clans/{id}")
    public ResponseEntity<Clan> getClanById(@PathVariable(value = "id") Long clanId)
            throws ResourceNotFoundException {
        Clan clan = clanRepository.findById(clanId)
                .orElseThrow(() -> new ResourceNotFoundException("Clan not found for this id :: " + clanId));
        return ResponseEntity.ok().body(clan);
    }

   /* @PostMapping("/clans")
    public Clan createClan(@Valid @RequestBody Clan clan)  {        //Con ResponseEntity puedo testear mas facil por el httpstatus y el body
            return clanRepository.save(clan);
    }
*/
    @PostMapping("/clans")
    public ResponseEntity<Clan> createClan(@Valid @RequestBody Clan clan)  {
            clanRepository.save(clan);
            return new ResponseEntity<>(clan, HttpStatus.CREATED);

    }

    @PutMapping("/clans/{clanId}")
    public ResponseEntity<Clan> updateClan(@PathVariable(value="clanId") Long clanId, @Valid @RequestBody Clan clanRequest) throws ResourceNotFoundException{
       Clan clan = clanRepository.findById(clanId)
               .orElseThrow(()->new ResourceNotFoundException("Clan not found for this ID:: "+clanId));
       clan.setName(clanRequest.getName());
       clan.setDescription(clanRequest.getDescription());
       clan.setLanguage(clanRequest.getLanguage());
       final Clan updatedClan = clanRepository.save(clan);
       return ResponseEntity.ok(updatedClan);
    }

    @DeleteMapping("/clans/{clanId}")
    public ResponseEntity<?> deleteClan(@PathVariable Long clanId){
        return clanRepository.findById(clanId).map(clan->{
            clanRepository.delete(clan);
            return ResponseEntity.ok().build();
        }).orElseThrow(()->new ResourceNotFoundException("Clan id "+clanId+" not found"));
    }
}
