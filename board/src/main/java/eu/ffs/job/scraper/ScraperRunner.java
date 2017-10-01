package eu.ffs.job.scraper;

import eu.ffs.repository.ConfigId;
import eu.ffs.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;

/**
 * Created by j on 30.09.17.
 */
@Service
public class ScraperRunner {
    @Autowired
    MintosScraperJob mintosScraperJob;

    @Autowired
    TwinoScraperJob twinoScraperJob;

    @Autowired
    ViventorScraperJob viventorScraperJob;

    @Autowired
    ConfigRepository configRepository;

    @Scheduled(fixedRateString = "${ffs.import.interval}")
    void perform() throws InterruptedException, MalformedURLException {
        if (configRepository.findOne(ConfigId.AUTO_IMPORT_MINTOS).getBooleanValue()) {
            System.out.println("Running MintosScraper.");
            mintosScraperJob.perform();
        }
        if (configRepository.findOne(ConfigId.AUTO_IMPORT_TWINO).getBooleanValue()) {
            System.out.println("Running TwinoScraper");
            twinoScraperJob.perform();
        }
        if (configRepository.findOne(ConfigId.AUTO_IMPORT_VIVENTOR).getBooleanValue()) {
            System.out.println("Running ViventorScraper");
            viventorScraperJob.perform();
        }
    }
}
