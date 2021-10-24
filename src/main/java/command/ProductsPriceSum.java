package command;

import sql.ProductDatabase;

import java.io.PrintWriter;

public class ProductsPriceSum extends Command {

    private final ProductDatabase database;

    ProductsPriceSum(ProductDatabase database) {
        this.database = database;
    }

    @Override
    public void printResult(PrintWriter writer) {
        wrapChecked(() -> {
            writer.println("<html><body>");
            writer.println("Summary price: ");
            writer.println(database.getSum());
            writer.println("</body></html>");
        });
    }
}
