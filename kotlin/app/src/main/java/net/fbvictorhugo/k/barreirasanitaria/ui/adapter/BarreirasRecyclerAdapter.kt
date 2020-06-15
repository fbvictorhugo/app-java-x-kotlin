package net.fbvictorhugo.k.barreirasanitaria.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.adapter_lista_padrao.view.*
import net.fbvictorhugo.k.barreirasanitaria.R
import net.fbvictorhugo.k.barreirasanitaria.data.model.BarreiraSanitaria

class BarreirasRecyclerAdapter : RecyclerView.Adapter<BarreirasRecyclerAdapter.MyViewHolder>() {

    private var _dataset: ArrayList<BarreiraSanitaria> = ArrayList()
    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_lista_padrao, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textViewPrincipal.text = _dataset[position].nome
        holder.textViewSecundario.text = getTextoExibicaoComplemento(_dataset[position])

        holder.itemView.setOnClickListener { onItemClickListener?.onClick(_dataset[position]) }
        holder.itemView.setOnLongClickListener {
            onItemClickListener?.onLongClick(_dataset[position])
            true
        }

    }

    override fun getItemCount(): Int {
        return _dataset.size
    }

    fun atualiza(dataset: ArrayList<BarreiraSanitaria>) {
        _dataset = dataset
        notifyDataSetChanged()
    }

    class MyViewHolder(baseView: View) : RecyclerView.ViewHolder(baseView) {
        val textViewPrincipal: AppCompatTextView = baseView.adapter_item_tv_principal
        val textViewSecundario: AppCompatTextView = baseView.adapter_item_tv_sencundario
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
        fun onClick(barreiraSanitaria: BarreiraSanitaria)
        fun onLongClick(barreiraSanitaria: BarreiraSanitaria)
    }

}
