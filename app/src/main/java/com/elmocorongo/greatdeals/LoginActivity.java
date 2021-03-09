package com.elmocorongo.greatdeals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class LoginActivity extends AppCompatActivity {

    private EditText txtCNPJLogin, txtSenhaLogin;
    private final DatabaseReference BD = FirebaseDatabase.getInstance().getReference();
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txtCNPJLogin = findViewById(R.id.txtCNPJLogin);
        txtSenhaLogin = findViewById(R.id.txtSenhaLogin);

        Button btnEntrar = findViewById(R.id.btnEntrar);
        btnEntrar.setOnClickListener(new Entrar());

        Button btnActCadastrar = findViewById(R.id.btnActCadastrar);
        btnActCadastrar.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), CadastroActivity.class);
            startActivity(intent);
        });

        txtCNPJLogin.addTextChangedListener(MyMask.Mask.insert("##############", txtCNPJLogin));

        db = new DBHelper(this);
    }

    private class Entrar implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String cnpj, senha1;

            cnpj = txtCNPJLogin.getText().toString();
            senha1 = txtSenhaLogin.getText().toString();

            if(cnpj.equals("")) {
                txtCNPJLogin.requestFocus();
                txtCNPJLogin.setError("Campo obrigatório!");
            } else if(cnpj.length() != 14){
                txtCNPJLogin.requestFocus();
                txtCNPJLogin.setError("CNPJ inválido!");
            } else if(senha1.equals("")){
                txtSenhaLogin.requestFocus();
                txtSenhaLogin.setError("Campo obrigatório!");
            } else {
                Boolean chkCNPJsenha = db.chkCNPJsenha(cnpj, senha1);
                if(chkCNPJsenha){
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);

                    //enviando cnpj para entrar
                    SharedPreferences prefs;
                    prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor ed = prefs.edit();
                    ed.putString("cnpj", cnpj);
                    ed.apply();

                    finish();
                }else{
                    Toast.makeText(getApplicationContext(),"CNPJ ou senha incorretos!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}