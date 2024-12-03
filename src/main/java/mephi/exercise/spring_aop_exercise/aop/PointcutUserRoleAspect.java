package mephi.exercise.spring_aop_exercise.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import mephi.exercise.spring_aop_exercise.annotation.RolesAllowed;

import java.util.Arrays;

/**
 * АОП-обработчик
 */
@Aspect
@Component
public class PointcutUserRoleAspect {

    /**
     * Пойнткат для аннотации - список разрешенных ролей для метода
     *
     * @param rolesAllowed аннотация {@link RolesAllowed}
     */
    @Pointcut("@annotation(mephi.exercise.spring_aop_exercise.annotation.RolesAllowed) && @annotation(rolesAllowed)")
    public void userRoleAnnotationPointcut(RolesAllowed rolesAllowed) {
    }

    /**
     * Пойнткат для аргумента - хидер с ролями пользователя
     *
     * @param userRoleHeader список ролей пользователя из хидера
     */
    @Pointcut("args(java.lang.String,..) && args(userRoleHeader,..)")
    public void userRoleArgumentPointcut(String userRoleHeader) {
    }

    /**
     * Проверка прав пользователя
     *
     * @param rolesAllowed аннотация с разрешенными ролями
     * @param userRoleHeader роли пользователя из хидера
     * @throws Throwable если недостаточно прав на выполнение метода - кидаем возникнет исключение {@link AccessDeniedException}
     */
    @Before("userRoleAnnotationPointcut(rolesAllowed) && userRoleArgumentPointcut(userRoleHeader)")
    public void userRoleAnnotationAspect(RolesAllowed rolesAllowed, String userRoleHeader) throws AccessDeniedException {
        System.out.println("Person Role: " + userRoleHeader);
        System.out.println("Allowed Role: " + Arrays.toString(rolesAllowed.value()));

        String[] userRoles = userRoleHeader.split(" ");
        for (RolesAllowed.Role roleAllowed : rolesAllowed.value()) {
            for (String userRole : userRoles) {
                if (roleAllowed.name().equals(userRole)) {
                    return;
                }
            }
        }

        String errorMessage = String.format("!! Пользователю с правами %s запрещено выполнять операцию, для которой разрешены права %s",
                userRoleHeader, Arrays.toString(rolesAllowed.value()));
        System.out.println(errorMessage);

        throw new AccessDeniedException(errorMessage);
    }
}
