package mephi.exercise.spring_aop_exercise.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
     * @param pjp контекст аспекта
     * @param rolesAllowed аннотация с разрешенными ролями
     * @param userRoleHeader роли пользователя из хидера
     * @return то что вернул метод если проверка пройдена, иначе пустой результат со статусом 401
     * @throws Throwable если в методе возникнет исключение - пробросим его дальше
     */
    @Around("userRoleAnnotationPointcut(rolesAllowed) && userRoleArgumentPointcut(userRoleHeader)")
    public Object userRoleAnnotationAspect(ProceedingJoinPoint pjp, RolesAllowed rolesAllowed, String userRoleHeader) throws Throwable {
        System.out.println("Person Role: " + userRoleHeader);
        System.out.println("Allowed Role: " + Arrays.toString(rolesAllowed.value()));

        String[] userRoles = userRoleHeader.split(" ");
        for (RolesAllowed.Role roleAllowed : rolesAllowed.value()) {
            for (String userRole : userRoles) {
                if (roleAllowed.name().equals(userRole)) {
                    return pjp.proceed(pjp.getArgs());
                }
            }
        }

        System.out.printf("!! Пользователю с правами %s запрещено выполнять операцию, для которой разрешены права %s",
                userRoleHeader, Arrays.toString(rolesAllowed.value()));
        System.out.println();

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
