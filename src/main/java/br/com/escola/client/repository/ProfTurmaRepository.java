package br.com.escola.client.repository;

import br.com.escola.client.entity.ProfTurma;
import br.com.escola.client.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProfTurmaRepository extends JpaRepository <ProfTurma, Long> {

    @Query(
            value = "INSERT INTO P " +
                    " "
    )
    ProfTurma insertCpf(String cpf);
}
