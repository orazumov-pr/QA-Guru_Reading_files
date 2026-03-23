import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.io.InputStream;


public class JsonParsingTest {

    private final ClassLoader cl = JsonParsingTest.class.getClassLoader();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void parseProductJsonFromInputStreamTest() throws IOException {

        try (InputStream is = cl.getResourceAsStream("product.json")) {
            Product product = objectMapper.readValue(is, Product.class);
            validateProduct(product);
        }
    }

    private void validateProduct(Product product) {
        System.out.println("Разобранный продукт: " + product);

        // Проверяем основные поля
        Assertions.assertNotNull(product);
        Assertions.assertEquals("PRD-001", product.getProductId());
        Assertions.assertEquals("Ноутбук", product.getName());
        Assertions.assertEquals(79999.99, product.getPrice(), 0.001);
        Assertions.assertTrue(product.isInStock());

        // Проверяем массив спецификаций
        Assertions.assertNotNull(product.getSpecifications());
        Assertions.assertEquals(5, product.getSpecifications().size());

        // Выводим все спецификации
        System.out.println("\nСпецификации продукта:");
        for (Specification spec : product.getSpecifications()) {
            System.out.println("  " + spec.getName() + ": " + spec.getValue());
        }
    }


}
