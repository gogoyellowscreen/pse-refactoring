package servlet;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import product.Product;
import sql.ProductDatabase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class AddProductServletTest extends ServletTest {

    private final AddProductServlet underTest = new AddProductServlet();

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
    }

    @AfterEach
    void tearDown() {
        super.tearDown();
    }

    @Test
    void doGet() throws Exception {
        String name = "iphone6";
        long price = 300;
        when(request.getParameter(eq("name"))).thenReturn(name);
        when(request.getParameter(eq("price"))).thenReturn(String.valueOf(price));

        underTest.doGet(request, response);
        assertEquals(List.of(new Product(1, name, price)), ProductDatabase.forTesting().getProducts());
        assertEquals(HttpServletResponse.SC_OK, responseStatus);
        assertEquals("text/html", responseContentType);
        assertEquals("OK\n", responseWriter.getBuffer().toString());
    }
}
