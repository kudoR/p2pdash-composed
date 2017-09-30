package eu.ffs.job.scraper;

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

    @Scheduled(fixedRateString = "${ffs.import.interval}")
    void perform() throws InterruptedException, MalformedURLException {
        System.out.println("Running MintosScraper.");
        mintosScraperJob.perform();
        System.out.println("Running TwinoScraper");
        twinoScraperJob.perform();
        System.out.println("Running ViventorScraper");
        viventorScraperJob.perform();
    }
}
