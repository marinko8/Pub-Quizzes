package com.example.Pubquizspringboot.controller;

import com.example.Pubquizspringboot.dto.*;
import com.example.Pubquizspringboot.model.*;
import com.example.Pubquizspringboot.service.KvizService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import static org.springframework.http.ResponseEntity.status;

@RestController
@RequestMapping("/api/kviz")
@AllArgsConstructor
public class KvizController {
    private final KvizService kvizService;


    @GetMapping("/page/")
    public ResponseEntity<PageResponse> getKvizovePoStranici(
            @RequestParam Integer brojStranice,
            @RequestParam(required = false) Optional<String> username,
            @RequestParam(required = false) Optional<Integer> kategorija) {
        if (username.isPresent()&&kategorija.isPresent()){
            return status(HttpStatus.OK).body(kvizService.getKvizove(brojStranice,username.get(),kategorija.get()));
        }
        else if(username.isPresent()){
            return status(HttpStatus.OK).body(kvizService.getKvizovePoStraniciKorisniku(brojStranice,username.get()));
        } else if(kategorija.isPresent()){
            return status(HttpStatus.OK).body(kvizService.getKvizovePoStraniciKategoriji(brojStranice,kategorija.get()));
        }else{
            return status(HttpStatus.OK).body(kvizService.getKvizovePoStranici(brojStranice));
        }
    }

    @GetMapping("/kvizoviKorisnikaPage/")
    public  ResponseEntity<PageResponse> getKvizoveAutora(
            @RequestParam Integer brojStranice,
            @RequestParam String username) {
        return status(HttpStatus.OK).body(kvizService.getKvizoveAutora(username,brojStranice));
    }

    @GetMapping("/kategorije")
    public ResponseEntity<List<Kategorija>> getKategorije(){
        return status(HttpStatus.OK).body(kvizService.getKategorije());
    }

    @GetMapping("/pitanja/")
    public ResponseEntity<List<PitanjeOdgovorResponse>> getPitanja(@RequestParam Integer id){
        return status(HttpStatus.OK).body(kvizService.getPitanja(id));
    }

    @GetMapping
    public ResponseEntity<Kviz> getKviz(@RequestParam Integer id){
        return status(HttpStatus.OK).body(kvizService.getKviz(id));
    }

    @GetMapping("/korisnik/")
    public ResponseEntity<PregledKorisnikaResponse> getPregledKorisnika(@RequestParam String username){
        return status(HttpStatus.OK).body(kvizService.getPregledKorisnika(username));
    }

    @PostMapping("/noviKviz")
    public ResponseEntity<Void> createKviz(@RequestBody CreateKvizRequest createKvizRequest){
        kvizService.createKviz(createKvizRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PostMapping("/spremiRezultat")
    public ResponseEntity<Void> spremiRezultat(@RequestBody RezultatRequest rezultatRequest){
        kvizService.spremiRezultat(rezultatRequest);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/obrisiKviz/")
    public ResponseEntity<Void> obrisiKviz(@RequestParam Integer id){
        kvizService.obrisiKviz(id);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
