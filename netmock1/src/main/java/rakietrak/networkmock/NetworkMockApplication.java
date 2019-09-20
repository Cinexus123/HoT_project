package rakietrak.networkmock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

@SpringBootApplication
public class NetworkMockApplication extends SpringBootServletInitializer {


    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        System.setProperty("spring.config.name", "mock");
        super.onStartup(servletContext);
    }

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "mock");
        SpringApplication.run(NetworkMockApplication.class, args);
    }

}
