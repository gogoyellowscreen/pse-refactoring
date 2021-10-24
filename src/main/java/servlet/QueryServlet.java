package servlet;

import product.Product;
import sql.ProductDatabase;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

/**
 * @author akirakozov
 */
public class QueryServlet extends HttpServlet {

    private final ProductDatabase database;

    /*visible for testing*/ QueryServlet() {
        database = ProductDatabase.forTesting();
    }

    public QueryServlet(String name) {
        database = ProductDatabase.fromName(name);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String command = request.getParameter("command");

        try {
            if ("max".equals(command)) {
                response.getWriter().println("<html><body>");
                response.getWriter().println("<h1>Product with max price: </h1>");
                Product maxPriceProduct = database.getMaxByPrice();
                response.getWriter().println(maxPriceProduct.name + "\t" + maxPriceProduct.price + "</br>");
                response.getWriter().println("</body></html>");
            } else if ("min".equals(command)) {
                response.getWriter().println("<html><body>");
                response.getWriter().println("<h1>Product with min price: </h1>");
                Product minPriceProduct = database.getMinByPrice();
                response.getWriter().println(minPriceProduct.name + "\t" + minPriceProduct.price + "</br>");
                response.getWriter().println("</body></html>");
            } else if ("sum".equals(command)) {
                response.getWriter().println("<html><body>");
                response.getWriter().println("Summary price: ");
                response.getWriter().println(database.getSum());
                response.getWriter().println("</body></html>");
            } else if ("count".equals(command)) {
                response.getWriter().println("<html><body>");
                response.getWriter().println("Number of products: ");
                response.getWriter().println(database.getCount());
                response.getWriter().println("</body></html>");
            } else {
                response.getWriter().println("Unknown command: " + command);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
