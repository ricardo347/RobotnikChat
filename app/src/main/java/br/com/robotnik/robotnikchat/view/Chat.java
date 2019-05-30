package br.com.robotnik.robotnikchat.view;

import java.util.Date;

public class Chat {
    private String mensagem;

    private int sender; //0 para chatbot,  1 para participante
    private int satisfatória;


    public Chat (String mensagem, int sender){

        this.mensagem = mensagem;
        this.sender = sender;

    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
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
}
