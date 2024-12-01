package mephi.exercise.spring_aop_exercise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy
public class SpringAopExerciseApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAopExerciseApplication.class, args);
	}

}
