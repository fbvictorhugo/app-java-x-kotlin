package net.fbvictorhugo.j.barreirasanitaria.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.fbvictorhugo.j.barreirasanitaria.R;
import net.fbvictorhugo.j.barreirasanitaria.data.model.Pessoa;

import java.util.List;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

public class PessoasRecyclerAdapter extends RecyclerView.Adapter<PessoasRecyclerAdapter.MyViewHolder> {

    private List<Pessoa> mDataset;
    private OnItemClickListener onItemClickListener;
    private Context mContext;

    public PessoasRecyclerAdapter() {
    }

    @Override
    public PessoasRecyclerAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_lista_padrao, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        holder.textViewPrincipal.setText(mDataset.get(position).getNome());
        String textoCpf = String.format(mContext.getResources().getString(R.string.texto_cpf_), String.valueOf(mDataset.get(position).getNumeroDocumento()));
        holder.textViewSecundario.setText(textoCpf);
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

    public void adiciona(Pessoa pessoa) {
        if (mDataset != null) {
            mDataset.add(pessoa);
            notifyDataSetChanged();
        }
    }

    public void atualiza(List<Pessoa> newDataset) {
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

    public interface OnItemClickListener {

        void onClick(Pessoa pessoa);

        void onLongClick(Pessoa pessoa);
    }

}
