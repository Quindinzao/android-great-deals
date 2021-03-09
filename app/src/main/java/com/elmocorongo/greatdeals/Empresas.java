package com.elmocorongo.greatdeals;

public class Empresas {
    String nome, cnpj, email, tel, cel, senha1;

    public Empresas(String nome, String cnpj, String email, String tel, String cel, String senha1) {
        this.nome = nome;
        this.cnpj = cnpj;
        this.email = email;
        this.tel = tel;
        this.cel = cel;
        this.senha1 = senha1;
    }

    public Empresas(){}

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getCel() {
        return cel;
    }

    public void setCel(String cel) {
        this.cel = cel;
    }

    public String getSenha1() {
        return senha1;
    }

    public void setSenha1(String senha1) {
        this.senha1 = senha1;
    }
}
