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
        buildLisaFake();
    }

    private long getProximoIdDisponivel() {
        if (mDummyList.size() == 0) {
            return 1;
        } else {
            return mDummyList.get(mDummyList.size() - 1).getId() + 1;
        }
    }

    @Override
    public List<BarreiraSanitaria> listar() {
        return mDummyList;
    }

    @Override
    public void inserir(BarreiraSanitaria barreiraSanitaria) throws Exception {
        if (barreiraSanitaria == null) {
            throw new NullPointerException("Modelo Barreira Sanitaria está nulo.");
        }

        barreiraSanitaria.setId(getProximoIdDisponivel());
        mDummyList.add(barreiraSanitaria);
    }

    @Override
    public void atualizar(BarreiraSanitaria barreiraSanitaria) throws Exception {
        if (barreiraSanitaria == null) {
            throw new NullPointerException("Modelo Barreira Sanitaria está nulo.");
        }

        for (int i = 0; i < mDummyList.size(); i++) {
            if (mDummyList.get(i).getId() == barreiraSanitaria.getId()) {
                mDummyList.set(i, barreiraSanitaria);
            }
        }
    }

    @Override
    public BarreiraSanitaria procurarBarreira(long barreirId) {
        //Para usar API streams requer minSdkVersion = 24
        for (BarreiraSanitaria barreiraSanitaria : mDummyList) {
            if (barreiraSanitaria.getId() == barreirId) {
                return barreiraSanitaria;
            }
        }
        return null;
    }

    @Override
    public String procurarNomeBarreira(long barreiraId) {
        BarreiraSanitaria barreira = procurarBarreira(barreiraId);
        if (barreira != null) {
            return barreira.getNome();
        } else {
            return "";
        }
    }

    void buildLisaFake() {
        //TODO LISTA FAKE
        BarreiraSanitaria barreira = new BarreiraSanitaria();
        barreira.setId(1);
        barreira.setNome("Acesso Vila Minalda");
        barreira.setCidade("Cataguases");
        barreira.setEstado("MG");
        barreira.setBairro("Vila Minalda");
        barreira.setEndereco("Av. Nicolau Siervi, 1286");
        mDummyList.add(barreira);

        barreira = new BarreiraSanitaria();
        barreira.setId(2);
        barreira.setNome("Acesso MG-285");
        barreira.setCidade("Cataguases");
        barreira.setEstado("MG");
        barreira.setEndereco("Rod Valdir Freitas");
        mDummyList.add(barreira);

    }

}
