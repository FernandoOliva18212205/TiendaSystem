package com.example.tiendasystem;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tiendasystem.db.DbHelper;
import com.example.tiendasystem.db.dbProducto;
import com.example.tiendasystem.db.dbTipos;
import com.example.tiendasystem.db.entidades.Tipos;


import java.util.ArrayList;
import java.util.List;

public class productoCrear extends AppCompatActivity {
    Button btn1, btn2;
    EditText txtCodigo, txtNombre, txtPrecio;
    String nombre;
    Double precio;
    Integer tipo, codigo;
    Spinner spTipo;
    private Cursor fila;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_crear);
        btn1 = findViewById(R.id.button1);
        btn2 = findViewById(R.id.btnEscaner);
        spTipo = findViewById(R.id.spinner);
        txtCodigo = findViewById(R.id.txtCodigo);
        txtNombre = findViewById(R.id.txtNombre);
        txtPrecio = findViewById(R.id.txtPrecio);

        DbHelper dbHelper = new DbHelper(productoCrear.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();


        List<Tipos> list = llenarTipos();

        ArrayAdapter<Tipos> adapter = new ArrayAdapter<>(getApplicationContext(),  R.layout.support_simple_spinner_dropdown_item, list);
        spTipo.setAdapter(adapter);

        spTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                tipo = ((Tipos)adapterView.getSelectedItem()).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHelper admin=new DbHelper(productoCrear.this);
                SQLiteDatabase db=admin.getWritableDatabase();
                codigo = Integer.valueOf(txtCodigo.getText().toString());
                nombre = txtNombre.getText().toString();
                precio = Double.valueOf(txtPrecio.getText().toString());
                fila=db.rawQuery("select * from t_productos where nombre='"+
                        nombre+"'",null);
                try {
                    if(fila.moveToFirst()){
                        String nom=fila.getString(1);
                        if (nombre.equals(nom)){
                            Toast.makeText(productoCrear.this, "¡Producto ya existente!", Toast.LENGTH_SHORT).show();
                            txtCodigo.setText("");
                            txtNombre.setText("");
                            txtPrecio.setText("");
                        }
                    }
                    else {
                        if(!nombre.equals("")){
                            dbProducto dbProductos = new dbProducto(productoCrear.this);
                            long id = dbProductos.insertarProductos(codigo,nombre, precio,tipo);
                            if(id > 0){
                                Toast.makeText(productoCrear.this, "¡Se ha registrado correctamente!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                            else
                            {
                                Toast.makeText(productoCrear.this, "¡Ha ocurrido algo inesperado!", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    }

                } catch (Exception e) {
                    Toast toast=Toast.makeText(productoCrear.this,"Error" + e.getMessage(),Toast.LENGTH_LONG);
                    toast.show();
                }
            }
        });
    }
    private List<Tipos> llenarTipos(){
        List<Tipos> listaTipo = new ArrayList<>();
        dbTipos dbTipos = new dbTipos(productoCrear.this);
        Cursor cursor = dbTipos.mostrarTipos();
        if(cursor != null && cursor.moveToFirst())
        {
                do{
                    Tipos tip = new Tipos();

                    tip.setId(cursor.getInt(cursor.getColumnIndex("id_tipos")));
                    tip.setNombre(cursor.getString(cursor.getColumnIndex("nombre")));
                    listaTipo.add(tip);
                }while (cursor.moveToNext());
        }
        dbTipos.close();

        return listaTipo;
    }

}