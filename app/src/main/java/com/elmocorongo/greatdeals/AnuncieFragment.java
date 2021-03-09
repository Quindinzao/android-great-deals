package com.elmocorongo.greatdeals;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import java.util.Objects;

public class AnuncieFragment extends Fragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_anuncie, container, false);

        //recebendo cnpj para entrar ou sair
        SharedPreferences prefs;
        prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String cnpj = prefs.getString("cnpj", null);

        //produtos
        Button btnProdutos = view.findViewById(R.id.btnProdutos);
        btnProdutos.setOnClickListener(view1 -> {
            if(cnpj == null){
                AlertDialog.Builder dialogo = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                dialogo.setTitle("Deseja entrar em sua conta?");
                dialogo.setPositiveButton("Entrar", new EntrarConta());
                dialogo.setNegativeButton("Cancelar", new Voltar());
                dialogo.setCancelable(false);
                dialogo.create();
                dialogo.show();
            } else {
                Intent intent = new Intent(getContext(), ProdutosActivity.class);
                startActivity(intent);
            }
        });

        //serviços
        /*Button btnServicos = view.findViewById(R.id.btnServicos);
        btnServicos.setOnClickListener(view1 -> {
            if(cnpj == null){
                AlertDialog.Builder dialogo = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                dialogo.setTitle("Deseja entrar em sua conta?");
                dialogo.setPositiveButton("Entrar", new EntrarConta());
                dialogo.setNegativeButton("Cancelar", new Voltar());
                dialogo.setCancelable(false);
                dialogo.create();
                dialogo.show();
            } else {
                Intent intent1 = new Intent(getContext(), ServicosActivity.class);
                startActivity(intent1);
            }
        });

        //serviços
        Button btnAlugados = view.findViewById(R.id.btnAlugados);
        btnAlugados.setOnClickListener(view1 -> {
            if(cnpj == null){
                AlertDialog.Builder dialogo = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                dialogo.setTitle("Deseja entrar em sua conta?");
                dialogo.setPositiveButton("Entrar", new EntrarConta());
                dialogo.setNegativeButton("Cancelar", new Voltar());
                dialogo.setCancelable(false);
                dialogo.create();
                dialogo.show();
            } else {
                Intent intent2 = new Intent(getContext(), AlugadosActivity.class);
                startActivity(intent2);
            }
        });

        //serviços
        Button btnParcerias = view.findViewById(R.id.btnParcerias);
        btnParcerias.setOnClickListener(view1 -> {
            if(cnpj == null){
                AlertDialog.Builder dialogo = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
                dialogo.setTitle("Deseja entrar em sua conta?");
                dialogo.setPositiveButton("Entrar", new EntrarConta());
                dialogo.setNegativeButton("Cancelar", new Voltar());
                dialogo.setCancelable(false);
                dialogo.create();
                dialogo.show();
            } else {
                Intent intent3 = new Intent(getContext(), ParceriaActivity.class);
                startActivity(intent3);
            }
        });*/

        return view;
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
}