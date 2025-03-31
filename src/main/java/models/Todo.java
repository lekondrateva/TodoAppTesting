package models;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder(toBuilder = true)
@EqualsAndHashCode
@ToString
public class Todo {

    private Long id;
    private String text;
    private boolean completed;

}