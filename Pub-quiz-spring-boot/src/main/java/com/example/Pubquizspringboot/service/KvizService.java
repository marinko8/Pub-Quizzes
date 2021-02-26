package com.example.Pubquizspringboot.service;

import com.example.Pubquizspringboot.dto.*;
import com.example.Pubquizspringboot.exeptions.PubQuizExeption;
import com.example.Pubquizspringboot.mapper.OdgovorMapper;
import com.example.Pubquizspringboot.mapper.PitanjeMapper;
import com.example.Pubquizspringboot.model.*;
import com.example.Pubquizspringboot.repository.*;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class KvizService {

    @Autowired
    private KorisnikRepository korisnikRepository;
    @Autowired
    private KvizRepository kvizRepository;
    @Autowired
    private KategorijaRepository kategorijaRepository;
    @Autowired
    private RezultatRepository rezultatRepository;
    @Autowired
    private PitanjeRepository pitanjeRepository;
    @Autowired
    private OdgovorRepository odgovorRepository;

    @Autowired
    private final PitanjeMapper pitanjeMapper;
    @Autowired
    private final OdgovorMapper odgovorMapper;

    /**
     * Vraća kvizove po broju stranice.
     * @param brojStranice
     * @return
     */
    public PageResponse getKvizovePoStranici(Integer brojStranice) {
        Pageable page=PageRequest.of(brojStranice-1,5);
        return new PageResponse(kvizRepository.findAllByOrderByIdDesc(page).toList(),kvizRepository.count());
    }

    /**
     * Vraća listu s jednom stranicom kvizova za korisnika (ne dohvaćaju se već riješeni kvizovi) i po kategoriji.
     * @param brojStranice
     * @param username
     * @param idKategorije
     * @return
     */
    public PageResponse getKvizove(Integer brojStranice,String username,Integer idKategorije) {
        Pageable page=PageRequest.of(brojStranice-1,5);

        List<Integer> rijeseno=rezultatRepository.findAllByUsername(username)
                .stream()
                .map(k->k.getKviz().getId())
                .collect(Collectors.toList());

        Kategorija kategorija=kategorijaRepository
                .findById(idKategorije)
                .orElseThrow(()->new PubQuizExeption("kategorija nije pronadena"));

        if(rijeseno.size()==0){
            return new PageResponse(kvizRepository.findAllByKategorijaOrderByIdDesc(kategorija,page),
                    kvizRepository.countAllByKategorija(kategorija));
        }else{
            return new PageResponse(kvizRepository.findAllByIdNotInAndKategorijaOrderByIdDesc(rijeseno,kategorija,page),
                    kvizRepository.countAllByIdNotInAndKategorija(rijeseno,kategorija));
        }
    }

    /**
     * Vraća stranicu kvizova za korisnika (ne dohvaćaju se riješeni kvizovi) i ukupan broj kvizova.
     * @param brojStranice
     * @param username
     * @return
     */
    public PageResponse getKvizovePoStraniciKorisniku(Integer brojStranice, String username) {
        Pageable page=PageRequest.of(brojStranice-1,5);

        List<Integer> rijeseno=rezultatRepository.findAllByUsername(username)
                .stream()
                .map(k->k.getKviz().getId())
                .collect(Collectors.toList());
        if (rijeseno.size()==0){
            return new PageResponse(kvizRepository.findAllByOrderByIdDesc(page).toList(),kvizRepository.count());
        }else {
            return new PageResponse(kvizRepository.findAllByIdNotInOrderByIdDesc(rijeseno,page),
                    kvizRepository.countAllByIdNotIn(rijeseno));
        }
    }

    /**
     * Vraća stranicu kvizova određene kategorije i ukupan broj kvizova te kategorije.
     * @param brojStranice
     * @param idKategorije
     * @return
     */
    public PageResponse getKvizovePoStraniciKategoriji(Integer brojStranice,Integer idKategorije) {
        Kategorija kategorija=kategorijaRepository
                .findById(idKategorije)
                .orElseThrow(()->new PubQuizExeption("kategorija nije pronadena"));
        Pageable page=PageRequest.of(brojStranice-1,5);
        return new PageResponse(kvizRepository.findAllByKategorijaOrderByIdDesc(kategorija,page),
                kvizRepository.countAllByKategorija(kategorija));
    }

    /**
     * Vraća sve kategorije.
     * @return
     */
    public List<Kategorija> getKategorije(){
        return kategorijaRepository.findAll();
    }

    /**
     * Vraća sva pitanja za id kviza.
     * @param id
     * @return
     */
    public List<PitanjeOdgovorResponse> getPitanja(Integer id){
        Kviz kviz=kvizRepository.findById(id).orElseThrow(()->new PubQuizExeption("kviz nije pronaden"));
        List<Pitanje> listaPitanja;
        listaPitanja=pitanjeRepository.findAllByKviz(kviz);
        List<PitanjeOdgovorResponse> response=new ArrayList<>();
        for(Pitanje pitanje : listaPitanja){
            response.add(new PitanjeOdgovorResponse(pitanjeMapper.mapToDto(pitanje),getOdgovore(pitanje)));
        }
        return response;
    }

    /**
     * Vraća odgovore za id pitanja.
     * @param pitanje
     * @return
     */
    public List<OdgovorDto> getOdgovore(Pitanje pitanje){
        return odgovorRepository.findAllByPitanje(pitanje)
                .stream()
                .map(odgovorMapper::mapToDto)
                .collect(Collectors.toList());
    }

    /**
     * Vraća entitet kviza za id.
     * @param id
     * @return
     */
    public Kviz getKviz(Integer id){
        return kvizRepository.findById(id).orElseThrow(()->new PubQuizExeption("Kviz nije pronaden"));
    }

    /**
     * Vraća Podatke o korisniku za username.
     * @param username
     * @return
     */
    public PregledKorisnikaResponse getPregledKorisnika(String username){
        Korisnik korisnik=korisnikRepository
                .findByUsername(username)
                .orElseThrow(()->new PubQuizExeption("korisnik nije pronaden"));
        return PregledKorisnikaResponse
                .builder()
                .bodovi(korisnik.getBodovi())
                .brojOdigranihKvizova(rezultatRepository.countAllByUsername(username))
                .brojKreiranihKvizova(kvizRepository.countAllByAutor(username))
                .build();
    }

    /**
     * Vraća stranicu kvizova za username autora.
     * @param username
     * @param brojStranice
     * @return
     */
    public PageResponse getKvizoveAutora(String username, Integer brojStranice){
        Pageable page=PageRequest.of(brojStranice-1,5);
        return PageResponse.builder()
                .kvizovi(kvizRepository.findAllByAutorOrderByIdDesc(username,page))
                .ukupno(kvizRepository.countAllByAutor(username))
                .build();
    }

    /**
     * Prima zahtjev za kreirani kviz i sprema ih u bazu podataka.
     * @param createKvizRequest
     */
    public void createKviz(CreateKvizRequest createKvizRequest){
        Kategorija kategorija=kategorijaRepository
                .findByNaziv(createKvizRequest.getKategorija())
                .orElseThrow(()->new PubQuizExeption("kategorija nije pronadena"));
        Kviz spremljeniKviz=kvizRepository.save(
                Kviz.builder()
                        .autor(createKvizRequest.getUsername())
                        .naziv(createKvizRequest.getNaziv())
                        .broj_ocjena(2)
                        .zbroj_ocjena(5)
                        .broj_ocjena_tezine(2)
                        .zbroj_ocjena_tezine(5)
                        .kategorija(kategorija)
                        .broj_rjesenja(0)
                        .broj_pitanja(createKvizRequest.getPitanja().size())
                        .build());

        for(PitanjeRequest pitanje: createKvizRequest.getPitanja()){
            Pitanje spremljenoPitanje=pitanjeRepository.save(
                    Pitanje.builder()
                            .pitanje(pitanje.getNaziv())
                            .kviz(spremljeniKviz).build());
            for (OdgovorRequest odgovor: pitanje.getOdgovori()){
                odgovorRepository.save(
                        Odgovor.builder()
                                .odgovor(odgovor.getNaziv())
                                .pitanje(spremljenoPitanje)
                                .tocno(odgovor.getTocan())
                                .build());
            }
        }
    }

    /**
     * Prima rezultat kviza i sprema ga u bazu podataka.
     * @param rezultatRequest
     */
    public void spremiRezultat(RezultatRequest rezultatRequest){
        if (!rezultatRequest.getUsername().equals("neprijavljen")) {
            Korisnik korisnik=korisnikRepository
                    .findByUsername(rezultatRequest.getUsername())
                    .orElseThrow(()->new PubQuizExeption("korisnik nije pronaden"));
            korisnik.setBodovi(korisnik.getBodovi()+rezultatRequest.getBodovi());
            korisnikRepository.save(korisnik);
        }
        Kviz kviz=kvizRepository.getOne(rezultatRequest.getIdKviza());

        if(rezultatRequest.getOcjena()!=-1){
            kviz.setBroj_ocjena(kviz.getBroj_ocjena()+1);
            kviz.setZbroj_ocjena(kviz.getZbroj_ocjena()+rezultatRequest.getOcjena());
        }
        if(rezultatRequest.getTezina()!=-1){
            kviz.setBroj_ocjena_tezine(kviz.getBroj_ocjena_tezine()+1);
            kviz.setZbroj_ocjena_tezine(kviz.getZbroj_ocjena_tezine()+rezultatRequest.getTezina());
        }
        kviz.setBroj_rjesenja(kviz.getBroj_rjesenja()+1);
        kvizRepository.save(kviz);

        rezultatRepository.save(
                Rezultat.builder()
                        .username(rezultatRequest.getUsername())
                        .bodovi(rezultatRequest.getBodovi())
                        .kviz(kviz)
                        .build());

    }

    /**
     * Briše odgovore, pitanja i kviza za id kviza.
     * @param id
     */
    public void obrisiKviz(Integer id){
        Kviz kviz=kvizRepository
                .findById(id)
                .orElseThrow(()->new PubQuizExeption("kviz nije pronaden"));
        rezultatRepository.deleteAllByKviz(kviz);
        List<Pitanje> pitanja=pitanjeRepository.findAllByKviz(kviz);
        for(Pitanje pitanje : pitanja){
            odgovorRepository.deleteAllByPitanje(pitanje);
            pitanjeRepository.delete(pitanje);
        }
        kvizRepository.delete(kviz);
    }
}
