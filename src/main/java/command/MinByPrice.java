package command;

import product.Product;
import sql.ProductDatabase;

import java.io.PrintWriter;

public class MinByPrice extends Command {

    private final ProductDatabase database;

    MinByPrice(ProductDatabase database) {
        this.database = database;
    }

    @Override
    public void printResult(PrintWriter writer) {
        wrapChecked(() -> {
            writer.println("<html><body>");
            writer.println("<h1>Product with min price: </h1>");
            Product minPriceProduct = database.getMinByPrice();
            writer.println(minPriceProduct.name + "\t" + minPriceProduct.price + "</br>");
            writer.println("</body></html>");
        });
    }
}
