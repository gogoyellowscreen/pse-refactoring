package command;

import product.Product;
import sql.ProductDatabase;

import java.io.PrintWriter;

public class ProductsGet extends Command {

    private final ProductDatabase database;

    ProductsGet(ProductDatabase database) {
        this.database = database;
    }

    @Override
    public void printResult(PrintWriter writer) {
        wrapChecked(() -> {
            writer.println("<html><body>");
            for (Product p : database.getProducts()) {
                writer.println(p.name + "\t" + p.price + "</br>");
            }
            writer.println("</body></html>");
        });
    }
}
