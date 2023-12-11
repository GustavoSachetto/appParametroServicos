package com.example.appparametroservico;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText edNome, edTel, edEmail, edEndereco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edNome = findViewById(R.id.edtNome);
        edTel = findViewById(R.id.edtTel);
        edEmail = findViewById(R.id.edtEmail);
        edEndereco = findViewById(R.id.edtEndereco);
    }
    public void cadastrar (View v) {
        Intent abrirTela2 = new Intent(MainActivity.this, tela2.class);
        abrirTela2.putExtra("nome", edNome.getText().toString());
        abrirTela2.putExtra("email", edEmail.getText().toString());
        abrirTela2.putExtra("tel", edTel.getText().toString());
        abrirTela2.putExtra("endereco", edEndereco.getText().toString());
        startActivity(abrirTela2);
    }
}