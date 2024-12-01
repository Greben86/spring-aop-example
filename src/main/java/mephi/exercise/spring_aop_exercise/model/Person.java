package mephi.exercise.spring_aop_exercise.model;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class Person {
    Long id;
    String name;
    String surname;
}
