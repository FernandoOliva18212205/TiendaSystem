package com.example.tiendasystem;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;

public class CajaActivity extends AppCompatActivity {
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



        btnEscanear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });
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
}