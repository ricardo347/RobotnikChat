package br.com.robotnik.robotnikchat;

import java.util.Date;

public class Chat {
    private String mensagem;
    private Date data;
    private int sender; //0 para chatbot,  1 para participante
    private boolean satisfatória;

    public Chat (String mensagem, int sender, Date data){

        this.mensagem = mensagem;
        this.sender = sender;
        this.data = data;

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

    public boolean isSatisfatória() {
        return satisfatória;
    }

    public void setSatisfatória(boolean satisfatória) {
        this.satisfatória = satisfatória;
    }
}
