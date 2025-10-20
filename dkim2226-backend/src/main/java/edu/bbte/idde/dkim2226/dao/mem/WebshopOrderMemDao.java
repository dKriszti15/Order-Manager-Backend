package edu.bbte.idde.dkim2226.dao.mem;

import edu.bbte.idde.dkim2226.dao.WebshopOrderDao;
import edu.bbte.idde.dkim2226.dao.exceptions.RepositoryException;
import edu.bbte.idde.dkim2226.model.WebshopOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebshopOrderMemDao implements WebshopOrderDao {
    protected Map<Long, WebshopOrder> entities = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(WebshopOrderMemDao.class);

    @Override
    public Collection<WebshopOrder> findAll() {
        return entities.values();
    }

    @Override
    public void create(WebshopOrder entity) throws RepositoryException {
        if (entities.containsKey(entity.getId())) {
            logger.warn("Entity with id {} already exists", entity.getId());
            throw new RepositoryException("ID already exists");
        }
        entities.put(entity.getId(), entity);
    }

    @Override
    public void update(WebshopOrder entity) throws RepositoryException {
        if (!entities.containsKey(entity.getId())) {
            logger.error("Entity with id {} does not exist", entity.getId());
            throw new RepositoryException("ID does not exist, so it can't be updated");
        }
        logger.info("Entity with id {} updated succesfully", entity.getId());
        entities.put(entity.getId(), entity);
    }

    @Override
    public void delete(Long id) throws RepositoryException {
        if (!entities.containsKey(id)) {
            logger.error("Entity with this id: {} does not exist", id);
            throw new RepositoryException("ID does not exist");
        }
        logger.info("Entity with id {} deleted succesfully", id);
        entities.remove(id);
    }

    @Override
    public WebshopOrder findById(Long id) throws RepositoryException {
        if (!entities.containsKey(id)) {
            logger.error("Id {} not found", id);
            throw new RepositoryException("ID not exist");
        }

        return entities.get(id);
    }

}
