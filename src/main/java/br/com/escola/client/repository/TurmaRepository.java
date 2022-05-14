package br.com.escola.client.repository;

import br.com.escola.client.entity.AlunoTurma;
import br.com.escola.client.entity.Turma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;

public interface TurmaRepository extends JpaRepository<Turma,Long> {
    @Transactional
    @Query(
            value = "SELECT id FROM Turma as t WHERE t.codigo = :codigo")
    Long findByCodigo(@Param("codigo") String codigo);
}
