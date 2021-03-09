package com.elmocorongo.greatdeals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AtualizarNomeEmpresaActivity extends AppCompatActivity {

    private EditText txtAtualizar;
    private final DatabaseReference BD = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atualizar);

        txtAtualizar = findViewById(R.id.txtAtualizar);

        Button btnAtualizar = findViewById(R.id.btnAtualizar);
        btnAtualizar.setOnClickListener(new AtualizarNome());

        //recebendo cnpj para entrar ou sair
        SharedPreferences prefs;
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String cnpj = prefs.getString("cnpj", null);
    }

    private class AtualizarNome implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String nome;
            nome = txtAtualizar.getText().toString();

            if(isCampoVazio(nome)){
                txtAtualizar.requestFocus();
                txtAtualizar.setError("Campo obrigatório!");
            } else {
                //recebendo cnpj para entrar ou sair
                SharedPreferences prefs;
                prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String cnpj = prefs.getString("cnpj", null);



                Toast.makeText(AtualizarNomeEmpresaActivity.this,  "Vê no firebase se foi", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isCampoVazio(String valor) {
        return (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
    }
}