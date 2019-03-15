package lab5.catalog.client;

import lab5.catalog.client.ui.ClientConsole;
import lab5.catalog.web.dto.StudentDto;
import lab5.catalog.web.dto.StudentsDto;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.client.RestTemplate;

public class ClientApp {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(
                        "lab5.catalog.client.config"
                );
        RestTemplate restTemplate = context.getBean(RestTemplate.class);

        ClientConsole clientConsole = context.getBean(ClientConsole.class);
        clientConsole.runConsole();


        StudentsDto result = restTemplate.getForObject(
                "http://localhost:8080/api/students",
                StudentsDto.class
        );
        System.out.println(result);

        System.out.println("bye ");
    }
}
