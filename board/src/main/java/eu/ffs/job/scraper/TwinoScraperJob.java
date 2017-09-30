package eu.ffs.job.scraper;

import eu.ffs.scraper.TwinoScraper;
import org.apache.xerces.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;

/**
 * Created by j on 16.09.17.
 */
@Service
public class TwinoScraperJob {

    @Value("${ffs.twino.user}")
    private String twinoUser;

    @Value("${ffs.twino.password}")
    private String twinoPassword;

    public void perform() throws MalformedURLException, InterruptedException {
        new TwinoScraper().getExport(twinoUser, new String(Base64.decode(twinoPassword)));
    }
}
