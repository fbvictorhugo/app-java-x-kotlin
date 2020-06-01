package net.fbvictorhugo.j.barreirasanitaria.data.dao;

import net.fbvictorhugo.j.barreirasanitaria.data.dao.dummy.UsuarioDummyDAO;

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

    public Object getDataSource(Class dataSourceInterface) {
        return instaceDummyDAO(dataSourceInterface);
    }

    private Object instaceDummyDAO(Class dataSourceInterface) {
        if (isSameClass(dataSourceInterface, IUsuarioDAO.class)) {
            return new UsuarioDummyDAO();
        } else {
            return null;
        }
    }

    private boolean isSameClass(Class verifyClass, Class targetClass) {

        if (verifyClass != null && targetClass != null) {
            if (verifyClass.toString().equals(targetClass.toString())) {
                return true;
            }
        }
        return false;
    }
}
