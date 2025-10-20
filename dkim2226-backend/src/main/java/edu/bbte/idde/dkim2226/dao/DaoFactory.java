package edu.bbte.idde.dkim2226.dao;

import edu.bbte.idde.dkim2226.dao.db.JdbcDaoFactory;
import edu.bbte.idde.dkim2226.dao.mem.MemDaoFactory;
import edu.bbte.idde.dkim2226.utils.ConfigurationFactory;

public abstract class DaoFactory {

    private static volatile DaoFactory instance;

    public static synchronized DaoFactory getInstance(String typeOfDao) {
        if (instance == null) {
            String profile = ConfigurationFactory.getMainConfiguration().getDao();

            if ("jdbc".equals(profile)) {
                instance = new JdbcDaoFactory();
            } else if ("mem".equals(profile)) {
                instance = new MemDaoFactory();
            }
        }
        return instance;
    }

    public abstract WebshopOrderDao getWebshopOrderDao();
}
