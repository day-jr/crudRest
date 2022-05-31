package br.com.escola.client.service;


import br.com.escola.client.dto.professor.ProfessorDTO;
import br.com.escola.client.entity.Professor;
import br.com.escola.client.repository.ProfTurmaRepository;
import br.com.escola.client.repository.ProfessorRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    public ProfessorRepository professorRepository;

    @Autowired
    public ProfTurmaRepository profTurmaRepository;

    public Professor save(Professor professor) {

        return professorRepository.save(professor);
    }

    public List<Professor> getAllProfessores() {
        return professorRepository.findAll();
    }


    public void deleteProfessorByCpf(String cpf) {
        professorRepository.deleteByCpf(cpf);
    }


    public Optional<Professor> findByCpf(String cpf) {

        return professorRepository.findByCpf(cpf);
    }

    public void deleteDependency(Long cpf) {
        profTurmaRepository.deleteDependency(cpf);
    }


    @Autowired
    ModelMapper modelMapper;

    public Optional<Professor> update(ProfessorDTO incomingBody, String cpf) {
        var optionalProfessor = findByCpf(cpf);

        if (optionalProfessor.isEmpty()) {
            return Optional.empty();
        }

        var professorDTO = optionalProfessor.get();
        modelMapper.map(incomingBody,professorDTO);
        return Optional.of(professorRepository.save(professorDTO));
    }

}
