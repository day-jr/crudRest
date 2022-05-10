package br.com.escola.client.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Professor implements Serializable {
    @Id
    @Column(name = "CPF")
    private  Long id;

    @OneToMany(mappedBy = "id")
    private  List<ProfTurma> cpfTurma;

    @Column(name = "NOME")
    private String nome;


    @Column(name = "EMAIL")
    private String email;







}
