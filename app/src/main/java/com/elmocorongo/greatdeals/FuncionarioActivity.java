package com.elmocorongo.greatdeals;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FuncionarioActivity extends AppCompatActivity {

    public ListView listaEquipe;
    private final DatabaseReference BD = FirebaseDatabase.getInstance().getReference();
    private FuncionariosAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario);

        listaEquipe = findViewById(R.id.listaEquipe);

        //recebendo cnpj para entrar ou sair
        SharedPreferences prefs;
        prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String cnpj = prefs.getString("cnpj", null);

        //colocando os funcionarios na lista
        DatabaseReference empresas = BD.child("empresas").child(cnpj).child("equipe");
        FirebaseListOptions<Equipe> options = new FirebaseListOptions.Builder<Equipe>()
                .setLayout(R.layout.item_equipe)
                .setQuery(empresas, Equipe.class)
                .setLifecycleOwner(this)
                .build();

        adapter = new FuncionariosAdapter(options);
        listaEquipe.setAdapter(adapter);
        listaEquipe.setOnItemClickListener(new CliqueLista());

    }

    //colocando os dados do funcionários na lista
    private static class FuncionariosAdapter extends FirebaseListAdapter<Equipe> {
        public FuncionariosAdapter(FirebaseListOptions<Equipe> options) {
            super(options);
        }

        @Override
        protected void populateView(View v, Equipe eq, int position) {
            TextView lblNomeFunc  = v.findViewById(R.id.lblNomeFunc);
            TextView lblCPFFunc = v.findViewById(R.id.lblCPFFunc);

            lblNomeFunc.setText(eq.getNome());
            lblCPFFunc.setText(eq.getCpf());
        }
    }

    //quando clicar em um item da lista
    private class CliqueLista implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Equipe eq = adapter.getItem(i);
            Toast.makeText(getApplicationContext(),
                    "Email: " + eq.getEmail()
                    + "\nTurno: " + eq.getTurno()
                    + "\nSalário: " + eq.getSalario()
                    + "\nCargo: " + eq.getCargo(),
                    Toast.LENGTH_SHORT).show();
        }
    }
}