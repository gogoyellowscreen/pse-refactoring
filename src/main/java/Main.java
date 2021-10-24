import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import servlet.AddProductServlet;
import servlet.GetProductsServlet;
import servlet.QueryServlet;
import sql.ProductDatabase;

/**
 * @author akirakozov
 */
public class Main {
    public static void main(String[] args) throws Exception {
        String serverDbName = "server.db";
        ProductDatabase.fromName(serverDbName).createIfNotExist();

        Server server = new Server(8081);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        server.setHandler(context);

        context.addServlet(new ServletHolder(new AddProductServlet(serverDbName)), "/add-product");
        context.addServlet(new ServletHolder(new GetProductsServlet(serverDbName)),"/get-products");
        context.addServlet(new ServletHolder(new QueryServlet(serverDbName)),"/query");

        server.start();
        server.join();
    }
}
