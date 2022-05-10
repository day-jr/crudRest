package br.com.escola.client.repository;

import br.com.escola.client.entity.Aluno;
import br.com.escola.client.entity.AlunoTurma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlunoTurmaRepository extends JpaRepository<AlunoTurma, Long> {
}
