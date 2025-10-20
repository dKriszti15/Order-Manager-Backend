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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(LoginServlet.class);
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    private final OrderService orderValidationService = new OrderValidationService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("/login POST");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        Map<String, Object> model = new ConcurrentHashMap<>();


        if (USERNAME.equals(username) && PASSWORD.equals(password)) {
            req.getSession().setAttribute("username", username);
            req.getSession().setAttribute("loggedIn", true);

            model.put("username", username);
            Template template = HandlebarsTemplateFactory.getTemplate("index");

            WebshopOrder[] webshopOrders = getWebshopOrders();

            model.put("orders", webshopOrders);

            template.apply(model, resp.getWriter());
        } else {
            model.put("error", "Incorrect username or password");
            Template template = HandlebarsTemplateFactory.getTemplate("login");
            template.apply(model, resp.getWriter());
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("/login GET");
        resp.sendRedirect("/showWebshopOrders");
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
