package com.example.tiendasystem.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.tiendasystem.db.entidades.productos;

import java.util.ArrayList;

public class dbProducto extends DbHelper{

    Context context;
    public dbProducto(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public long insertarProductos(Integer codigo,String nombre, Double precio, Integer tipo){
        long id =0;
        try{
            DbHelper dbHelper = new DbHelper(context);
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("id_producto", codigo);
            values.put("nombre", nombre);
            values.put("precio", precio);
            values.put("tipo", tipo);

            id = db.insert(TABLE_PRODUCTOS, null, values );
        }catch (Exception ex)
        {
            ex.toString();
        }
        return id;
    }

    public ArrayList<productos> mostrarProducto(Integer codigo){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        ArrayList<productos> listaProductos = new ArrayList<>();
        productos produtos = null;
        Cursor cursorProducto = null;

        cursorProducto = db.rawQuery("SELECT nombre, precio FROM " + TABLE_PRODUCTOS + " WHERE id_producto = '"+codigo+"' LIMIT 1", null);

        if(cursorProducto.moveToFirst())
        {
            do{
                produtos = new productos();
                produtos.setNombre(cursorProducto.getString(0));
                produtos.setPrecio(cursorProducto.getDouble(1));
                listaProductos.add(produtos);
            }while (cursorProducto.moveToNext());
        }

        cursorProducto.close();

        return listaProductos;
    }

    public productos verProductos(Integer codigo){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        productos produtoss = null;
        Cursor cursorProducto;

        cursorProducto = db.rawQuery("SELECT * FROM "+TABLE_PRODUCTOS+" WHERE id_producto = '" + codigo + "' LIMIT 1", null);

        if(cursorProducto.moveToFirst())
        {
            produtoss = new productos();
            produtoss.setId(cursorProducto.getInt(0));
            produtoss.setNombre(cursorProducto.getString(1));
            produtoss.setPrecio(cursorProducto.getDouble(2));
            produtoss.setTipo(cursorProducto.getInt(3));
        }

        cursorProducto.close();

        return produtoss;
    }

    public boolean editarProducto(Integer codigo,String nombre, Double precio, int tipo)
    {

        boolean correcto = false;
        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try{
            db.execSQL("UPDATE " + TABLE_PRODUCTOS +" SET nombre = '"+nombre+"', precio = " +precio + ", tipo = "+ tipo + " WHERE id_producto = '"+codigo+"' ");
            correcto = true;
        }catch (Exception ex){
            correcto=false;
        }finally {
            db.close();
        }


        return correcto;
    }

    public productos verProductos2(String nombre){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        productos produtoss = null;
        Cursor cursorProducto;

        cursorProducto = db.rawQuery("SELECT p.id,p.nombre,p.precio,t.nombre FROM "+TABLE_PRODUCTOS+" p WHERE nombre = '" + nombre + "' INNER JOIN " +TABLE_TIPOS+" t ON p.tipo = t.id_tipo LIMIT 1", null);

        if(cursorProducto.moveToFirst())
        {
            produtoss = new productos();
            produtoss.setId(cursorProducto.getInt(0));
            produtoss.setNombre(cursorProducto.getString(1));
            produtoss.setPrecio(cursorProducto.getDouble(2));
            produtoss.setNombretipo(cursorProducto.getString(3));
        }

        cursorProducto.close();

        return produtoss;
    }

    public ArrayList<productos> mostrarProductos2(){

        DbHelper dbHelper = new DbHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        ArrayList<productos> listaProductos = new ArrayList<>();
        productos produtos = null;
        Cursor cursorProducto = null;

        cursorProducto = db.rawQuery("SELECT t_productos.id_producto,t_productos.nombre,t_productos.precio,t_tipos.nombre FROM "+TABLE_PRODUCTOS+" INNER JOIN " +TABLE_TIPOS+" ON t_productos.tipo=t_tipos.id_tipos", null);

        if(cursorProducto.moveToFirst())
        {
            do{
                produtos = new productos();
                produtos.setId(cursorProducto.getInt(0));
                produtos.setNombre(cursorProducto.getString(1));
                produtos.setPrecio(cursorProducto.getDouble(2));
                produtos.setNombretipo(cursorProducto.getString(3));
                listaProductos.add(produtos);
            }while (cursorProducto.moveToNext());
        }

        cursorProducto.close();

        return listaProductos;
    }
}
