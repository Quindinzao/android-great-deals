package com.elmocorongo.greatdeals;

public class Equipe {
    String nome, cpf, email, turno, salario, cargo;

    public Equipe(String nome, String cpf, String email, String turno, String salario, String cargo) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.turno = turno;
        this.salario = salario;
        this.cargo = cargo;
    }

    public Equipe(){}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTurno() {
        return turno;
    }

    public void setTurno(String turno) {
        this.turno = turno;
    }

    public String getSalario() {
        return salario;
    }

    public void setSalario(String salario) {
        this.salario = salario;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
}
