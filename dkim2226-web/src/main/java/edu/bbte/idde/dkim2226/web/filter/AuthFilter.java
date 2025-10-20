package edu.bbte.idde.dkim2226.web.filter;

import com.github.jknack.handlebars.Template;
import edu.bbte.idde.dkim2226.web.servlet.HandlebarsTemplateFactory;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebFilter("/showWebshopOrders")
public class AuthFilter extends HttpFilter {
    private static final Logger logger = LoggerFactory.getLogger(AuthFilter.class);

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {
        logger.info("Authentication filter is checking session");

        Map<String, Object> model = new ConcurrentHashMap<>();

        if (req.getSession().getAttribute("loggedIn") == null) {
            logger.info("User is not logged in, redirecting to login page");

            Template template = HandlebarsTemplateFactory.getTemplate("login");
            template.apply(model, res.getWriter());
        } else {
            chain.doFilter(req, res);
        }
    }
}