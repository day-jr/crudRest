package br.com.escola.client.repository;

import br.com.escola.client.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ProfessorRepository extends JpaRepository <Professor,Long>  {

    List<Professor> findByCpfContains(String cpf);
}


