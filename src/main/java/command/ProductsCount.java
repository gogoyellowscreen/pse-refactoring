package command;

import sql.ProductDatabase;

import java.io.PrintWriter;

public class ProductsCount extends Command {

    private final ProductDatabase database;

    ProductsCount(ProductDatabase database) {
        this.database = database;
    }

    @Override
    public void printResult(PrintWriter writer) {
        wrapChecked(() -> {
            writer.println("<html><body>");
            writer.println("Number of products: ");
            writer.println(database.getCount());
            writer.println("</body></html>");
        });
    }
}
