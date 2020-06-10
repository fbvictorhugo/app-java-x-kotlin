package net.fbvictorhugo.k.barreirasanitaria.data.dao.dummy

import net.fbvictorhugo.k.barreirasanitaria.data.dao.BarreiraSanitariaDAO
import net.fbvictorhugo.k.barreirasanitaria.data.model.BarreiraSanitaria

object DummyBarreiraSanitariaDAO : BarreiraSanitariaDAO {

    private val _dummyList: ArrayList<BarreiraSanitaria> = ArrayList()

    private fun getProximoIdDisponivel(): Long {
        return if (_dummyList.size == 0) {
            1
        } else {
            _dummyList[_dummyList.size - 1].id + 1
        }
    }

    override fun listar(): ArrayList<BarreiraSanitaria> {
        return _dummyList
    }

    override fun inserir(barreiraSanitaria: BarreiraSanitaria) {
        barreiraSanitaria.id = getProximoIdDisponivel()
        _dummyList.add(barreiraSanitaria)
    }

    override fun atualizar(barreiraSanitaria: BarreiraSanitaria) {
        TODO("Not yet implemented")
    }

}