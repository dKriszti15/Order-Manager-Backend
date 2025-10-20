package edu.bbte.idde.dkim2226.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.dkim2226.dao.exceptions.RepositoryException;
import edu.bbte.idde.dkim2226.model.WebshopOrder;
import edu.bbte.idde.dkim2226.service.OrderService;
import edu.bbte.idde.dkim2226.service.OrderValidationService;
import edu.bbte.idde.dkim2226.service.exceptions.InvalidOrderException;
import edu.bbte.idde.dkim2226.service.exceptions.OrderIdAlreadyExists;
import edu.bbte.idde.dkim2226.service.exceptions.OrderIdNotFoundException;
import edu.bbte.idde.dkim2226.web.exceptions.ErrorMessage;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

@WebServlet("/webshopOrders")
public class WSOrderServlet extends HttpServlet {
    private static final Logger logger = LoggerFactory.getLogger(WSOrderServlet.class);

    private final OrderService orderValidationService = new OrderValidationService();

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("GET /webshopOrders");
        logger.info(objectMapper.writeValueAsString(req.getParameterMap()));
        resp.setContentType("application/json");

        String id = req.getParameter("id");

        if (id == null) {
            objectMapper.writeValue(resp.getWriter(), getWebshopOrders());
        } else {
            WebshopOrder webshopOrder = null;

            try {
                webshopOrder = orderValidationService.findOrderById(Long.parseLong(id));
            } catch (OrderIdNotFoundException e) {
                logger.error(e.getMessage());
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                objectMapper.writeValue(resp.getWriter(), Map.of(
                        "error", "The requested WebshopOrder could not be found."
                ));
            } catch (NumberFormatException e) {
                logger.error(e.getMessage());
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                objectMapper.writeValue(resp.getWriter(), Map.of(
                        "error", "The given ID has wrong format."
                ));
            }

            resp.setStatus(HttpServletResponse.SC_OK);
            objectMapper.writeValue(resp.getWriter(), webshopOrder);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("POST /webshopOrders");
        resp.setContentType("application/json");

        try {
            WebshopOrderDTO wsdto = objectMapper.readValue(req.getInputStream(), WebshopOrderDTO.class);
            logger.info("{}", wsdto);
            if (isDTOCorrectFormat(wsdto)) {
                try {
                    WebshopOrder webshopOrder = new WebshopOrder(
                            wsdto.getId(),
                            new Date(),
                            wsdto.getAddress(),
                            wsdto.getCustomerName(),
                            wsdto.getAmountPaid(),
                            wsdto.getStatus()
                    );
                    orderValidationService.addOrder(webshopOrder);
                    resp.setStatus(HttpServletResponse.SC_OK);
                    objectMapper.writeValue(resp.getWriter(), Map.of("message", "Order created successfully."));
                } catch (InvalidOrderException | OrderIdAlreadyExists e) {
                    logger.error("Error while adding order: {}", e.getMessage());
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    objectMapper.writeValue(resp.getWriter(), Map.of(
                            "error", "Invalid order attributes or order ID already exists."
                    ));
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                objectMapper.writeValue(resp.getWriter(), Map.of(
                        "error", "Missing required attributes in request body."
                ));
            }
        } catch (IOException e) {
            logger.error("Invalid JSON input: {}", e.getMessage());
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), Map.of(
                    "error", "Invalid JSON format."
            ));
        }
    }


    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("PUT /webshopOrders");
        resp.setContentType("application/json");
        try {
            WebshopOrderDTO wsdto = objectMapper.readValue(req.getInputStream(), WebshopOrderDTO.class);
            logger.info("{}", wsdto);

            if (isDTOCorrectFormat(wsdto)) {

                WebshopOrder webshopOrder = new WebshopOrder(
                        wsdto.getId(),
                        new Date(),
                        wsdto.getAddress(),
                        wsdto.getCustomerName(),
                        wsdto.getAmountPaid(),
                        wsdto.getStatus());

                orderValidationService.updateOrder(webshopOrder);
                objectMapper.writeValue(resp.getWriter(), getWebshopOrders());
            }
        } catch (InvalidOrderException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), new ErrorMessage("Wrong attributes."));
        } catch (OrderIdNotFoundException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            objectMapper.writeValue(resp.getWriter(), new ErrorMessage("Order with given ID not found"));
        } catch (NumberFormatException e) {
            logger.error(e.getMessage());
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), Map.of(
                    "error", "The given ID has wrong format."
            ));
        } catch (IOException e) {
            logger.error("Invalid JSON input foramat: {}", e.getMessage());
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), Map.of("error", "Invalid JSON format."));
        }
    }

    private static boolean isDTOCorrectFormat(WebshopOrderDTO wsdto) {
        return wsdto.getId() != null && wsdto.getCustomerName() != null
                && wsdto.getAddress() != null && wsdto.getAmountPaid() != null
                && wsdto.getStatus() != null;
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        logger.info("DELETE /webshopOrders");
        resp.setContentType("application/json");
        String id = req.getParameter("id");

        if (id != null) {

            try {
                orderValidationService.removeOrder(Long.parseLong(id));
                WebshopOrder[] webshopOrders = getWebshopOrders();

                objectMapper.writeValue(resp.getWriter(), webshopOrders);
            } catch (OrderIdNotFoundException e) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                objectMapper.writeValue(resp.getWriter(), new ErrorMessage("Order with given ID not found."));
            } catch (NumberFormatException e) {
                logger.error(e.getMessage());
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                objectMapper.writeValue(resp.getWriter(), Map.of(
                        "error", "The given ID has wrong format."
                ));
            }
        } else {
            logger.error("No ID was given.");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            objectMapper.writeValue(resp.getWriter(), Map.of(
                    "error", "No ID was given."
            ));
        }
    }

    private WebshopOrder[] getWebshopOrders() {
        WebshopOrder[] webshopOrders;

        try {
            webshopOrders = orderValidationService.findAllOrders().toArray(new WebshopOrder[0]);
        } catch (RepositoryException e) {
            webshopOrders = new WebshopOrder[0];
            logger.error(e.getMessage());
        }

        return webshopOrders;
    }
}
