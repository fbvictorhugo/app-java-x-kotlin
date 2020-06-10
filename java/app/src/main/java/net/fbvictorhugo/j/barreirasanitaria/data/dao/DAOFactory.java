package net.fbvictorhugo.j.barreirasanitaria.data.dao;

import net.fbvictorhugo.j.barreirasanitaria.data.dao.dummy.DummyBarreiraSanitariaDAO;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.dummy.DummyPessoaDAO;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.dummy.DummyQuestionarioDAO;
import net.fbvictorhugo.j.barreirasanitaria.data.dao.dummy.DummyUsuarioDAO;

public class DAOFactory {

    private static DAOFactory sInstance;

    private DAOFactory() {
    }

    public static DAOFactory getInstance() {
        if (sInstance == null) {
            sInstance = new DAOFactory();
        }
        return sInstance;
    }

    public Object getDataSource(final TabelasDataBase tabela) {
        return instaceDummyDAO(tabela);
    }

    private Object instaceDummyDAO(final TabelasDataBase tabela) {

        switch (tabela) {
            case USUARIO:
                return DummyUsuarioDAO.getInstance();
            case BARREIRA_SANITARIA:
                return DummyBarreiraSanitariaDAO.getInstance();
            case PESSOA:
                return DummyPessoaDAO.getInstance();
            case QUESTIONARIO:
                return DummyQuestionarioDAO.getInstance();

            default:
                return null;
        }
    }
}
