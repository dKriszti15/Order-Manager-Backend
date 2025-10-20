package edu.bbte.idde.dkim2226.dao.db;

import edu.bbte.idde.dkim2226.dao.DaoFactory;
import edu.bbte.idde.dkim2226.dao.WebshopOrderDao;

public class JdbcDaoFactory extends DaoFactory {
    private WebshopOrderJdbcDao webshopOrderDao;

    @Override
    public synchronized WebshopOrderDao getWebshopOrderDao() {
        if (webshopOrderDao == null) {
            webshopOrderDao = new WebshopOrderJdbcDao();
        }
        return webshopOrderDao;
    }
}
