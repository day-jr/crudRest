package br.com.escola.client.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;



import javax.persistence.*;
import java.io.Serializable;

import java.util.Collections;
import java.util.List;



@AllArgsConstructor

@Builder

@Entity
public class Professor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "CPF", unique = true, nullable = false)
    private String cpf;


    @Column(name = "NOME",nullable = true)
    private String nome;


    @Column(name = "EMAIL",nullable = true)
    private String email;

///////////////VVVV FK KEY VVVV//////////////
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "professor",orphanRemoval = true)
    @Column(name = "FK_TURMAS")
    private List<ProfTurma> turmas;


    //////////////////////////////////////
    public static Professor profConverter(Professor p){
        Professor professor = new Professor();
        professor.setCpf(p.getCpf());
        professor.setNome(p.getNome());
        professor.setEmail(p.getEmail());
        return professor;
    }




    public Professor(){}



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Professor)) return false;

        Professor professor = (Professor) o;

        if (!id.equals(professor.id)) return false;
        if (!cpf.equals(professor.cpf)) return false;
        if (nome != null ? !nome.equals(professor.nome) : professor.nome != null) return false;
        if (email != null ? !email.equals(professor.email) : professor.email != null) return false;
        return turmas != null ? turmas.equals(professor.turmas) : professor.turmas == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + cpf.hashCode();
        result = 31 * result + (nome != null ? nome.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (turmas != null ? turmas.hashCode() : 0);
        return result;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public List<ProfTurma> getTurmas() {
        return turmas;
    }

    public void setTurmas(List<ProfTurma> turmas) {
        this.turmas = turmas;
    }
}
