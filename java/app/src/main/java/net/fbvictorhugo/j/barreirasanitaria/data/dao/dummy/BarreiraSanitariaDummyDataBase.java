package net.fbvictorhugo.j.barreirasanitaria.data.dao.dummy;

import net.fbvictorhugo.j.barreirasanitaria.data.dao.IBarreiraSanitariaDAO;
import net.fbvictorhugo.j.barreirasanitaria.data.model.BarreiraSanitaria;

import java.util.ArrayList;
import java.util.List;

public class BarreiraSanitariaDummyDataBase implements IBarreiraSanitariaDAO {

    private List<BarreiraSanitaria> mDummyList;
    private static BarreiraSanitariaDummyDataBase sFactory;

    public static BarreiraSanitariaDummyDataBase getInstance() {
        if (sFactory == null) {
            sFactory = new BarreiraSanitariaDummyDataBase();
        }
        return sFactory;
    }

    private BarreiraSanitariaDummyDataBase() {
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

    }

    private long getProximoIdDisponivel() {
        if (mDummyList.size() == 0) {
            return 1;
        } else {
            return mDummyList.get(mDummyList.size() - 1).getId() + 1;
        }
    }

    public BarreiraSanitaria procurar(long id) {
        for (BarreiraSanitaria barreira : mDummyList) {
            if (barreira.getId() == id) {
                return barreira;
            }
        }
        return null;
    }

}
