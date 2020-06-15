package net.fbvictorhugo.k.barreirasanitaria.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.adapter_lista_padrao.view.*
import net.fbvictorhugo.k.barreirasanitaria.R
import net.fbvictorhugo.k.barreirasanitaria.data.model.Pessoa

class PessoasRecyclerAdapter(private val _context: Context) :
    RecyclerView.Adapter<PessoasRecyclerAdapter.MyViewHolder>() {

    private var _dataset: ArrayList<Pessoa> = ArrayList()
    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(_context).inflate(R.layout.adapter_lista_padrao, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.textViewPrincipal.text = _dataset[position].nome
        val textoCpf = String.format(
            _context.resources.getString(R.string.texto_cpf_), _dataset[position].numeroDocumento
        )
        holder.textViewSecundario.text = textoCpf
        holder.itemView.setOnClickListener { onItemClickListener?.onClick(_dataset[position]) }
        holder.itemView.setOnLongClickListener {
            onItemClickListener?.onLongClick(_dataset[position])
            true
        }

    }

    override fun getItemCount(): Int {
        return _dataset.size
    }

    fun atualiza(dataset: ArrayList<Pessoa>) {
        _dataset = dataset
        notifyDataSetChanged()
    }

    class MyViewHolder(baseView: View) : RecyclerView.ViewHolder(baseView) {
        val textViewPrincipal: AppCompatTextView = baseView.adapter_item_tv_principal
        val textViewSecundario: AppCompatTextView = baseView.adapter_item_tv_sencundario
    }

    interface OnItemClickListener {
        fun onClick(pessoa: Pessoa)
        fun onLongClick(pessoa: Pessoa)
    }

}
