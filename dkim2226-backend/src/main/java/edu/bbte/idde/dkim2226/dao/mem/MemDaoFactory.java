package edu.bbte.idde.dkim2226.dao.mem;

import edu.bbte.idde.dkim2226.dao.DaoFactory;
import edu.bbte.idde.dkim2226.dao.WebshopOrderDao;

public class MemDaoFactory extends DaoFactory {

    @Override
    public WebshopOrderDao getWebshopOrderDao() {
        return new WebshopOrderMemDao();
    }
}
