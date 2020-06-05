package net.fbvictorhugo.k.barreirasanitaria.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import net.fbvictorhugo.k.barreirasanitaria.R
import net.fbvictorhugo.k.barreirasanitaria.data.model.BarreiraSanitaria

class BarreirasRecyclerAdapter : RecyclerView.Adapter<BarreirasRecyclerAdapter.MyViewHolder>() {

    private var mDataset: ArrayList<BarreiraSanitaria>? = null
    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_lista_padrao, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textViewPrincipal.text = mDataset?.get(position)?.nome
        holder.textViewSecundario.text = getTextoExibicaoComplemento(mDataset?.get(position))

        holder.itemView.setOnClickListener { onItemClickListener?.onClick(mDataset?.get(position)) }
        holder.itemView.setOnLongClickListener {
            onItemClickListener?.onLongClick(mDataset?.get(position))
            true
        }

    }

    override fun getItemCount(): Int {
        return mDataset?.size ?: 0
    }

    fun atualiza(newDataset: ArrayList<BarreiraSanitaria>) {
        mDataset = newDataset
        notifyDataSetChanged()
    }

    class MyViewHolder(baseView: View) : RecyclerView.ViewHolder(baseView) {
        val textViewPrincipal: AppCompatTextView =
            baseView.findViewById(R.id.adapter_item_tv_principal)
        val textViewSecundario: AppCompatTextView =
            baseView.findViewById(R.id.adapter_item_tv_sencundario)
    }

    private fun getTextoExibicaoComplemento(barreiraSanitaria: BarreiraSanitaria?): String {
        return when (barreiraSanitaria) {
            null -> ""
            else -> return if (barreiraSanitaria.descricao.isEmpty()) {
                "${barreiraSanitaria.cidade} / ${barreiraSanitaria.estado}"
            } else {
                barreiraSanitaria.descricao
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(barreiraSanitaria: BarreiraSanitaria?)
        fun onLongClick(barreiraSanitaria: BarreiraSanitaria?)
    }

}
