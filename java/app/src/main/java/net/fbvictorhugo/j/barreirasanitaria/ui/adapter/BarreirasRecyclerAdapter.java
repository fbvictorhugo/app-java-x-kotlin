package net.fbvictorhugo.j.barreirasanitaria.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import net.fbvictorhugo.j.barreirasanitaria.R;
import net.fbvictorhugo.j.barreirasanitaria.data.model.BarreiraSanitaria;

import java.util.List;

public class BarreirasRecyclerAdapter extends RecyclerView.Adapter<BarreirasRecyclerAdapter.MyViewHolder> {

    private List<BarreiraSanitaria> mDataset;

    public BarreirasRecyclerAdapter() {
    }

    @Override
    public BarreirasRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_lista_padrao, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.textViewPrincipal.setText(mDataset.get(position).getNome());
        holder.textViewSecundario.setText(mDataset.get(position).getDescricao());
    }

    @Override
    public int getItemCount() {
        return mDataset != null ? mDataset.size() : 0;
    }

    public void adiciona(BarreiraSanitaria barreiraSanitaria) {
        if (mDataset != null) {
            mDataset.add(barreiraSanitaria);
            notifyDataSetChanged();
        }
    }

    public void atualiza(List<BarreiraSanitaria> myDataset) {
        mDataset = myDataset;
        notifyDataSetChanged();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView textViewPrincipal;
        AppCompatTextView textViewSecundario;

        MyViewHolder(View baseView) {
            super(baseView);
            textViewPrincipal = baseView.findViewById(R.id.adapter_item_tv_principal);
            textViewSecundario = baseView.findViewById(R.id.adapter_item_tv_sencundario);
        }
    }

}
