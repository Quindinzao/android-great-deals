package com.elmocorongo.greatdeals;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistrosActivity extends AppCompatActivity {

    private final DatabaseReference BD = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registros);

        //recebendo cnpj para comprar
        SharedPreferences prefs;
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String cnpj = prefs.getString("cnpj", null);

        ListView listaComprados = findViewById(R.id.listaComprados);

        //Intent intent = getIntent();
        //String key = intent.getStringExtra("key");

        //colocando os produtos na lista
        DatabaseReference empresas = BD.child("empresas").child(cnpj).child("comprados");
        FirebaseListOptions<Produto> options = new FirebaseListOptions.Builder<Produto>()
                .setLayout(R.layout.item_produto)
                .setQuery(empresas, Produto.class)
                .setLifecycleOwner(this)
                .build();

        ProdutoAdapter adapterP = new ProdutoAdapter(options);
        listaComprados.setAdapter(adapterP);
    }

    //produto adapter
    private static class ProdutoAdapter extends FirebaseListAdapter<Produto> {
        public ProdutoAdapter(@NonNull FirebaseListOptions<Produto> options) {
            super(options);
        }

        @Override
        protected void populateView(@NonNull View v, @NonNull Produto p, int position) {
            TextView lblTitulo  = v.findViewById(R.id.lblTitulo);
            TextView lblSubtitulo = v.findViewById(R.id.lblSubtitulo);
            TextView lblPreco = v.findViewById(R.id.lblDescricaoPreco);

            lblTitulo.setText(p.getTitulo());
            lblSubtitulo.setText(p.getSubtitulo());
            lblPreco.setText(p.getPreco());
        }
    }
}