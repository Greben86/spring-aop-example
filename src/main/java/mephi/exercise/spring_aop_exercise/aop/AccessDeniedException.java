package mephi.exercise.spring_aop_exercise.aop;

/**
 * Исключение, кидаем если произошла попытка выполнить метод с неправильными правами
 */
public class AccessDeniedException extends IllegalAccessException {
    public AccessDeniedException(String message) {
        super(message);
    }
}
