package br.com.escola.client.repository;

import br.com.escola.client.entity.ProfTurma;
import br.com.escola.client.entity.Professor;
import br.com.escola.client.entity.Turma;
import br.com.escola.client.entity.idClasses.ProfTurmaId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;



public interface ProfTurmaRepository extends JpaRepository <ProfTurma, Long> {
    @Query(
            value = "INSERT INTO PROF_TURMA (FK_CPF,FK_CODIGO) VALUES (:cpf,:codigo)",
            nativeQuery = true)
    void saveComposite(@Param("cpf") Professor cpf,@Param("codigo") Turma codigo);


}
