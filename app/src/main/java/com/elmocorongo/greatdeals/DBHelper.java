package com.elmocorongo.greatdeals;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "greatdealsfinal", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE empresa(cnpj VARCHAR primary key, senha1 VARCHAR)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists empresa");
    }

    public boolean insert(String cnpj, String senha1) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("cnpj", cnpj);
        contentValues.put("senha1", senha1);

        long ins = db.insert("empresa", null, contentValues);
        return ins != 1;
    }

    public Boolean checkCNPJ(String cnpj) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM empresa WHERE cnpj = ?", new String[]{cnpj});
        return cursor.getCount() <= 0;
    }

    public Boolean chkCNPJsenha (String cnpj, String senha1){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM empresa WHERE cnpj = ? and senha1 = ?", new String[]{cnpj, senha1});
        return cursor.getCount() > 0;
    }
}
