package mephi.exercise.spring_aop_exercise.repositories.impl;

import org.springframework.stereotype.Repository;
import mephi.exercise.spring_aop_exercise.model.Person;
import mephi.exercise.spring_aop_exercise.repositories.PersonalRepository;

@Repository
public class PersonalRepositoryImpl implements PersonalRepository {

    @Override
    public Person findById(Long id) {
        return new Person(id, "Иван", "Иванов");
    }

    @Override
    public Person save(Person person) {
        System.out.println("save person");
        return person;
    }

    @Override
    public void delete(Long id) {
        System.out.println("delete person");
    }
}
