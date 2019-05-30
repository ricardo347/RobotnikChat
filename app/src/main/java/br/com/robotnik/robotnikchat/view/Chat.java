package br.com.robotnik.robotnikchat.view;

import java.util.Date;

public class Chat {
    private String mensagem;
    private Date data;
    private int sender; //0 para chatbot,  1 para participante
    private int satisfatória;
    private int tentativa;

    public Chat (String mensagem, int sender, Date data, int tentativa){

        this.mensagem = mensagem;
        this.sender = sender;
        this.data = data;
        this.tentativa = tentativa;

    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }

    public int getSatisfatória() {
        return satisfatória;
    }

    public void setSatisfatória(int satisfatória) {
        this.satisfatória = satisfatória;
    }

    public int getTentativa() {
        return tentativa;
    }

    public void setTentativa(int tentativa) {
        this.tentativa = tentativa;
    }
}
