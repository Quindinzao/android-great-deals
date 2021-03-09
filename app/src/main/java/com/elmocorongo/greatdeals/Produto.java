package com.elmocorongo.greatdeals;

public class Produto {
    String titulo, subtitulo, tipo, descricao, preco;

    public Produto(String titulo, String subtitulo, String tipo, String descricao, String preco) {
        this.titulo = titulo;
        this.subtitulo = subtitulo;
        this.tipo = tipo;
        this.descricao = descricao;
        this.preco = preco;
    }

    public Produto() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getSubtitulo() {
        return subtitulo;
    }

    public void setSubtitulo(String subtitulo) {
        this.subtitulo = subtitulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getPreco() {
        return preco;
    }

    public void setPreco(String preco) {
        this.preco = preco;
    }
}
