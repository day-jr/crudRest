package br.com.escola.client.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class Professor implements Serializable {
    @Id
    @Column(name = "PK_CPF")
    private  Long id;


    @Column(name = "NOME",nullable = true)
    private String nome;


    @Column(name = "EMAIL",nullable = true)
    private String email;





    @OneToMany(mappedBy = "cpf")
    @Column(name = "FK_TURMAS")
    private List<ProfTurma> turmas;
}
