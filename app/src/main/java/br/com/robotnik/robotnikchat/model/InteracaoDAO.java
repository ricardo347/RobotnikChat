package br.com.robotnik.robotnikchat.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class InteracaoDAO {

    private Context context;

    public InteracaoDAO(Context contexto){
        this.context = contexto;
    }


    public void insereInteracao(Interacao interacao){
        ChatDbHelper dbHelper = new ChatDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.execSQL(ChatContract.insereInteracao(
                interacao.getIdsessao(),
                interacao.getPergunta(),
                interacao.getResposta(),
                interacao.getSatisfatoria(),
                interacao.getNumtentativa()));
        db.close();
        dbHelper.close();
        Log.v("insereInteracao", "Sessão inserida no banco com sucesso");
    }


}
