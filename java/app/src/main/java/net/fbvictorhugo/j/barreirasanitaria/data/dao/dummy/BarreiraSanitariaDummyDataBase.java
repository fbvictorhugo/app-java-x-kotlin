package net.fbvictorhugo.j.barreirasanitaria.data.dao.dummy;

import net.fbvictorhugo.j.barreirasanitaria.data.dao.IBarreiraSanitariaDAO;
import net.fbvictorhugo.j.barreirasanitaria.data.model.BarreiraSanitaria;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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

        BarreiraSanitaria barreiraSanitaria = new BarreiraSanitaria();
        barreiraSanitaria.setId(new Random().nextLong());
        barreiraSanitaria.setNome("Barreira 267");
        barreiraSanitaria.setDescricao("Fica na avenida");
        barreiraSanitaria.setCidade("Cataguases");
        barreiraSanitaria.setBairro("Centro");
        barreiraSanitaria.setEstado("MG");
        barreiraSanitaria.setEndereco("Rua Major Vieira");

        mDummyList.add(barreiraSanitaria);

        barreiraSanitaria = new BarreiraSanitaria();
        barreiraSanitaria.setId(new Random().nextLong());
        barreiraSanitaria.setNome("Barreira 52");
        barreiraSanitaria.setDescricao("bloqueio azul");
        barreiraSanitaria.setCidade("Cataguases");
        barreiraSanitaria.setBairro("Haide");
        barreiraSanitaria.setEstado("MG");
        barreiraSanitaria.setEndereco("Rua Major Vieira");

        mDummyList.add(barreiraSanitaria);
    }

    @Override
    public List<BarreiraSanitaria> listar() {
        return mDummyList;
    }

}
