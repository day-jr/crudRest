package br.com.escola.client.service;


import br.com.escola.client.entity.Turma;
import br.com.escola.client.repository.TurmaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurmaService {

    @Autowired
    public TurmaRepository turmaRepository;

    public Turma save(Turma turma) {
        return turmaRepository.save(turma);
    }

    public List<Turma> getTurma() {
        return turmaRepository.findAll();
    }

    public Turma returnTurma(String codigo) {
        return turmaRepository.returnTurma(codigo);
    }

    public Optional<Turma> findTurma(Long id) {
        return turmaRepository.findById(id);
    }

    public Optional<Long> findTurmaBycodigo(String codigo) {
        return turmaRepository.findByCodigo(codigo);
    }

    public void deleteTurmaById(Long id) {
        turmaRepository.deleteById(id);
    }

    public void deleteDependency(Long codigo) {
        turmaRepository.deleteDependency(codigo);
    }
}
