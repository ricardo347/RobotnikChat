package br.com.robotnik.robotnikchat.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class UsuarioDAO {

    private Context context;

    public UsuarioDAO(Context context){
        this.context = context;
    }

    public void insereUsuario(Usuario usuario){
        ChatDbHelper dbHelper = new ChatDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        db.execSQL(ChatContract.insereUsuario(usuario.getNome(), usuario.getEmail()));
        db.close();
        dbHelper.close();
    }

}
