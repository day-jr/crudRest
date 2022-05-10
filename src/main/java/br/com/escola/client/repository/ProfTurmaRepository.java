package br.com.escola.client.repository;

import br.com.escola.client.entity.ProfTurma;
import br.com.escola.client.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfTurmaRepository extends JpaRepository <ProfTurma, Long> {
}
