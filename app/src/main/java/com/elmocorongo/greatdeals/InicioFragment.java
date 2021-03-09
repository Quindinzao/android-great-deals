package com.elmocorongo.greatdeals;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class InicioFragment extends Fragment {

    public ListView listaItens;
    private final DatabaseReference BD = FirebaseDatabase.getInstance().getReference();
    private ProdutoAdapter adapterP;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);

        listaItens = view.findViewById(R.id.listaItens);

        //colocando os produtos na lista
        DatabaseReference geral = BD.child("geral");
        FirebaseListOptions<Produto> options = new FirebaseListOptions.Builder<Produto>()
                .setLayout(R.layout.item_produto)
                .setQuery(geral, Produto.class)
                .setLifecycleOwner(this)
                .build();

        adapterP = new ProdutoAdapter(options);
        listaItens.setAdapter(adapterP);
        listaItens.setOnItemClickListener(new CliqueLista());

        return view;
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
            TextView lblDescricao = v.findViewById(R.id.lblDescricaoPreco);

            lblTitulo.setText(p.getTitulo());
            lblSubtitulo.setText(p.getSubtitulo());
            lblDescricao.setText(p.getDescricao());
        }
    }

    //quando clicar em um item da lista
    private class CliqueLista implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Produto p = adapterP.getItem(i);

            String titulo, subtitulo, tipo, descricao, preco;

            titulo = p.getTitulo();
            subtitulo = p.getSubtitulo();
            tipo = p.getTipo();
            descricao = p.getDescricao();
            preco = p.getPreco();

            Intent intent = new Intent(getContext(), ProdutoUmActivity.class);
            intent.putExtra("titulo", titulo);
            intent.putExtra("subtitulo", subtitulo);
            intent.putExtra("tipo", tipo);
            intent.putExtra("descricao", descricao);
            intent.putExtra("preco", preco);
            startActivity(intent);
        }
    }
}
