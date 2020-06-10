package net.fbvictorhugo.j.barreirasanitaria.data.dao.dummy;

import net.fbvictorhugo.j.barreirasanitaria.data.dao.BarreiraSanitariaDAO;
import net.fbvictorhugo.j.barreirasanitaria.data.model.BarreiraSanitaria;

import java.util.ArrayList;
import java.util.List;

public class DummyBarreiraSanitariaDAO implements BarreiraSanitariaDAO {

    private List<BarreiraSanitaria> mDummyList;
    private static DummyBarreiraSanitariaDAO sInstance;

    public static DummyBarreiraSanitariaDAO getInstance() {
        if (sInstance == null) {
            sInstance = new DummyBarreiraSanitariaDAO();
        }
        return sInstance;
    }

    private DummyBarreiraSanitariaDAO() {
        mDummyList = new ArrayList<>();
    }

    @Override
    public List<BarreiraSanitaria> listar() {
        return mDummyList;
    }

    @Override
    public void inserir(BarreiraSanitaria barreiraSanitaria) throws Exception {
        if (barreiraSanitaria == null) {
            throw new NullPointerException("Modelo BarreiraSanitaria está nulo.");
        }

        barreiraSanitaria.setId(getProximoIdDisponivel());
        mDummyList.add(barreiraSanitaria);
    }

    @Override
    public void atualizar(BarreiraSanitaria barreiraSanitaria) throws Exception {
        //TODO("Not yet implemented")
    }

    private long getProximoIdDisponivel() {
        if (mDummyList.size() == 0) {
            return 1;
        } else {
            return mDummyList.get(mDummyList.size() - 1).getId() + 1;
        }
    }

}
