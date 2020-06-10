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

    private var _context: Context? = null
    private var _dataset: ArrayList<Pessoa>? = null
    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        _context = parent.context

        val view =
            LayoutInflater.from(_context).inflate(R.layout.adapter_lista_padrao, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textViewPrincipal.text = _dataset?.get(position)?.nome
        val textoCpf = String.format(
            _context?.resources?.getString(R.string.texto_cpf_)!!,
            _dataset?.get(position)?.numeroDocumento
        )
        holder.textViewSecundario.text = textoCpf

        holder.itemView.setOnClickListener { onItemClickListener?.onClick(_dataset?.get(position)) }
        holder.itemView.setOnLongClickListener {
            onItemClickListener?.onLongClick(_dataset?.get(position))
            true
        }

    }

    override fun getItemCount(): Int {
        return _dataset?.size ?: 0
    }

    fun atualiza(newDataset: ArrayList<Pessoa>) {
        _dataset = newDataset
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
