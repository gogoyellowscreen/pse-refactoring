package servlet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import product.Product;
import sql.ProductDatabase;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class QueryServletTest extends ServletTest {

    private final Product product1 = new Product(1, "iphone6", 300);
    private final Product product2 = new Product(2, "iphone7", 400);
    private final Product product3 = new Product(3, "iphone8", 500);

    private final QueryServlet underTest = new QueryServlet();

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        fillProductDatabase();
    }

    @Override
    @AfterEach
    void tearDown() {
        super.tearDown();
    }

    @Test
    void doGet_max() throws Exception {
        when(request.getParameter(eq("command"))).thenReturn("max");
        String expectedResponse = "<html><body>\n" +
                "<h1>Product with max price: </h1>\n" +
                product3.name + "\t" + product3.price + "</br>\n" +
                "</body></html>\n";

        underTest.doGet(request, response);
        assertEquals(expectedResponse, responseWriter.getBuffer().toString());
        assertEquals(HttpServletResponse.SC_OK, responseStatus);
        assertEquals("text/html", responseContentType);
    }

    @Test
    void doGet_min() throws Exception {
        when(request.getParameter(eq("command"))).thenReturn("min");
        String expectedResponse = "<html><body>\n" +
                "<h1>Product with min price: </h1>\n" +
                product1.name + "\t" + product1.price + "</br>\n" +
                "</body></html>\n";

        underTest.doGet(request, response);
        assertEquals(expectedResponse, responseWriter.getBuffer().toString());
        assertEquals(HttpServletResponse.SC_OK, responseStatus);
        assertEquals("text/html", responseContentType);
    }

    @Test
    void doGet_sum() throws Exception {
        when(request.getParameter(eq("command"))).thenReturn("sum");
        String expectedResponse = "<html><body>\n" +
                "Summary price: \n" + (product1.price + product2.price + product3.price) + "\n" +
                "</body></html>\n";

        underTest.doGet(request, response);
        assertEquals(expectedResponse, responseWriter.getBuffer().toString());
        assertEquals(HttpServletResponse.SC_OK, responseStatus);
        assertEquals("text/html", responseContentType);
    }

    @Test
    void doGet_count() throws Exception {
        when(request.getParameter(eq("command"))).thenReturn("count");
        String expectedResponse = "<html><body>\n" +
                "Number of products: \n3\n" +
                "</body></html>\n";

        underTest.doGet(request, response);
        assertEquals(expectedResponse, responseWriter.getBuffer().toString());
        assertEquals(HttpServletResponse.SC_OK, responseStatus);
        assertEquals("text/html", responseContentType);
    }

    private void fillProductDatabase() {
        try {
            ProductDatabase.forTesting().executeUpdate("INSERT INTO PRODUCT (NAME, PRICE) values " +
                    "(\"" + product1.name + "\"," + product1.price + ")," +
                    "(\"" + product2.name + "\"," + product2.price + ")," +
                    "(\"" + product3.name + "\"," + product3.price + ");");
        } catch (SQLException e) {
            // ignore.
        }
    }
}
