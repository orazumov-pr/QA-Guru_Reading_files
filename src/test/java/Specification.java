import com.fasterxml.jackson.annotation.JsonProperty;

public class Specification {
    @JsonProperty("name")
    private String name;

    @JsonProperty("value")
    private String value;

    // Конструкторы
    public Specification() {
    }

    public Specification(String name, String value) {
        this.name = name;
        this.value = value;
    }

    // Геттеры и сеттеры
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Specification{" +
                "name='" + name + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}