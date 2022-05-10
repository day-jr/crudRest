package br.com.escola.client.service;


import br.com.escola.client.entity.Professor;
import br.com.escola.client.repository.ProfessorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfessorService {

    @Autowired
    public ProfessorRepository professorRepository;

    public Professor Save(Professor professor){
        return professorRepository.save(professor);
    }

    public List<Professor> GetProfessor(){
        return professorRepository.findAll();
    }


    public Optional<Professor> FindProfessor(Long id){

        return professorRepository.findById(id);
    }


    public void DeleteProfessorById(Long id){
        professorRepository.deleteById(id);
    }


    public List<Professor> findByCpfContains(String cpf) {

        return professorRepository.findByCpfContains(cpf);
    }
}
