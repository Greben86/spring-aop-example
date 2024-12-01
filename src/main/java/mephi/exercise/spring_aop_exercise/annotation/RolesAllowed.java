package mephi.exercise.spring_aop_exercise.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для указания списка ролей, кому можно пользоваться методом
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RolesAllowed {
    /**
     * Список ролей
     *
     * @return массив ролей
     */
    Role[] value();

    /**
     * Роли пользователей
     */
    enum Role {
        USER, ADMIN;
    }
}
