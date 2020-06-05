package net.fbvictorhugo.j.barreirasanitaria.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.fbvictorhugo.j.barreirasanitaria.R;
import net.fbvictorhugo.j.barreirasanitaria.data.model.BarreiraSanitaria;

import java.util.List;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class BarreirasRecyclerAdapter extends RecyclerView.Adapter<BarreirasRecyclerAdapter.MyViewHolder> {

    private List<BarreiraSanitaria> mDataset;
    private OnItemClickListener onItemClickListener;

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
        holder.textViewSecundario.setText(getTextoExibicaoComplemento(mDataset.get(position)));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onClick(mDataset.get(position));
                }
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onLongClick(mDataset.get(position));
                }
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDataset != null ? mDataset.size() : 0;
    }

    public void atualiza(List<BarreiraSanitaria> newDataset) {
        mDataset = newDataset;
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

    public void setOnItemClickListener(OnItemClickListener listener) {
        onItemClickListener = listener;
    }

    private String getTextoExibicaoComplemento(BarreiraSanitaria barreiraSanitaria) {
        if (barreiraSanitaria != null) {
            if (barreiraSanitaria.getDescricao().isEmpty()) {
                return
                        barreiraSanitaria.getCidade() + " / " + barreiraSanitaria.getEstado();
            } else {
                return barreiraSanitaria.getDescricao();
            }
        } else return "";
    }

    public interface OnItemClickListener {
        void onClick(BarreiraSanitaria barreiraSanitaria);

        void onLongClick(BarreiraSanitaria barreiraSanitaria);
    }

}
