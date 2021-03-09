package com.elmocorongo.greatdeals;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CadastroActivity extends AppCompatActivity {

    private EditText txtNomeEmpresaCadastrar, txtCNPJCadastrar, txtEmailCadastrar, txtTelCadastrar, txtCelCadastrar, txtSenha1, txtSenha2;
    private final DatabaseReference BD = FirebaseDatabase.getInstance().getReference();
    DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        txtNomeEmpresaCadastrar = findViewById(R.id.txtNomeEmpresaCadastrar);
        txtCNPJCadastrar = findViewById(R.id.txtCNPJCadastrar);
        txtEmailCadastrar = findViewById(R.id.txtEmailCadastrar);
        txtTelCadastrar = findViewById(R.id.txtTelCadastrar);
        txtCelCadastrar = findViewById(R.id.txtCelCadastrar);
        txtSenha1 = findViewById(R.id.txtSenha1);
        txtSenha2 = findViewById(R.id.txtSenha2);

        Button btnCadastrar = findViewById(R.id.btnCadastrar);
        btnCadastrar.setOnClickListener(new Cadastrar());

        //txtCNPJCadastrar.addTextChangedListener(MyMask.Mask.insert("##.###.###/####-##", txtCNPJCadastrar));
        txtTelCadastrar.addTextChangedListener(MyMask.Mask.insert("(##)####-####", txtTelCadastrar));
        txtCelCadastrar.addTextChangedListener(MyMask.Mask.insert("(##)#####-####", txtCelCadastrar));

        db = new DBHelper(this);
    }

    private class Cadastrar implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String nome, cnpj, email, tel, cel, senha1, senha2;

            nome = txtNomeEmpresaCadastrar.getText().toString();
            cnpj = txtCNPJCadastrar.getText().toString();
            email = txtEmailCadastrar.getText().toString();
            tel = txtTelCadastrar.getText().toString();
            cel = txtCelCadastrar.getText().toString();
            senha1 = txtSenha1.getText().toString();
            senha2 = txtSenha2.getText().toString();

            if(isCampoVazio(nome)){
                txtNomeEmpresaCadastrar.requestFocus();
                txtNomeEmpresaCadastrar.setError("Campo obrigatório!");
            } else if(isCampoVazio(cnpj)){
                txtCNPJCadastrar.requestFocus();
                txtCNPJCadastrar.setError("Campo obrigatório!");
            } else if(cnpj.length() != 14) {
                txtCNPJCadastrar.requestFocus();
                txtCNPJCadastrar.setError("CNPJ inválido!");
            } else if(isCampoVazio(email)){
                txtEmailCadastrar.requestFocus();
                txtEmailCadastrar.setError("Campo obrigatório!");
            } else if(!isEmailValido(email)) {
                txtEmailCadastrar.requestFocus();
                txtEmailCadastrar.setError("E-mail inválido!");
            } else if(isCampoVazio(tel)){
                txtTelCadastrar.requestFocus();
                txtTelCadastrar.setError("Campo obrigatório!");
            } else if(!isTelValido(tel)){
                txtTelCadastrar.requestFocus();
                txtTelCadastrar.setError("Telefone inválido!");
            } else if(tel.length() != 13) {
                txtTelCadastrar.requestFocus();
                txtTelCadastrar.setError("Telefone inválido!");
            } else if(isCampoVazio(cel)){
                txtCelCadastrar.requestFocus();
                txtCelCadastrar.setError("Campo obrigatório!");
            } else if(!isCelValido(cel)){
                txtCelCadastrar.requestFocus();
                txtCelCadastrar.setError("Celular inválido!");
            } else if(cel.length() != 14){
                txtCelCadastrar.requestFocus();
                txtCelCadastrar.setError("Celular inválido!");
            } else if(isCampoVazio(senha1)) {
                txtSenha1.requestFocus();
                txtSenha1.setError("Campo obrigatório!");
            } else if(senha1.length() < 8){
                txtSenha1.requestFocus();
                txtSenha1.setError("A senha deve conter, no mínimo, 8 caracteres!");
            } else if(isCampoVazio(senha2)){
                txtSenha2.requestFocus();
                txtSenha2.setError("Campo obrigatório!");
            } else if(senha1.equals(senha2)){
                Boolean checkCNPJ = db.checkCNPJ(cnpj);
                if(checkCNPJ){
                    boolean insertData = db.insert(cnpj, senha1);
                    if(insertData){
                        Empresas e = new Empresas(nome, cnpj, email, tel, cel, senha1);

                        DatabaseReference empresas = BD.child("empresas");
                        empresas.child(cnpj).setValue(e);

                        txtNomeEmpresaCadastrar.setText("");
                        txtCNPJCadastrar.setText("");
                        txtEmailCadastrar.setText("");
                        txtTelCadastrar.setText("");
                        txtTelCadastrar.setText("");
                        txtCelCadastrar.setText("");
                        txtSenha1.setText("");
                        txtSenha2.setText("");

                        Toast.makeText(CadastroActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    txtCNPJCadastrar.requestFocus();
                    txtCNPJCadastrar.setError("Este CNPJ já possui cadastro!");
                }
            } else {
                txtSenha2.requestFocus();
                txtSenha2.setError("As senhas devem ser iguais!");
            }
        }
    }

    private boolean isCampoVazio(String valor) {
        return (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
    }

    private boolean isEmailValido(String email) {
        return (Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }

    private boolean isTelValido(String tel) {
        return (Patterns.PHONE.matcher(tel).matches());
    }

    private boolean isCelValido(String cel) {
        return (Patterns.PHONE.matcher(cel).matches());
    }
}