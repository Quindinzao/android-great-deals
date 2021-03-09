package com.elmocorongo.greatdeals;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class FuncionariosFragment extends Fragment {

    private EditText txtNomeF, txtCPF, txtEmailF, txtTurno, txtSalario, txtCargo;
    private final DatabaseReference BD = FirebaseDatabase.getInstance().getReference();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_funcionarios, container, false);

        txtNomeF = view.findViewById(R.id.txtNomeF);
        txtCPF = view.findViewById(R.id.txtCPF);
        txtEmailF = view.findViewById(R.id.txtEmailF);
        txtTurno = view.findViewById(R.id.txtTurno);
        txtSalario = view.findViewById(R.id.txtSalario);
        txtCargo = view.findViewById(R.id.txtCargo);

        Button btnVerFunc = view.findViewById(R.id.btnVerFunc);
        btnVerFunc.setOnClickListener(new AddFunc());

        Button btnCadastrarFuncionario = view.findViewById(R.id.btnCadastrarF);
        btnCadastrarFuncionario.setOnClickListener(new CadastrarFuncionario());

        return view;
    }


    private class CadastrarFuncionario implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            //recebendo cnpj para entrar ou sair
            SharedPreferences prefs;
            prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            String cnpj = prefs.getString("cnpj", null);

            //verificando se foi feito login
            if(cnpj == null){
                AlertDialog.Builder dialogo = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                dialogo.setTitle("Deseja entrar em sua conta?");
                dialogo.setPositiveButton("Entrar", new EntrarConta());
                dialogo.setNegativeButton("Cancelar", new Voltar());
                dialogo.setCancelable(false);
                dialogo.create();
                dialogo.show();
            } else {
                String nome, cpf, email, turno, salario, cargo;

                nome = txtNomeF.getText().toString();
                cpf = txtCPF.getText().toString();
                email = txtEmailF.getText().toString();
                turno = txtTurno.getText().toString();
                salario = txtSalario.getText().toString();
                cargo = txtCargo.getText().toString();

                Equipe eq = new Equipe(nome, cpf, email, turno, salario, cargo);

                if (isCampoVazio(nome)) {
                    txtNomeF.requestFocus();
                    txtNomeF.setError("Campo obrigatório!");
                } else if (isCampoVazio(cpf)) {
                    txtCPF.requestFocus();
                    txtCPF.setError("Campo obrigatório!");
                } else if (cpf.length() != 11) {
                    txtCPF.requestFocus();
                    txtCPF.setError("CPF inválido!");
                } else if (isCampoVazio(email)) {
                    txtEmailF.requestFocus();
                    txtEmailF.setError("Campo obrigatório!");
                } else if (!isEmailValido(email)) {
                    txtEmailF.requestFocus();
                    txtEmailF.setError("Email inválido!");
                } else if (isCampoVazio(turno)) {
                    txtTurno.requestFocus();
                    txtTurno.setError("Campo obrigatório!");
                } else if (isCampoVazio(salario)) {
                    txtSalario.requestFocus();
                    txtSalario.setError("Campo obrigatório!");
                } else if (isCampoVazio(cargo)) {
                    txtCargo.requestFocus();
                    txtCargo.setError("Campo obrigatório!");
                } else {
                    DatabaseReference empresas = BD.child("empresas");
                    empresas.child(cnpj).child("equipe").child(cpf).setValue(eq);

                    txtNomeF.setText("");
                    txtCPF.setText("");
                    txtEmailF.setText("");
                    txtTurno.setText("");
                    txtSalario.setText("");
                    txtCargo.setText("");

                    Toast.makeText(getContext(), "Funcionário cadastrado com sucessso!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //leva para a activity para ver funcionários
    private class AddFunc implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //recebendo cnpj para entrar ou sair
            SharedPreferences prefs;
            prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
            String cnpj = prefs.getString("cnpj", null);

            //verificando se foi feito login
            if(cnpj == null){
                AlertDialog.Builder dialogo = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                dialogo.setTitle("Deseja entrar em sua conta?");
                dialogo.setPositiveButton("Entrar", new EntrarConta());
                dialogo.setNegativeButton("Cancelar", new Voltar());
                dialogo.setCancelable(false);
                dialogo.create();
                dialogo.show();
            } else {
                Intent intent = new Intent(getContext(), FuncionarioActivity.class);
                startActivity(intent);
            }
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
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        }
    }

    private boolean isCampoVazio(String valor){
        return (TextUtils.isEmpty(valor) || valor.trim().isEmpty());
    }

    private boolean isEmailValido(String email){
        return (Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }
}

