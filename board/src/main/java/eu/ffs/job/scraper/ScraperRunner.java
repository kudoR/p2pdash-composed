package eu.ffs.job.scraper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${ffs.twino.scraper.enabled}")
    private boolean twinoEnabled;

    @Value("${ffs.viventor.scraper.enabled}")
    private boolean viventorEnabled;

    @Value("${ffs.mintos.scraper.enabled}")
    private boolean mintosEnabled;

    @Scheduled(fixedRateString = "${ffs.import.interval}")
    void perform() throws InterruptedException, MalformedURLException {

        if (mintosEnabled) {
            System.out.println("Running MintosScraper.");
            mintosScraperJob.perform();
        }

        if (twinoEnabled) {
            System.out.println("Running TwinoScraper");
            twinoScraperJob.perform();
        }

        if (viventorEnabled) {
            System.out.println("Running ViventorScraper");
            viventorScraperJob.perform();
        }
    }
}
