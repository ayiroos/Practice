package sooriya.gdrive.viewdocsapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
        "sooriya.gdrive"
})
public class ViewDocsApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(ViewDocsApiApplication.class, args);
    }

}
