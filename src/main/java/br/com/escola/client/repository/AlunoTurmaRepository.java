package br.com.escola.client.repository;

import br.com.escola.client.entity.Aluno;
import br.com.escola.client.entity.AlunoTurma;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface AlunoTurmaRepository extends JpaRepository<AlunoTurma, Long> {

    @Transactional
    @Query(
            value = "SELECT aluno FROM AlunoTurma as a WHERE  a.turma.codigo  = :codigo")
    Optional<List<Aluno>> getAllStudentAssignmentsByClassCode(@Param("codigo") String codigo);

    @Transactional
    @Query(
            value = "SELECT a FROM AlunoTurma as a WHERE  a.turma.codigo  = :codigo")
    Optional<List<AlunoTurma>> getAllClassesAssignmentByClassCode(@Param("codigo") String codigo);


    @Transactional
    @Modifying
    @Query(
            value = "DELETE AlunoTurma WHERE " +
                    "turma.id = :idTurma and " +
                    "aluno.id = :idAluno")
    void deleteAlunoTurma(@Param("idAluno") Long idAluno, @Param("idTurma") Long idTurma);


    @Transactional
    @Query(
            value = "SELECT a FROM  AlunoTurma a WHERE a.aluno.matricula = :matricula ")
    Optional<List<AlunoTurma>> getAllAlunoTurmaByMatricula(@Param("matricula") String matricula);


    @Transactional
    @Query(
            value = "SELECT a FROM AlunoTurma as a WHERE a.aluno.matricula = :matricula AND a.turma.codigo = :codigo")
    AlunoTurma findAlunoTurmaByMatriculaCodigo(@Param("matricula") String matricula, @Param("codigo") String codigo);


    @Transactional
    @Query(
            value = "SELECT al FROM AlunoTurma as al WHERE " +
                    "al.aluno.id = :idAluno AND " +
                    "al.turma.id = :idTurma")
    AlunoTurma findByIds(@Param("idAluno") Long idAluno, @Param("idTurma") Long idTurma);

    @Transactional
    @Query(
            value = "SELECT al FROM AlunoTurma as al WHERE al.aluno.id = :idAluno")
    List<AlunoTurma> getAllById(@Param("idAluno") Long idAluno);


    @Transactional
    @Modifying
    @Query(
            value = "UPDATE AlunoTurma as al SET al.turma.id = :value WHERE " +
                    "al.aluno.id = :matricula AND al.turma.id = :codigo")
    void modify(@Param("matricula") Long idAluno, @Param("codigo") Long idTurma, @Param("value") Long value);

    @Transactional
    @Modifying
    @Query(
            value = "DELETE AlunoTurma WHERE aluno.id = :id")
    void deleteDependency(@Param("id") Long id);

}
