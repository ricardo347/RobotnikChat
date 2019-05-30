package br.com.robotnik.robotnikchat.model;

public class Interacao {
    private Usuario usuario;
    private String pergunta;
    private String resposta;
    private int satisfatoria;
    private int numtentativa;
    private int idsessao;

    public Interacao(Usuario usuario,
                     int idsessao,
                     String pergunta,
                     String resposta,
                     int satisfatoria,
                     int numtentativa){
        this.idsessao = idsessao;
        this.usuario = usuario;
        this.pergunta = pergunta;
        this.resposta = resposta;
        this.satisfatoria = satisfatoria;
        this.numtentativa = numtentativa;

    }


    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public int getSatisfatoria() {
        return satisfatoria;
    }

    public void setSatisfatoria(int satisfatoria) {
        this.satisfatoria = satisfatoria;
    }

    public int getNumtentativa() {
        return numtentativa;
    }

    public void setNumtentativa(int numtentativa) {
        this.numtentativa = numtentativa;
    }

    public void setUsuario (Usuario usuario){
        this.usuario = usuario;
    }

    public Usuario getUsuario(){
        return this.usuario;
    }

    public int getIdsessao() {
        return idsessao;
    }

    public void setIdsessao(int idsessao) {
        this.idsessao = idsessao;
    }
}
