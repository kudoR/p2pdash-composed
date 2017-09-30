package eu.ffs;

import eu.ffs.controller.MintosDataController;
import eu.ffs.controller.TwinoDataController;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.batch.JobLauncherCommandLineRunner;
import org.springframework.context.annotation.Bean;
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
public class DashboardApp extends JobLauncherCommandLineRunner {

    @Autowired
    ConfigService configService;

    public DashboardApp(JobLauncher jobLauncher, JobExplorer jobExplorer) {
        super(jobLauncher, jobExplorer);

    }

    public static void main(String[] args) {
        for (String s : args) {
            System.out.println(s);
        }
        SpringApplication.run(DashboardApp.class, args);
    }

    @Bean
    ApplicationRunner saveArguments() {
        return (ApplicationArguments arguments) -> {
            String[] sourceArgs = arguments.getSourceArgs();
            if (sourceArgs.length > 0) {
                String mintosFilePath = sourceArgs[0];
                configService.updateInputPath(Constants.CONFIG_REPO_KEY_MINTOS, mintosFilePath);
            }
            if (sourceArgs.length > 1) {
                String twinoFilePath = sourceArgs[1];
                configService.updateInputPath(Constants.CONFIG_REPO_KEY_TWINO, twinoFilePath);
            }
            if (sourceArgs.length > 2) {
                String viventorFilePath = sourceArgs[2];
                configService.updateInputPath(Constants.CONFIG_REPO_KEY_VIVENTOR, viventorFilePath);
            }
        };
    }

}
