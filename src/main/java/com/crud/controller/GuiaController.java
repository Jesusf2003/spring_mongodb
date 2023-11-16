package com.crud.controller;

import com.crud.entity.Guia;
import com.crud.repository.GuiasRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class GuiaController {

    @Autowired
    private GuiasRepository guiasRepository;

    @GetMapping("/guias")
    public ResponseEntity<List<Guia>> findAllGuias(@RequestParam(required = false) String title) {
        try {
            List<Guia> guias = new ArrayList<>();
            if (title == null) {
                guiasRepository.findAll().forEach(guias::add);
            } else {
                guiasRepository.findByTitleContaining(title).forEach(guias::add);
            }
            if (guias.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(guias, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/guias/{id}")
    public ResponseEntity<Guia> findGuiaById(@PathVariable("id") String id) {
        Optional<Guia> data = guiasRepository.findById(id);
        if (data.isPresent()) {
            return new ResponseEntity<>(data.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/guias/published")
    public ResponseEntity<List<Guia>> findByPublished() {
        try {
            List<Guia> data = guiasRepository.findByPublished(true);
            if (data.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(data, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/guias")
    public ResponseEntity<Guia> create(@RequestBody Guia data) {
        try {
            Guia _data = guiasRepository.save(data);
            return new ResponseEntity<>(_data, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/guias/{id}")
    public ResponseEntity<Guia> updateGuia(@PathVariable("id") String id, @RequestBody Guia data) {
        Optional<Guia> opt = guiasRepository.findById(id);
        if (opt.isPresent()) {
            Guia _data = opt.get();
            _data.setTitle(data.getTitle());
            _data.setDescription(data.getDescription());
            _data.setPublished(data.isPublished());
            return new ResponseEntity<>(guiasRepository.save(_data), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/guias/{id}")
    public ResponseEntity<HttpStatus> deleteGuia(@PathVariable("id") String id) {
        try {
            guiasRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/guias")
    public ResponseEntity<HttpStatus> deleteAllGuias() {
        try {
            guiasRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}