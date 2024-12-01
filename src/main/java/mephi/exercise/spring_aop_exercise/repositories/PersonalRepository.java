package mephi.exercise.spring_aop_exercise.repositories;

import mephi.exercise.spring_aop_exercise.model.Person;

public interface PersonalRepository {
    Person findById(Long id);

    Person save(Person person);

    void delete(Long id);
}
