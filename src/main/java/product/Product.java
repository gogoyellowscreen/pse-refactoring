package product;

public class Product {
    public final int id;
    public final String name;
    public final long price;

    public Product(int id, String name, long price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Product) {
            Product that = (Product) obj;
            return this.id == that.id && this.name.equals(that.name) && this.price == that.price;
        }
        return false;
    }
}
