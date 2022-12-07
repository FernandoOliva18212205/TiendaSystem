package com.example.tiendasystem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tiendasystem.db.DbHelper;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class productoEliminar extends AppCompatActivity  implements View.OnClickListener {
    Button btn1, btn2;
    EditText et1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producto_eliminar);
        btn1 = findViewById(R.id.button1);
        et1 = findViewById(R.id.txtCodigo);
        btn2 = findViewById(R.id.btnEscaner);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DbHelper admin = new DbHelper(productoEliminar.this);
                SQLiteDatabase db = admin.getWritableDatabase();

                db.execSQL("DELETE FROM t_productos where id_producto= '" + et1.getText() + "'");
                Toast.makeText(productoEliminar.this, "¡Se ha eliminado correctamente!", Toast.LENGTH_SHORT).show();
                finish();
                db.close();
            }
        });

        btn2.setOnClickListener(this);
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
                et1.setText(Contenido);


            } else {
                Toast.makeText(this, "Sin resultado", Toast.LENGTH_LONG).show();
            }

        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}