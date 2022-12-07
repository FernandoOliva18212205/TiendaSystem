package com.example.tiendasystem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tiendasystem.db.adaptadores.listaProductos;
import com.example.tiendasystem.db.dbProducto;
import com.example.tiendasystem.db.entidades.productos;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class CajaActivity extends AppCompatActivity  implements View.OnClickListener{
    private EditText txtCodigo;
    private TextView lblTotal;
    private ListView lvProductosCobrar;
    private Button btnEscanear,btnCobrar;
    private Integer codigo;
    private String nombre;
    private Double precio, acum=0.0;
    ArrayList<String> producto = new ArrayList<String>();
    ArrayList<productos> listaArrayProducto;
    productos produtoss;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caja);

        txtCodigo = findViewById(R.id.txtCodigoCobrar);
        lblTotal = findViewById(R.id.lblTotal);
        lvProductosCobrar = findViewById(R.id.lvProductos);
        btnEscanear = findViewById(R.id.btnEscanear);
        btnCobrar = findViewById(R.id.btnCobrar);



        btnEscanear.setOnClickListener(this);
        btnCobrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lvProductosCobrar.setAdapter(null);
                lblTotal.setText("$");
                txtCodigo.setText("");
                Toast.makeText(CajaActivity.this, "GRACIAS POR SU COMPRA", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onClick(View view) {
        scanCode();
    }

    private void scanCode(){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Escaneando");
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result!= null) {
            if (result.getContents() != null) {

                String Contenido = result.getContents();
                txtCodigo.setText(Contenido);
                try{

                    codigo = Integer.valueOf(txtCodigo.getText().toString()) ;
                    dbProducto dbProductos = new dbProducto(CajaActivity.this);
                    produtoss = dbProductos.verProductos(codigo);

                    if(produtoss != null){
                        nombre = produtoss.getNombre();
                        precio = produtoss.getPrecio();
                    }

                    producto.add(nombre + "\nPrecio unitario: $" + precio );
                    //Variable acumuladora que va calculando el total el pedido
                    acum = acum + precio;

                    ArrayAdapter<String> adaptador = new ArrayAdapter<String>(CajaActivity.this, android.R.layout.simple_list_item_1, producto);
                    lvProductosCobrar.setAdapter(adaptador);
                    lblTotal.setText("$" + acum);

                }catch (Exception e)
                {
                    Toast.makeText(CajaActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }


            } else {
                Toast.makeText(this, "Sin resultado", Toast.LENGTH_LONG).show();
            }

        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }

    }
}