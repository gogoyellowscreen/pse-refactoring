package servlet;

import command.Command;
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
        String commandName = request.getParameter("command");

        Command command = Command.fromNameAndDatabase(commandName, database);
        command.printResult(response.getWriter());

        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
    }
}
