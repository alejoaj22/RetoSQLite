package com.edwinacubillos.firebaseexample.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alejo on 24/10/2017.
 */

public class ContactosSQliteHelper extends SQLiteOpenHelper {
    private String DATA_BASE_NAME = "agendaBD";
    private int DATA_VERSION =1;

    private String sqlCreate = "CREATE TABLE contactos ("+
            "id         INTEGER PRIMARY KEY AUTOINCREMENT,"+//campo 0
            "nombre     TEXT,"+  //campo 1
            "telefono   TEXT,"+ //campo 2
            "correo     TEXT)"; //campo 3

    public ContactosSQliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS contactos");
        db.execSQL(sqlCreate);
    }
}
