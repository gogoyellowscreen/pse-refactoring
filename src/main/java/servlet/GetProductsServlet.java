package servlet;

import product.Product;
import sql.ProductDatabase;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author akirakozov
 */
public class GetProductsServlet extends HttpServlet {

    private final ProductDatabase database;

    /*visible for testing*/ GetProductsServlet() {
        database = ProductDatabase.forTesting();
    }

    public GetProductsServlet(String name) {
        database = ProductDatabase.fromName(name);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        try {
            response.getWriter().println("<html><body>");
            for (Product p : database.getProducts()) {
                response.getWriter().println(p.name + "\t" + p.price + "</br>");
            }
            response.getWriter().println("</body></html>");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
