package com.example.tiendasystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.tiendasystem.db.adaptadores.listaProductos;
import com.example.tiendasystem.db.dbProducto;
import com.example.tiendasystem.db.entidades.productos;

import java.util.ArrayList;
import java.util.UUID;

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item){

        switch (item.getItemId()){
            case R.id.icon_add:{
                Intent intent = new Intent(VerInventarioActivity.this, productoCrear.class);
                startActivity(intent);
                Toast.makeText(this, "Crear", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.icon_save:{
                Intent intent = new Intent(VerInventarioActivity.this, productoModificar.class);
                startActivity(intent);
                Toast.makeText(this, "Guardar", Toast.LENGTH_LONG).show();
                break;
            }
            case R.id.icon_delete:{
                Intent intent = new Intent(VerInventarioActivity.this, productoEliminar.class);
                startActivity(intent);
                Toast.makeText(this, "Eliminar", Toast.LENGTH_LONG).show();
                break;
            }
            default: break;
        }
        return true;
    }
}