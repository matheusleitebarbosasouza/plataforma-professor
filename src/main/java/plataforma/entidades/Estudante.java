package br.edu.plataforma.entity;
import jakarta.persistence.*;
@Entity @Table(name="estudantes") public class Estudante {
@Id @GeneratedValue(strategy=GenerationType.IDENTITY) public Long id;
@Column(nullable=false) public String nome;
@Column(nullable=false,unique=true) public String matricula;
@Column(nullable=false) public String email;
}
