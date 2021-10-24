package command;

import product.Product;
import sql.ProductDatabase;

import java.io.PrintWriter;

public class MaxByPrice extends Command {

    private final ProductDatabase database;

    MaxByPrice(ProductDatabase database) {
        this.database = database;
    }

    @Override
    public void printResult(PrintWriter writer) {
        wrapChecked(() -> {
            writer.println("<html><body>");
            writer.println("<h1>Product with max price: </h1>");
            Product maxPriceProduct = database.getMaxByPrice();
            writer.println(maxPriceProduct.name + "\t" + maxPriceProduct.price + "</br>");
            writer.println("</body></html>");
        });
    }
}
