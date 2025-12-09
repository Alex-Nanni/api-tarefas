package com.ru5055380.api_tarefas.controller;

import com.ru5055380.api_tarefas.model.Tarefa;
import com.ru5055380.api_tarefas.repository.TarefaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tarefas")
@CrossOrigin(origins = "*")
public class TarefaController {
    
    @Autowired
    private TarefaRepository tarefaRepository;
    
    // Criar nova tarefa (POST)
    @PostMapping
    public ResponseEntity<Tarefa> criarTarefa(@RequestBody Tarefa tarefa) {
        try {            
            Tarefa novaTarefa = tarefaRepository.save(tarefa);
            return new ResponseEntity<>(novaTarefa, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Listar todas as tarefas (GET)
    @GetMapping
    public ResponseEntity<List<Tarefa>> listarTodasTarefas() {
        try {
            List<Tarefa> tarefas = tarefaRepository.findAll();
            
            if (tarefas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            
            return new ResponseEntity<>(tarefas, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Buscar tarefa por ID (GET)
    @GetMapping("/{id}")
    public ResponseEntity<Tarefa> buscarTarefaPorId(@PathVariable("id") Long id) {
        Optional<Tarefa> tarefaData = tarefaRepository.findById(id);
        
        if (tarefaData.isPresent()) {
            return new ResponseEntity<>(tarefaData.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Atualizar tarefa (PUT)
    @PutMapping("/{id}")
    public ResponseEntity<Tarefa> atualizarTarefa(@PathVariable("id") Long id, @RequestBody Tarefa tarefa) {
        Optional<Tarefa> tarefaData = tarefaRepository.findById(id);
        
        if (tarefaData.isPresent()) {
            Tarefa tarefaExistente = tarefaData.get();
            tarefaExistente.setNome(tarefa.getNome());
            tarefaExistente.setDataEntrega(tarefa.getDataEntrega());
            tarefaExistente.setResponsavel(tarefa.getResponsavel());
            
            return new ResponseEntity<>(tarefaRepository.save(tarefaExistente), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    // Deletar tarefa (DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deletarTarefa(@PathVariable("id") Long id) {
        try {
            tarefaRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    // Endpoint para criar sua tarefa específica
    @PostMapping("/minha-tarefa")
    public ResponseEntity<Tarefa> criarMinhaTarefa() {
        Tarefa minhaTarefa = new Tarefa();
        minhaTarefa.setNome("Implementar API RESTful para gerenciamento de tarefas");
        minhaTarefa.setDataEntrega(LocalDate.now().plusDays(1));
        minhaTarefa.setResponsavel("Alex Nogueira Nanni - RU: 5055380");
        
        Tarefa tarefaSalva = tarefaRepository.save(minhaTarefa);
        return new ResponseEntity<>(tarefaSalva, HttpStatus.CREATED);
    }
}