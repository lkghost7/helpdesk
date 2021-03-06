package itacademy.domain.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Subdivision {
    private Long id;
    private String name;

    public Subdivision(String name) {
        this.name = name;
    }
}