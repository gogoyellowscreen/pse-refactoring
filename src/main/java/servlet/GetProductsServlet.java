package servlet;

import command.Command;
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
        Command.fromNameAndDatabase("get", database)
                .printResult(response.getWriter());

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
