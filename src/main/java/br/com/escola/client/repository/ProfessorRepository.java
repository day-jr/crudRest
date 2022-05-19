package br.com.escola.client.repository;

import br.com.escola.client.entity.Professor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


public interface ProfessorRepository extends JpaRepository<Professor, Long> {
    @Transactional
    @Query(
            value = "SELECT p FROM Professor p WHERE p.cpf = :cpf ")
    Optional<Professor> findByCpf(@Param("cpf") String cpf);


    @Transactional
    @Query(
            value = "SELECT p FROM Professor as p")
    List<Professor> findAllProf();

    @Transactional
    @Modifying
    @Query(
            value = "DELETE Professor WHERE cpf = :cpf")
    void deleteByCpf(@Param("cpf") String cpf);




}


