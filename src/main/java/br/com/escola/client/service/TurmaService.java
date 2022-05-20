package br.com.escola.client.service;


import br.com.escola.client.entity.AlunoTurma;
import br.com.escola.client.entity.Turma;
import br.com.escola.client.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TurmaService {

    @Autowired
    public TurmaRepository turmaRepository;

    @Autowired
    public ProfessorRepository professorRepository;

    @Autowired
    public AlunoTurmaRepository alunoTurmaRepository;

    @Autowired
    public ProfTurmaRepository profTurmaRepository;


    //Search all classes assigned to a CPF
    public Optional<List<Turma>> allClassesAssignedToCpf(Optional<String> cpf) {
        if(cpf.isEmpty()) return null;

        var turmaFound =  profTurmaRepository.getClassesAssignedToCpf(cpf.get());

        return turmaFound;
    }

    public Turma save(Turma turma) {
        return turmaRepository.save(turma);
    }


    public Optional<List<Turma>> getTurmas() {
        return Optional.of(turmaRepository.findAll());
    }

    public Optional<Turma> findTurma(Long id) {
        return turmaRepository.findById(id);
    }

    public Optional<Long> findTurmaIdBycodigo(String codigo) {
        var turmaId = turmaRepository.findByCodigo(codigo).get().getId();
        return Optional.ofNullable(turmaId);
    }

    public void deleteTurmaById(Long id) {
        turmaRepository.deleteById(id);
    }

    public void deleteDependency(Long codigo) {
        turmaRepository.deleteDependency(codigo);
    }
}
