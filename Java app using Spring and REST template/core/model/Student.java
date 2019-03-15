package lab5.catalog.core.model;

import lombok.*;

import javax.persistence.Entity;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString(callSuper = true)
public class Student extends BaseEntity<Long> {
    private String name;
    private int grupa;
}
