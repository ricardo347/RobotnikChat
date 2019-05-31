package br.com.robotnik.robotnikchat.model;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.SimpleTimeZone;

public class Sessao {
    private List<Interacao> interacoes;
    private Usuario usuario;
    private String inicio;
    private String fim;
    private int id;
    private boolean fechada;


    public Sessao(int id, Usuario usuario, String inicio, String fim){
        this.id = id;
        this.usuario = usuario;
        interacoes = new ArrayList<>();
        fechada = false;


        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        try {
            Date datei = sdf.parse(inicio);
            Date datef = sdf.parse(fim);
            this.inicio = sdf.format(datei);
            this.fim = sdf.format(datef);

        } catch (ParseException e) {
            e.printStackTrace();
        }


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

    public void setInicio(Timestamp inicio) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.inicio = sdf.format(inicio);
    }

    public String getFim() {
        return fim;
    }

    public void setFim(Timestamp fim) {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        this.fim = sdf.format(fim);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isFechada() {
        return fechada;
    }

    public void setFechada(boolean fechada) {
        this.fechada = fechada;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Interacao getInteracaoResolvida(){

        for(Interacao i : this.interacoes){
            if(i.getSatisfatoria() == 1)
                return i;
        }
        return null;
    }

}
