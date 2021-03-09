package com.elmocorongo.greatdeals;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProdutosActivity extends AppCompatActivity {

    private EditText txtTitulo, txtSubtitulo, txtTipo, txtPreco, txtDescricao;
    private final DatabaseReference BD = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        txtTitulo = findViewById(R.id.txtTituloProduto);
        txtSubtitulo = findViewById(R.id.txtSubtituloProduto);
        txtTipo = findViewById(R.id.txtTipoProduto);
        txtPreco = findViewById(R.id.txtPrecoProd);
        txtDescricao = findViewById(R.id.txtDescricaoProd);

        Button btnAnunciar = findViewById(R.id.btnAnunciarProduto);
        btnAnunciar.setOnClickListener(new AnunciarProduto());
    }

    private class AnunciarProduto implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            AlertDialog.Builder dialogo = new AlertDialog.Builder(ProdutosActivity.this);
            dialogo.setTitle("PUBLICAR ANÚNCIO ");
            dialogo.setMessage("Deseja publicar?");
            dialogo.setPositiveButton("Sim", new DialogoOk());
            dialogo.setNegativeButton("Ainda não", new DialogoCancel());
            dialogo.setCancelable(false);
            dialogo.create();
            dialogo.show();
        }
    }

    private static class DialogoCancel implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
        }
    }

    private class DialogoOk implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            //recebendo cnpj para gravar os dados
            SharedPreferences prefs;
            prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            String cnpj = prefs.getString("cnpj", null);

            String titulo, subtitulo, tipo, descricao, preco;

            titulo = txtTitulo.getText().toString();
            subtitulo = txtSubtitulo.getText().toString();
            tipo = txtTipo.getText().toString();
            descricao = txtDescricao.getText().toString();
            preco = txtPreco.getText().toString();

            Produto p = new Produto(titulo, subtitulo, tipo, descricao, preco);

            //verificando se o campo esta preenchido
            if(isCampoVazio(titulo)){
                txtTitulo.requestFocus();
                txtTitulo.setError("Este campo é obrigatório!");
            } else if(isCampoVazio(subtitulo)){
                txtSubtitulo.requestFocus();
                txtSubtitulo.setError("Este campo é obrigatório!");
            } else if(isCampoVazio(tipo)){
                txtTipo.requestFocus();
                txtTipo.setError("Este campo é obrigatório!");
            } else if(isCampoVazio(descricao)){
                txtDescricao.requestFocus();
                txtDescricao.setError("Este campo é obrigatório!");
            } else if(isCampoVazio(preco)){
                txtPreco.requestFocus();
                txtPreco.setError("Este campo é obrigatório!");
            } else {
                DatabaseReference geral = BD.child("geral");
                String key = geral.push().getKey();
                assert key != null;
                geral.child(key).setValue(p);

                DatabaseReference empresas = BD.child("empresas");
                empresas.child(cnpj).child("produtos").child(key).setValue(p);

                Toast.makeText(ProdutosActivity.this, "Produto anunciado!", Toast.LENGTH_SHORT).show();
                txtTitulo.setText("");
                txtSubtitulo.setText("");
                txtTipo.setText("");
                txtDescricao.setText("");
                txtPreco.setText("");

                finish();
            }
        }
    }

    //Função para ver se o campo está vazio
    private boolean isCampoVazio(String valor) {
        return (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
    }
}