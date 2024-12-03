package mephi.exercise.spring_aop_exercise.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import mephi.exercise.spring_aop_exercise.aop.AccessDeniedException;

/**
 * Контроллер для перехвата исключений доступа к методам {@link AccessDeniedException}
 */
@ControllerAdvice
public class AccessDeniedAdvice {

    /**
     * Если поймали исключение {@link AccessDeniedException}, то возвращаем статус 401
     *
     * @return ответ со статусом 401
     */
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Void> handleException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
