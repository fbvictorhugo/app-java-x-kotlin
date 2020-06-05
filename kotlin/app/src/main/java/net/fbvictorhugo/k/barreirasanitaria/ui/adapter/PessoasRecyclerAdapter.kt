package net.fbvictorhugo.k.barreirasanitaria.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import net.fbvictorhugo.k.barreirasanitaria.R
import net.fbvictorhugo.k.barreirasanitaria.data.model.Pessoa

class PessoasRecyclerAdapter : RecyclerView.Adapter<PessoasRecyclerAdapter.MyViewHolder>() {

    private var context: Context? = null
    private var mDataset: ArrayList<Pessoa>? = null
    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        context = parent.context

        val view =
            LayoutInflater.from(context).inflate(R.layout.adapter_lista_padrao, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textViewPrincipal.text = mDataset?.get(position)?.nome
        val textoCpf = String.format(
            context?.resources?.getString(R.string.texto_cpf_)!!,
            mDataset?.get(position)?.numeroDocumento
        )
        holder.textViewSecundario.text = textoCpf

        holder.itemView.setOnClickListener { onItemClickListener?.onClick(mDataset?.get(position)) }
        holder.itemView.setOnLongClickListener {
            onItemClickListener?.onLongClick(mDataset?.get(position))
            true
        }

    }

    override fun getItemCount(): Int {
        return mDataset?.size ?: 0
    }

    fun atualiza(newDataset: ArrayList<Pessoa>) {
        mDataset = newDataset
        notifyDataSetChanged()
    }

    class MyViewHolder(baseView: View) : RecyclerView.ViewHolder(baseView) {
        val textViewPrincipal: AppCompatTextView =
            baseView.findViewById(R.id.adapter_item_tv_principal)
        val textViewSecundario: AppCompatTextView =
            baseView.findViewById(R.id.adapter_item_tv_sencundario)
    }

    interface OnItemClickListener {
        fun onClick(pessoa: Pessoa?)
        fun onLongClick(pessoa: Pessoa?)
    }

}
