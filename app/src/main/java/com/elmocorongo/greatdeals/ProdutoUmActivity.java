package com.elmocorongo.greatdeals;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProdutoUmActivity extends AppCompatActivity {

    private TextView lblTit, lblSub, lblTipo, lblPrec, lblDesc;
    private final DatabaseReference BD = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produto_um);

        String titulo, subtitulo, tipo, descricao, preco;

        Intent intent = getIntent();

        titulo = intent.getStringExtra("titulo");
        subtitulo = intent.getStringExtra("subtitulo");
        tipo = intent.getStringExtra("tipo");
        descricao = intent.getStringExtra("descricao");
        preco = intent.getStringExtra("preco");

        lblTit = findViewById(R.id.lblTituloProd);
        lblSub = findViewById(R.id.lblSubtituloProd);
        lblTipo = findViewById(R.id.lblTipoProd);
        lblPrec = findViewById(R.id.lblPrecoProd);
        lblDesc = findViewById(R.id.lblDescricaoProd);

        lblTit.setText(titulo);
        lblSub.setText(subtitulo);
        lblTipo.setText(tipo);
        lblDesc.setText(descricao);
        lblPrec.setText(preco);

        Button btnComprar = findViewById(R.id.btnComprarProd);
        btnComprar.setOnClickListener(new Comprar());
    }

    private class Comprar implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //recebendo cnpj para entrar ou sair
            SharedPreferences prefs;
            prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String cnpj = prefs.getString("cnpj", null);

            //verificando se foi feito login
            if(cnpj == null){
                AlertDialog.Builder dialogo = new AlertDialog.Builder(ProdutoUmActivity.this);
                dialogo.setTitle("Deseja entrar em sua conta?");
                dialogo.setPositiveButton("Entrar", new EntrarConta());
                dialogo.setNegativeButton("Cancelar", new Voltar());
                dialogo.setCancelable(false);
                dialogo.create();
                dialogo.show();
            } else {
                AlertDialog.Builder dialogo = new AlertDialog.Builder(ProdutoUmActivity.this);
                dialogo.setTitle("Deseja comprar esse produto?");
                dialogo.setPositiveButton("Sim", new Sim());
                dialogo.setNegativeButton("Quero não", new Voltar());
                dialogo.setCancelable(false);
                dialogo.create();
                dialogo.show();
            }
        }
    }

    //dialog comprar
    private class Sim implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            //recebendo cnpj para comprar
            SharedPreferences prefs;
            prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String cnpj = prefs.getString("cnpj", null);

            String titulo, subtitulo, tipo, descricao, preco;

            titulo = lblTit.getText().toString();
            subtitulo = lblSub.getText().toString();
            tipo = lblTipo.getText().toString();
            descricao = lblDesc.getText().toString();
            preco = lblPrec.getText().toString();

            Produto p = new Produto(titulo, subtitulo, tipo, descricao, preco);

            DatabaseReference empresas = BD.child("empresas");
            String key = empresas.push().getKey();
            assert key != null;
            empresas.child(cnpj).child("comprados").child(key).setValue(p);

            Intent intent = new Intent(getApplicationContext(), RegistrosActivity.class);
            intent.putExtra("key", key);
            startActivity(intent);

            finish();
        }
    }

    //dialog voltar
    private static class Voltar implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
        }
    }

    //dialog entrar na conta
    private class EntrarConta implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }
    }
}