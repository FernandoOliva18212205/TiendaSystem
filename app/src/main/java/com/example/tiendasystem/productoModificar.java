package com.example.tiendasystem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.tiendasystem.db.dbProducto;
import com.example.tiendasystem.db.dbTipos;
import com.example.tiendasystem.db.entidades.Tipos;
import com.example.tiendasystem.db.entidades.productos;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.List;

public class productoModificar extends AppCompatActivity  implements View.OnClickListener{
    EditText txtNombre,txtPrecio,txtBusqueda;
    Button btnBuscar,btnGuardar;
    Spinner spTipo;
    Integer tipo,codigo;

    productos produtoss;

    String nombre = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_modificar);
        txtBusqueda = findViewById(R.id.txtCodigo);
        txtNombre = findViewById(R.id.txtNombre);
        txtPrecio = findViewById(R.id.txtPrecio);
        btnBuscar = findViewById(R.id.btnEscaner);
        btnGuardar = findViewById(R.id.button1);
        spTipo = findViewById(R.id.spinner);
        List<Tipos> list = llenarTipos();

        ArrayAdapter<Tipos> adapter = new ArrayAdapter<>(getApplicationContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, list);

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
        btnBuscar.setOnClickListener(this);
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try{
                    codigo = Integer.valueOf(txtBusqueda.getText().toString()) ;
                    Double precio = Double.valueOf(txtPrecio.getText().toString());
                    nombre = txtNombre.getText().toString();
                    dbProducto dbProductos = new dbProducto(productoModificar.this);
                    dbProductos.editarProducto(codigo,nombre,precio,tipo);

                    Toast.makeText(productoModificar.this, "Producto Editado correctamente", Toast.LENGTH_SHORT).show();
                }catch (Exception e)
                {
                    Toast.makeText(productoModificar.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private List<Tipos> llenarTipos(){
        List<Tipos> listaTipo = new ArrayList<>();
        dbTipos dbTipos = new dbTipos(productoModificar.this);
        Cursor cursos = dbTipos.mostrarTipos();
        if(cursos != null)
        {
            if(cursos.moveToFirst()){
                do{
                    Tipos tip = new Tipos();

                    tip.setId(cursos.getInt(cursos.getColumnIndex("id_tipos")));
                    tip.setNombre(cursos.getString(cursos.getColumnIndex("nombre")));
                    listaTipo.add(tip);
                }while (cursos.moveToNext());
            }
        }
        dbTipos.close();

        return listaTipo;
    }

    @Override
    public void onClick(View view) {
        scanCode();
    }

    private void scanCode() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Escaneando");
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() != null) {

                String Contenido = result.getContents();
                txtBusqueda.setText(Contenido);
                try{
                    codigo = Integer.valueOf(txtBusqueda.getText().toString()) ;
                    dbProducto dbProductos = new dbProducto(productoModificar.this);
                    produtoss = dbProductos.verProductos(codigo);

                    if(produtoss != null){
                        txtNombre.setText(produtoss.getNombre());
                        txtPrecio.setText(String.valueOf(produtoss.getPrecio()));
                        int posicion = produtoss.getTipo() - 2;
                        spTipo.setSelection(posicion);

                    }
                }catch (Exception e)
                {
                    Toast.makeText(productoModificar.this, "Error", Toast.LENGTH_SHORT).show();
                }


            } else {
                Toast.makeText(this, "Sin resultado", Toast.LENGTH_LONG).show();
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}