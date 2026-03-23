import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class Product {
    @JsonProperty("productId")
    private String productId;

    @JsonProperty("name")
    private String name;

    @JsonProperty("price")
    private double price;

    @JsonProperty("inStock")
    private boolean inStock;

    @JsonProperty("specifications")
    private List<Specification> specifications;

    // Конструкторы
    public Product() {
    }

    public Product(String productId, String name, double price, boolean inStock, List<Specification> specifications) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.inStock = inStock;
        this.specifications = specifications;
    }

    // Геттеры и сеттеры
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }

    public List<Specification> getSpecifications() {
        return specifications;
    }

    public void setSpecifications(List<Specification> specifications) {
        this.specifications = specifications;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productId='" + productId + '\'' +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", inStock=" + inStock +
                ", specifications=" + specifications +
                '}';
    }
}