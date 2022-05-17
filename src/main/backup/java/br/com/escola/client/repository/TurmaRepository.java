package br.com.escola.client.repository;

import br.com.escola.client.entity.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
}
