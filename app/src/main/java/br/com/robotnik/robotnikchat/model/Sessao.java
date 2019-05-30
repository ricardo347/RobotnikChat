package br.com.robotnik.robotnikchat.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Sessao {
    private List<Interacao> interacoes;
    private Usuario usuario;
    private String inicio;
    private String fim;
    private int id;


    public Sessao(int id, Usuario usuario, String inicio, String fim){
        this.id = id;
        this.inicio = inicio;
        this.fim = fim;
        this.usuario = usuario;
        interacoes = new ArrayList<>();
    }


    public List<Interacao> getInteracoes() {
        return interacoes;
    }

    public void setInteracoes(List<Interacao> interacoes) {
        this.interacoes = interacoes;
    }

    public String getInicio() {
        return inicio;
    }

    public void setInicio(String inicio) {
        this.inicio = inicio;
    }

    public String getFim() {
        return fim;
    }

    public void setFim(String fim) {
        this.fim = fim;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
