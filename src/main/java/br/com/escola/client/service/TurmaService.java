package br.com.escola.client.service;


import br.com.escola.client.dto.turma.TurmaDTO;
import br.com.escola.client.entity.AlunoTurma;
import br.com.escola.client.entity.ProfTurma;
import br.com.escola.client.entity.Turma;
import br.com.escola.client.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TurmaService {

    @Autowired
    public TurmaRepository turmaRepository;

    @Autowired
    public ProfTurmaRepository profTurmaRepository;

    @Autowired
    public AlunoTurmaRepository alunoTurmaRepository;


    //Search all classes assigned to a CPF
    public Optional<List<Turma>> allClassesAssignedToCpf(String cpf) {
        var turmaFound = profTurmaRepository.getClassesAssignedToCpf(cpf);

        if (turmaFound.isEmpty()) return Optional.empty();

        return turmaFound;
    }

    public Turma save(Turma turma) {
        turma.setId(null);
        return turmaRepository.save(turma);
    }



    public Optional<List<Turma>> getTurmaWhereStudentIsTaughtByProfessor(String matricula, String cpf) {

        var aluno = alunoTurmaRepository.getAllAlunoTurmaByMatricula(matricula);
        var professor = profTurmaRepository.getProfessorsAssignedByCpf(cpf);
        if (aluno.isEmpty() || professor.isEmpty()) return Optional.empty();


        List<String> codigoListAluno = new ArrayList<>();
        List<String> codigoListProf = new ArrayList<>();
        List<String> codigoTurmasInCommon = new ArrayList<>();

        //Take all class code from professor and throws to a list of codes
        for (ProfTurma prof : professor.get()) {
            codigoListProf.add(prof.getTurma().getCodigo());
        }

        //Take all class code from student and throws to a list of codes
        for (AlunoTurma al : aluno.get()) {
            codigoListAluno.add(al.getTurma().getCodigo());
        }

        //Compares both lists, if equals, throws to "turmasInCommon"
        for (String profCodes : codigoListProf) {

            if (codigoListAluno.contains(profCodes)) {
                codigoTurmasInCommon.add(profCodes);
            }
        }

        if (codigoTurmasInCommon.isEmpty()) {
            return Optional.empty();
        }

        //Get all turmaFK from list by its code and throws to a list of Entity (turmaFK)
        List<Turma> turmasInCommon = new ArrayList<>();
        for (String turma : codigoTurmasInCommon) {
            turmasInCommon.add(turmaRepository.findByCodigo(turma).get());
        }


        return Optional.of(turmasInCommon);
    }

    public Optional<List<Turma>> getTurmas() {
        return Optional.of(turmaRepository.findAll());
    }

    public Optional<Turma> findTurma(Long id) {
        return turmaRepository.findById(id);
    }

    public Optional<Long> findTurmaIdBycodigo(String codigo) {
        var turma = turmaRepository.findByCodigo(codigo);

        if (turma.isEmpty()) {
            return Optional.empty();
        }


        return Optional.ofNullable(turma.get().getId());
    }

    public void deleteTurmaById(Long id) {
        turmaRepository.deleteById(id);
    }

    public void deleteDependency(Long codigo) {
        turmaRepository.deleteDependencyAlunoTurma(codigo);
        turmaRepository.deleteDependencyProfturma(codigo);
    }

    public Optional<List<Turma>> filterClassesByFinishTime(Time finishTime) {
        return turmaRepository.limitByTime(finishTime);
    }

    @Autowired
    ModelMapper modelMapper;

    public Optional<Turma> update(TurmaDTO incomingBody, String codigo) {
        var optionalId = findTurmaIdBycodigo(codigo);
        if (optionalId.isEmpty()) {
            return Optional.empty();
        }
        var turmaDTO = findTurma(optionalId.get()).get();

        modelMapper.map(incomingBody,turmaDTO);

        return Optional.of(turmaRepository.save(turmaDTO));
    }
}
