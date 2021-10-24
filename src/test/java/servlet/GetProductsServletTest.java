package servlet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import product.Product;
import sql.ProductDatabase;

import javax.servlet.http.HttpServletResponse;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GetProductsServletTest extends ServletTest {

    private final GetProductsServlet underTest = new GetProductsServlet();

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
    }

    @Override
    @AfterEach
    void tearDown() {
        super.tearDown();
    }

    @Test
    void doGet_empty() throws Exception {
        String emptyResponse = "<html><body>\n</body></html>\n";

        underTest.doGet(request, response);
        assertEquals(emptyResponse, responseWriter.getBuffer().toString());
        assertEquals(HttpServletResponse.SC_OK, responseStatus);
        assertEquals("text/html", responseContentType);
    }

    @Test
    void doGet_nonEmpty() throws Exception {
        Product product1 = new Product(1, "iphone6", 300);
        Product product2 = new Product(2, "iphone7", 400);
        Product product3 = new Product(3, "iphone8", 500);
        ProductDatabase.forTesting().executeUpdate("INSERT INTO PRODUCT (NAME, PRICE) values " +
                "(\"" + product1.name + "\"," + product1.price + ")," +
                "(\"" + product2.name + "\"," + product2.price + ")," +
                "(\"" + product3.name + "\"," + product3.price + ");");

        String expectedResponse = "<html><body>\n" +
                product1.name + "\t" + product1.price + "</br>\n" +
                product2.name + "\t" + product2.price + "</br>\n" +
                product3.name + "\t" + product3.price + "</br>\n" +
                "</body></html>\n";

        underTest.doGet(request, response);
        assertEquals(expectedResponse, responseWriter.getBuffer().toString());
        assertEquals(HttpServletResponse.SC_OK, responseStatus);
        assertEquals("text/html", responseContentType);
    }
}
