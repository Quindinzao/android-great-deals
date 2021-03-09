package com.elmocorongo.greatdeals;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.app.AlertDialog;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import com.elmocorongo.greatdeals.ui.main.SectionsPagerAdapter;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        //recebendo cnpj para entrar ou sair
        SharedPreferences prefs;
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String cnpj = prefs.getString("cnpj", null);

        fab.setOnClickListener(view -> {

            Intent intent = new Intent(getApplicationContext(), RegistrosActivity.class);
            startActivity(intent);

            /*Snackbar.make(view, "Sem registros de negócios... :(", Snackbar.LENGTH_LONG)
                    .setBackgroundTint(Color.parseColor("#FFE100"))
                    .setTextColor(Color.parseColor("#FF000000"))
                    .setAction("Action", null).show();*/
        });

        FloatingActionButton fab2 = findViewById(R.id.fab2);

        fab2.setOnClickListener(view -> {
            if(cnpj == null){
                AlertDialog.Builder dialogo = new AlertDialog.Builder(MainActivity.this);
                dialogo.setTitle("Deseja entrar em sua conta?");
                dialogo.setPositiveButton("Entrar", new EntrarConta());
                dialogo.setNegativeButton("Cancelar", new Voltar());
                dialogo.setCancelable(false);
                dialogo.create();
                dialogo.show();
            } else {
                AlertDialog.Builder dialogo = new AlertDialog.Builder(MainActivity.this);
                dialogo.setTitle("Deseja sair de sua conta?");
                dialogo.setPositiveButton("Sair", new Sair());
                dialogo.setNegativeButton("Voltar", new Voltar());
                dialogo.setCancelable(false);
                dialogo.create();
                dialogo.show();
            }
        });
    }

    private static class Voltar implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.cancel();
        }
    }

    private class EntrarConta implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private class Sair implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialogInterface, int i) {
            String cnpj = null;

            //enviando cnpj para entrar
            SharedPreferences prefs;
            prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
            SharedPreferences.Editor ed = prefs.edit();
            ed.putString("cnpj", cnpj);
            ed.apply();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}