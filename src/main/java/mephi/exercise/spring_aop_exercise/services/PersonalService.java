package mephi.exercise.spring_aop_exercise.services;

import mephi.exercise.spring_aop_exercise.model.Person;

public interface PersonalService {
    Person findById(Long id);

    Person save(Person person);

    void delete(Long id);
}
