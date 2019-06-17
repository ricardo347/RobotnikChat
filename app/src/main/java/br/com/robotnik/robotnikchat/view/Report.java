package br.com.robotnik.robotnikchat.view;

public class Report {
    private int sessao;
    private String usuario;
    private String inicio;
    private String fim;
    private String resposta;
    private String pergunta;
    private int resolvido;
    private int qtdTentativas;


    public Report(
            int sessao,
            String usuario,
            String inicio,
            String fim,
            String resposta,
            String pergunta,
            int qtdTentativas,
            int resolvido){

        this.sessao=sessao;
        this.usuario = usuario;
        this.inicio = inicio;
        this.fim = fim;
        this.resposta = resposta;
        this.pergunta = pergunta;
        this.qtdTentativas = qtdTentativas;
        this.resolvido = resolvido;

    }

    public int getSessao() {
        return sessao;
    }

    public void setSessao(int sessao) {
        this.sessao = sessao;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
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

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public int getResolvido() {
        return resolvido;
    }

    public void setResolvido(int resolvido) {
        this.resolvido = resolvido;
    }

    public int getQtdTentativas() {
        return qtdTentativas;
    }

    public void setQtdTentativas(int tentativas) {
        this.qtdTentativas = tentativas;
    }
}



