package br.com.robotnik.robotnikchat.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;


public class SessaoDAO {
    private Context context;

    public SessaoDAO(Context context){
        this.context = context;
    }

    public List<Sessao> buscaSessaoPorData(){
        List<Sessao> sessoes = new ArrayList<>();
        ChatDbHelper dbHelper = new ChatDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = String.format(
                "SELECT * FROM % ", ChatContract.SessaoContract.TABLE_NAME);

        Cursor cursor = db.rawQuery(sql,null);
        while(cursor.moveToNext()){
           sessoes.add(new Sessao(cursor.getInt(cursor.getColumnIndex(ChatContract.SessaoContract.COLUMN_NAME_ID)),
                                  new Usuario (cursor.getInt(cursor.getColumnIndex(ChatContract.SessaoContract.COLUMN_NAME_ID)),null, null),
                                  cursor.getString(cursor.getColumnIndex(ChatContract.SessaoContract.COLUMN_NAME_DTINICIO)),
                                  cursor.getString(cursor.getColumnIndex(ChatContract.SessaoContract.COLUMN_NAME_DTFIM))));
        }
        cursor.close();


        //busca de interações
        for(int i = 0;i <  sessoes.size(); i++){
            sql = String.format(
                    "SELECT %s.%s," +//idsessao
                            "%s.%s," +//usuarionome
                            "%s.%s" +//usuarioid
                            "%s.%s," +//inicio
                            "%s.%s," +//fim
                            "%s.%s," +//numinteracao
                            "%s.%s," +//satisfatoria
                            "%s.%s," +//pergunta
                            "%s.%s," +//resposta
                            "FROM %s " + //SESSAO
                            "INNER JOIN %s " + //INTERACAO
                            "ON %s.%s = %s.%s" +// ON INTERACAO.IDSESSAO = SESSAO.IDSESSAO
                            "INNER JOIN %s" + //USUARIO
                            "ON %s.%s = %s.%s" +//USUARIO.IDUSUARIO = SESSAO.IDUSUARIO
                            "WHERE %s.%s = %s ",//SESSAO.IDSESSAO = VALOR
                    ChatContract.SessaoContract.COLUMN_NAME_ID,
                    ChatContract.UsuarioContract.COLUMN_NAME_NOME,
                    ChatContract.UsuarioContract.COLUMN_NAME_NOME,
                    ChatContract.SessaoContract.COLUMN_NAME_DTINICIO,
                    ChatContract.SessaoContract.COLUMN_NAME_DTFIM,
                    ChatContract.InteracaoContract.COLUMN_NAME_NUM_TENTATIVA,
                    ChatContract.InteracaoContract.COLUMN_NAME_SATISFATORIA,
                    ChatContract.InteracaoContract.COLUMN_NAME_PERGUNTA,
                    ChatContract.InteracaoContract.COLUMN_NAME_RESPOSTA,
                    ChatContract.SessaoContract.TABLE_NAME,
                    ChatContract.InteracaoContract.TABLE_NAME,
                    ChatContract.InteracaoContract.TABLE_NAME,
                    ChatContract.InteracaoContract.COLUMN_NAME_ID_SESSAO,
                    ChatContract.SessaoContract.TABLE_NAME,
                    ChatContract.SessaoContract.COLUMN_NAME_ID,
                    ChatContract.UsuarioContract.TABLE_NAME,
                    ChatContract.UsuarioContract.TABLE_NAME,
                    ChatContract.UsuarioContract.COLUMN_NAME_ID,
                    ChatContract.SessaoContract.TABLE_NAME,
                    ChatContract.SessaoContract.COLUMN_NAME_ID_USUARIO,
                    ChatContract.SessaoContract.TABLE_NAME,
                    ChatContract.SessaoContract.COLUMN_NAME_ID,
                    sessoes.get(i).getId());

            Cursor c = db.rawQuery(sql,null);
            sessoes.get(i).getInteracoes().add(
                    new Interacao(
                            new Usuario(
                                    c.getInt(c.getColumnIndex(ChatContract.UsuarioContract.COLUMN_NAME_ID)),
                                    c.getString(c.getColumnIndex(ChatContract.UsuarioContract.COLUMN_NAME_NOME)),
                                    c.getString(c.getColumnIndex(ChatContract.UsuarioContract.COLUMN_NAME_EMAIL))
                            ),
                            c.getString(c.getColumnIndex(ChatContract.InteracaoContract.COLUMN_NAME_PERGUNTA)),
                            c.getString(c.getColumnIndex(ChatContract.InteracaoContract.COLUMN_NAME_RESPOSTA)),
                            c.getInt(c.getColumnIndex(ChatContract.InteracaoContract.COLUMN_NAME_SATISFATORIA)),
                            c.getInt(c.getColumnIndex(ChatContract.InteracaoContract.COLUMN_NAME_NUM_TENTATIVA))
                    )
            );
            c.close();
        }
        //db.close();
        // dbHelper.close();
        return sessoes;
    }

    public Sessao buscaSessao(){

        ChatDbHelper dbHelper = new ChatDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = String.format(
                "SELECT %s.%s," +//idsessao
                        "%s.%s," +//usuario
                        "%s.%s," +//inicio
                        "%s.%s," +//fim
                        "%s.%s," +//numinteracao
                        "%s.%s," +//satisfatoria
                        "%s.%s," +//pergunta
                        "%s.%s," +//resposta
                        "FROM %s " +
                        "INNER JOIN %s " +
                        "ON %s.%s = %s.%s" +
                        "INNER JOIN %s" +
                        "ON %s.%s = %s.%s" +
                        "WHERE %s.%s BETWEEN %s AND $s ", ChatContract.InteracaoContract.TABLE_NAME);

        Cursor cursor = db.rawQuery(sql,null);


               return null;
    }

    public List<Interacao> buscaSessaoAgrupadaPorTentativa(){
        return null;
    }

    public void insereSessao(Sessao sessao){
        ChatDbHelper dbHelper = new ChatDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.execSQL(ChatContract.insereSessao(sessao.getUsuario().getId(),sessao.getInicio(), sessao.getFim() ));
        db.close();
        dbHelper.close();
    }




}