package br.com.robotnik.robotnikchat.model;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
    public Usuario buscaUsuario (String nome, String email){

        ChatDbHelper dbHelper = new ChatDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = String.format(
                "SELECT * FROM %s WHERE %s = '%s' AND %s = '%s'",
                ChatContract.UsuarioContract.TABLE_NAME,
                ChatContract.UsuarioContract.COLUMN_NAME_NOME,
                nome,
                ChatContract.UsuarioContract.COLUMN_NAME_EMAIL,
                email);
        Cursor cursor = db.rawQuery(sql,null);
        //Log.v("cursor", "tamanho"+ cursor.getCount());

        if(cursor != null && cursor.moveToFirst()) {

           Usuario usuario = new Usuario( //retorna o usuario encontrado
                    cursor.getInt(cursor.getColumnIndex(ChatContract.UsuarioContract.COLUMN_NAME_ID)),
                    cursor.getString(cursor.getColumnIndex(ChatContract.UsuarioContract.COLUMN_NAME_ID)),
                    cursor.getString(cursor.getColumnIndex(ChatContract.UsuarioContract.COLUMN_NAME_ID))
                    );
            cursor.close();
            db.close();
            dbHelper.close();
            return usuario;
        }else{
            cursor.close();
            db.close();
            dbHelper.close();
            return null; //não existe o usuario buscado
        }
    }
    public int getProximoIdUsuario(){
        int idUsuario = 0;
        ChatDbHelper dbHelper = new ChatDbHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String sql = String.format(
                "SELECT MAX(%s) as %s FROM %s",
                ChatContract.UsuarioContract.COLUMN_NAME_ID,
                ChatContract.UsuarioContract.COLUMN_NAME_ID,
                ChatContract.UsuarioContract.TABLE_NAME );
        Cursor cursor = db.rawQuery(sql,null);
        Log.v("cursor", "tamanho"+ cursor.getCount());

        if(cursor != null && cursor.moveToFirst())
            idUsuario = cursor.getInt(cursor.getColumnIndex(ChatContract.UsuarioContract.COLUMN_NAME_ID)) + 1;

        cursor.close();
        db.close();
        dbHelper.close();

        return idUsuario;
    }
}
