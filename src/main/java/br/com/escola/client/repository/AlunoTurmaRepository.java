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
    @Modifying
    @Query(
            value = "INSERT INTO ALUNO_TURMA (FK_MATRICULA,FK_CODIGO) " +
                    "VALUES (:matricula,:turma)",
            nativeQuery = true)
    void saveComposite(@Param("matricula") Long matricula, @Param("turma") Long codigo);

    @Transactional
    @Query(
            value = "SELECT aluno FROM AlunoTurma as a WHERE  a.turma.codigo  = :codigo")
    Optional<List<Aluno>> getAllAssignmentsOfStudentByClassCode(@Param("codigo") String codigo);


    @Transactional
    @Modifying
    @Query(
            value = "DELETE ALUNO_TURMA WHERE " +
                    "FK_CODIGO = :idTurma and " +
                    "FK_MATRICULA = :idAluno", nativeQuery = true)
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
            value = "SELECT * FROM ALUNO_TURMA WHERE FK_MATRICULA = :idAluno AND FK_CODIGO = :idTurma", nativeQuery = true)
    AlunoTurma findByIds(@Param("idAluno") Long idAluno, @Param("idTurma") Long idTurma);

    @Transactional
    @Query(
            value = "SELECT * FROM ALUNO_TURMA WHERE FK_MATRICULA = :idAluno", nativeQuery = true)
    List<AlunoTurma> getAllById(@Param("idAluno") Long idAluno);


    @Transactional
    @Modifying
    @Query(
            value = "UPDATE ALUNO_TURMA SET FK_CODIGO = :value WHERE " +
                    "FK_MATRICULA = :matricula AND FK_CODIGO = :codigo", nativeQuery = true)
    void modify(@Param("matricula") Long idAluno, @Param("codigo") Long idTurma, @Param("value") Long value);

    @Transactional
    @Modifying
    @Query(
            value = "DELETE ALUNO_TURMA WHERE ALUNO_TURMA.FK_MATRICULA = :matricula", nativeQuery = true)
    void deleteDependency(@Param("matricula") Long matricula);

}
