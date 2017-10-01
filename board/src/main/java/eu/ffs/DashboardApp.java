package eu.ffs;

import eu.ffs.controller.MintosDataController;
import eu.ffs.controller.TwinoDataController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan(basePackageClasses = {
        TwinoDataController.class,
        MintosDataController.class,
        DashboardApp.class})
@EnableJpaRepositories("eu.ffs.repository")
@EnableScheduling
public class DashboardApp {

    public static void main(String[] args) {
        SpringApplication.run(DashboardApp.class, args);
    }
}
