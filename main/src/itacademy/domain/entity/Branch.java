package itacademy.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class Branch {
    private Long id;
    private String name;
    private String address;

    public Branch(String name) {
        this.name = name;
    }

    public Branch(String name, String address) {
        this.name = name;
        this.address = address;
    }
}