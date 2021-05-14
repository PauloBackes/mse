package com.unisinos.mse.service;

import com.unisinos.mse.model.Cirurgia;
import com.unisinos.mse.repository.CirurgiaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CirurgiaService {

    CirurgiaRepository cirurgiaRepository;

    public List<Cirurgia> buscarTodasCirurgias() {
        return cirurgiaRepository.findAll();
    }

    public Cirurgia buscarCirurgiaPeloId(String id) {
        return cirurgiaRepository.findCirurgiaById(id);
    }
}
