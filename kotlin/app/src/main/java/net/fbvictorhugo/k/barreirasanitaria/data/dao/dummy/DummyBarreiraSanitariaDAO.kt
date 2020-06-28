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

        for (i in _dummyList.indices) {
            if (_dummyList[i].id == barreiraSanitaria.id) {
                _dummyList[i] = barreiraSanitaria
            }
        }

    }

    override fun procurarBarreira(barreiraId: Long): BarreiraSanitaria? {
        return _dummyList
            .find {
                it.id == barreiraId
            }
    }

    override fun procurarNomeBarreira(barreiraId: Long): String {
        return procurarBarreira(barreiraId)?.nome.toString()
    }

    init {
        //TODO LISTA FAKE
        _dummyList.add((BarreiraSanitaria("Alpha", "Cataguases", "MG", 1)))
        _dummyList.add((BarreiraSanitaria("Bravo", "Cataguases", "MG", 2)))
        _dummyList.add((BarreiraSanitaria("Charlie", "Cataguases", "MG", 3)))
    }

}