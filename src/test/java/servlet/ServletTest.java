package servlet;

import org.mockito.Mock;
import sql.ProductDatabase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class ServletTest {
    protected StringWriter responseWriter;
    protected String responseContentType;
    protected int responseStatus;
    @Mock
    protected HttpServletRequest request;
    @Mock
    protected HttpServletResponse response;

    void setUp() {
        try {
            openMocks(this);
            ProductDatabase.forTesting().createIfNotExist();
            responseWriter = new StringWriter();
            when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
            doAnswer(invocation -> responseStatus = invocation.getArgument(0))
                    .when(response).setStatus(anyInt());
            doAnswer(invocation -> responseContentType = invocation.getArgument(0))
                    .when(response).setContentType(anyString());
        } catch (Exception e) {
            // ignore.
        }
    }

    void tearDown() {
        try {
            ProductDatabase.forTesting().dropTable();
        } catch (Exception e) {
            // ignore.
        }
    }
}
