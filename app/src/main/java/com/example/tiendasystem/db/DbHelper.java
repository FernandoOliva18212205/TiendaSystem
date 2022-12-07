package com.example.tiendasystem.db;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String DATABASE_NOMBRE = "inventorydb.db";
    public static final String TABLE_USUARIOS = "t_usuarios";
    public static final String TABLE_PRODUCTOS = "t_productos";
    public static final String TABLE_TIPOS = "t_tipos";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NOMBRE, null, DATABASE_VERSION);
    }



    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_USUARIOS + "(" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL UNIQUE," +
                "contrasena TEXT NOT NULL," +
                "fotoPerfil BLOB)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_TIPOS + "(" +
                "id_tipos INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL UNIQUE)");
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_PRODUCTOS + "(" +
                "id_producto TEXT PRIMARY KEY," +
                "nombre TEXT NOT NULL UNIQUE," +
                "precio DOUBLE NOT NULL," +
                "tipo INTEGER NOT NULL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_USUARIOS);
        onCreate(sqLiteDatabase);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_TIPOS);
        onCreate(sqLiteDatabase);
        sqLiteDatabase.execSQL("DROP TABLE " + TABLE_PRODUCTOS);
        onCreate(sqLiteDatabase);
    }
}
