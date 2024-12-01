package mephi.exercise.spring_aop_exercise.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import mephi.exercise.spring_aop_exercise.annotation.RolesAllowed;
import mephi.exercise.spring_aop_exercise.model.Person;
import mephi.exercise.spring_aop_exercise.services.PersonalService;

@RequiredArgsConstructor
@RestController
@RequestMapping("api")
public class PersonalController {

    public static final String USER_ROLE_HEADER = "X-USER-ROLE";

    private final PersonalService personalService;

    /**
     * Получение объекта по идентификатору
     *
     * @param userRoleHeader роли пользователя из хидера
     * @param id идентификатор
     * @return найденный объект со статусом
     */
    @RolesAllowed({RolesAllowed.Role.USER, RolesAllowed.Role.ADMIN})
    @GetMapping(value = "/person/{id}/get", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> findById(@RequestHeader(USER_ROLE_HEADER) String userRoleHeader, @PathVariable("id") Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(personalService.findById(id));
    }

    /**
     * Сохранение объекта
     *
     * @param userRoleHeader роли пользователя из хидера
     * @param person объект
     * @return сохраненный объект со статусом
     */
    @RolesAllowed({RolesAllowed.Role.USER, RolesAllowed.Role.ADMIN})
    @PostMapping(value = "/person/save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Person> save(@RequestHeader(USER_ROLE_HEADER) String userRoleHeader, @RequestBody Person person) {
        return ResponseEntity.status(HttpStatus.OK).body(personalService.save(person));
    }

    /**
     * Удаление объекта по идентификатору
     *
     * @param userRoleHeader роли пользователя из хидера
     * @param id идентификатор
     * @return статус
     */
    @RolesAllowed({RolesAllowed.Role.ADMIN})
    @DeleteMapping(value = "/person/{id}/delete")
    public ResponseEntity<Void> delete(@RequestHeader(USER_ROLE_HEADER) String userRoleHeader, @PathVariable("id") Long id) {
        personalService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
