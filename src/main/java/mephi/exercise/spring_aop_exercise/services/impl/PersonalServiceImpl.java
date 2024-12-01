package mephi.exercise.spring_aop_exercise.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import mephi.exercise.spring_aop_exercise.model.Person;
import mephi.exercise.spring_aop_exercise.repositories.PersonalRepository;
import mephi.exercise.spring_aop_exercise.services.PersonalService;

@Service
@RequiredArgsConstructor
public class PersonalServiceImpl implements PersonalService {

    private final PersonalRepository personalRepository;


    @Override
    public Person findById(Long id) {
        return personalRepository.findById(id);
    }

    @Override
    public Person save(Person person) {
        return personalRepository.save(person);
    }

    @Override
    public void delete(Long id) {
        personalRepository.delete(id);
    }
}
