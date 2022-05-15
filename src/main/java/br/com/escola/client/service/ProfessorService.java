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

    public Professor save(Professor professor){
        return professorRepository.save(professor);
    }

    public List<Professor> getProfessor(){
        return professorRepository.findAllProf();
    }



    public void deleteProfessorByCpf(String cpf){
        professorRepository.deleteByCpf(cpf);
    }


    public Professor findByCpf(String cpf) {

         return professorRepository.findByCpf(cpf);
    }

    public void deleteDependency(Long cpf){
        professorRepository.deleteDependency(cpf);
    }









}
