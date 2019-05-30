package br.com.robotnik.robotnikchat.model;

import android.provider.BaseColumns;

import java.util.Date;

public class ChatContract {
    private ChatContract(){}

    public static class UsuarioContract implements BaseColumns {
        public static final String TABLE_NAME = "USUARIO";
        public static final String COLUMN_NAME_ID = "IDUSUARIO";
        public static final String COLUMN_NAME_NOME = "NOME";
        public static final String COLUMN_NAME_EMAIL = "EMAIL";
    }
    public static class SessaoContract implements BaseColumns{
        public static final String TABLE_NAME = "SESSAO";
        public static final String COLUMN_NAME_ID = "IDSESSAO";
        public static final String COLUMN_NAME_ID_USUARIO = "IDUSUARIO";
        public static final String COLUMN_NAME_DTINICIO = "DTINICIO";
        public static final String COLUMN_NAME_DTFIM = "DTFIM";

    }
    public static class InteracaoContract implements BaseColumns{
        public static final String TABLE_NAME = "INTERACAO";
        public static final String COLUMN_NAME_ID = "IDINTERACAO";
        public static final String COLUMN_NAME_ID_SESSAO = "IDSESSAO";
        public static final String COLUMN_NAME_PERGUNTA = "PERGUNTA";
        public static final String COLUMN_NAME_RESPOSTA = "RESPOSTA";
        public static final String COLUMN_NAME_SATISFATORIA = "SATISFATORIA";
        public static final String COLUMN_NAME_NUM_TENTATIVA = "NUMTENTATIVA";
    }

    public static String createTableUsuario(){
        return String.format("CREATE TABLE %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "%s VARCHAR(255)," +
                "%s VARCHAR(150))",
                UsuarioContract.TABLE_NAME,
                UsuarioContract.COLUMN_NAME_ID,
                UsuarioContract.COLUMN_NAME_NOME,
                UsuarioContract.COLUMN_NAME_EMAIL);
    }

    public static String createTableSessao(){
        return String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                "%s INTEGER," +
                "%s TEXT, " +
                "%s TEXT, FOREIGN KEY(%s) REFERENCES %s(%s))",
                SessaoContract.TABLE_NAME,
                SessaoContract.COLUMN_NAME_ID,
                SessaoContract.COLUMN_NAME_ID_USUARIO,
                SessaoContract.COLUMN_NAME_DTINICIO,
                SessaoContract.COLUMN_NAME_DTFIM,
                SessaoContract.COLUMN_NAME_ID_USUARIO,
                UsuarioContract.TABLE_NAME,
                UsuarioContract.COLUMN_NAME_ID
                ) ;
    }

    public static String createTableInteracao (){
        return String.format("CREATE TABLE %s (%s INTEGER PRIMARY KEY AUTOINCREMENT, " +
               "%s INTEGER," +
                "%s VARCHAR(1000)," +
                "%s VARCHAR(1000)," +
                "%s INTEGER," +
                "%s INTEGER, FOREIGN KEY(%s) REFERENCES %s(%s) )",
                InteracaoContract.TABLE_NAME,
                InteracaoContract.COLUMN_NAME_ID,
                InteracaoContract.COLUMN_NAME_ID_SESSAO,
                InteracaoContract.COLUMN_NAME_PERGUNTA,
                InteracaoContract.COLUMN_NAME_RESPOSTA,
                InteracaoContract.COLUMN_NAME_SATISFATORIA,
                InteracaoContract.COLUMN_NAME_NUM_TENTATIVA,
                InteracaoContract.COLUMN_NAME_ID_SESSAO,
                SessaoContract.TABLE_NAME,
                SessaoContract.COLUMN_NAME_ID
                );
    }


    public static String insereUsuario(String nome, String email){
        return String.format(
                "INSERT INTO %s (%s, %s) VALUES ('%s', '%s')",
                UsuarioContract.TABLE_NAME,
                UsuarioContract.COLUMN_NAME_NOME,
                UsuarioContract.COLUMN_NAME_EMAIL,
                nome,
                email
        );
    }
    public static String insereSessao(int idusuario, String inicio, String fim){
        return String.format(
                "INSERT INTO %s (%s,%s,%s) VALUES (%s,'%s', '%s')",
                SessaoContract.TABLE_NAME,
                SessaoContract.COLUMN_NAME_ID_USUARIO,
                SessaoContract.COLUMN_NAME_DTINICIO,
                SessaoContract.COLUMN_NAME_DTFIM,
                idusuario,
                inicio,
                fim);
    }

    public static String insereInteracao(int idSessao,
                                         String pergunta,
                                         String resposta,
                                         int satisfatoria,
                                         int numTentativa) {
        return String.format(
                "INSERT INTO %s (%s,%s,%s,%s,%S) VALUES (%s,'%s','%s',%s,%s)",
                InteracaoContract.TABLE_NAME,
                InteracaoContract.COLUMN_NAME_ID_SESSAO,
                InteracaoContract.COLUMN_NAME_PERGUNTA,
                InteracaoContract.COLUMN_NAME_RESPOSTA,
                InteracaoContract.COLUMN_NAME_SATISFATORIA,
                InteracaoContract.COLUMN_NAME_NUM_TENTATIVA,
                idSessao,
                pergunta,
                resposta,
                satisfatoria,
                numTentativa);
    }



}
