package br.com.escola.client.repository;

import br.com.escola.client.entity.AlunoTurma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface AlunoTurmaRepository extends JpaRepository<AlunoTurma, Long> {
    @Transactional
    @Modifying
    @Query(
            value = "INSERT INTO ALUNO_TURMA (FK_MATRICULA,FK_CODIGO) " +
                    "VALUES (:matricula,:turma)",
            nativeQuery = true)
    void saveComposite(@Param("matricula") Long matricula, @Param("turma") Long codigo);
}
