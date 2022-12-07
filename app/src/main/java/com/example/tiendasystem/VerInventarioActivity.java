package com.example.tiendasystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;

import com.example.tiendasystem.db.adaptadores.listaProductos;
import com.example.tiendasystem.db.dbProducto;
import com.example.tiendasystem.db.entidades.productos;

import java.util.ArrayList;

public class VerInventarioActivity extends AppCompatActivity {
    RecyclerView listProductos;
    ArrayList<productos> listaArrayProducto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_inventario);

        listProductos = findViewById(R.id.listaProducto);

        listProductos.setLayoutManager(new LinearLayoutManager(this));


        dbProducto dbProductos = new dbProducto(VerInventarioActivity.this);
        try{
            listaArrayProducto = new ArrayList<>();
            listaProductos adapter = new listaProductos(dbProductos.mostrarProductos2());
            listProductos.setAdapter(adapter);
        }catch (Exception e)
        {

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }
}