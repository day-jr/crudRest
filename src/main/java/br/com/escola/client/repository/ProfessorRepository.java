package br.com.escola.client.repository;

import br.com.escola.client.entity.ProfTurma;
import br.com.escola.client.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;


public interface ProfessorRepository extends JpaRepository <Professor,Long>  {
    List<Professor> findByCpfContains(String cpf);


}


