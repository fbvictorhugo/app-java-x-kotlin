package net.fbvictorhugo.j.barreirasanitaria.data.dao;

import net.fbvictorhugo.j.barreirasanitaria.data.dao.dummy.BarreiraSanitariaDummyDataBase;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.dummy.UsuarioDummyDataBase;

public class DAOFactory {

    private static DAOFactory sFactory;

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        if (sFactory == null) {
            sFactory = new DAOFactory();
        }
        return sFactory;
    }

    public Object getDataSource(final TabelasDataBase tabela) {
        return instaceDummyDAO(tabela);
    }

    private Object instaceDummyDAO(final TabelasDataBase tabela) {

        switch (tabela) {
            case USUARIO:
                return new UsuarioDummyDataBase();
            case BARREIRA_SANITARIA:
                return new BarreiraSanitariaDummyDataBase();
            default:
                return null;
        }
    }
}
