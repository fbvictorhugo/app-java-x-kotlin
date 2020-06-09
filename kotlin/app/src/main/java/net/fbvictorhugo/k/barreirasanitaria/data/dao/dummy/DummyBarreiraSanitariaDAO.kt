package net.fbvictorhugo.k.barreirasanitaria.data.dao.dummy

import net.fbvictorhugo.k.barreirasanitaria.data.dao.BarreiraSanitariaDAO
import net.fbvictorhugo.k.barreirasanitaria.data.model.BarreiraSanitaria

object DummyBarreiraSanitariaDAO : BarreiraSanitariaDAO {

    private val mDummyList: ArrayList<BarreiraSanitaria> = ArrayList()

    override fun listar(): ArrayList<BarreiraSanitaria> {
        return mDummyList
    }

    override fun inserir(barreiraSanitaria: BarreiraSanitaria) {
        barreiraSanitaria.id = getProximoIdDisponivel()
        mDummyList.add(barreiraSanitaria)
    }

    override fun atualizar(barreiraSanitaria: BarreiraSanitaria) {
        TODO("Not yet implemented")
    }

    private fun getProximoIdDisponivel(): Long {
        return if (mDummyList.size == 0) {
            1
        } else {
            mDummyList[mDummyList.size - 1].id + 1
        }
    }

}