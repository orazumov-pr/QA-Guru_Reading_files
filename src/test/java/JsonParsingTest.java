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

        Assertions.assertNotNull(product);

    }


}
