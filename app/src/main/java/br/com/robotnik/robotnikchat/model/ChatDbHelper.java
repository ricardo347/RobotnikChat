package br.com.robotnik.robotnikchat.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class ChatDbHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "chats.db";
    private static final int DB_VERSION = 1;

    ChatDbHelper(Context context){

        super(context, DB_NAME,null,DB_VERSION);


    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ChatContract.createTableUsuario());
        db.execSQL(ChatContract.createTableSessao());
        db.execSQL(ChatContract.createTableInteracao());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
