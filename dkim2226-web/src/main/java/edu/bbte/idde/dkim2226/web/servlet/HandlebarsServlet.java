package edu.bbte.idde.dkim2226.web.servlet;

import com.github.jknack.handlebars.Template;
import edu.bbte.idde.dkim2226.dao.exceptions.RepositoryException;
import edu.bbte.idde.dkim2226.model.WebshopOrder;
import edu.bbte.idde.dkim2226.service.OrderService;
import edu.bbte.idde.dkim2226.service.OrderValidationService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/showWebshopOrders")
public class HandlebarsServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(HandlebarsServlet.class);
    private final OrderService orderValidationService = new OrderValidationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("/ GET --------- HandlebarsServlet ----------");

        Map<String, Object> model = new ConcurrentHashMap<>();

        WebshopOrder[] webshopOrders = getWebshopOrders();
        logger.info(Arrays.toString(webshopOrders));
        model.put("orders", webshopOrders);

        Template template = HandlebarsTemplateFactory.getTemplate("index");
        template.apply(model, resp.getWriter());

    }

    private WebshopOrder[] getWebshopOrders() {
        WebshopOrder[] webshopOrders;

        try {
            webshopOrders = orderValidationService.findAllOrders().toArray(new WebshopOrder[0]);
        } catch (RepositoryException e) {
            webshopOrders = new WebshopOrder[0];
            logger.warn(e.getMessage());
        }

        return webshopOrders;
    }
}


