package br.com.escola.client.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;




@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Professor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "CPF", unique = true)
    private String cpf;


    @Column(name = "NOME",nullable = true)
    private String nome;


    @Column(name = "EMAIL",nullable = true)
    private String email;

///////////////VVVV FK KEY VVVV//////////////
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "cpf")
    @Column(name = "FK_TURMAS")
    private List<ProfTurma> turmas;


    //////////////////////////////////////
    public static Professor profConverter(Professor p){
        Professor professor = new Professor();
        professor.setCpf(p.getCpf());
        professor.setNome(p.getNome());
        professor.setEmail(p.getEmail());
        professor.setTurmas(p.getTurmas());
        return professor;
    }


    public static String profCpfToString(Professor p){

        return String.valueOf(p.getCpf());
    }


}
