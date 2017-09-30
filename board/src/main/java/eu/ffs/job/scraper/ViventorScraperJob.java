package eu.ffs.job.scraper;

import eu.ffs.scraper.ViventorScraper;
import org.apache.xerces.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;

/**
 * Created by j on 16.09.17.
 */
@Service
public class ViventorScraperJob {

    @Value("${ffs.viventor.user}")
    private String viventorUser;

    @Value("${ffs.viventor.password}")
    private String viventorPassword;

    public void perform() throws MalformedURLException, InterruptedException {
        new ViventorScraper().getExport(viventorUser, new String(Base64.decode(viventorPassword)));
    }
}
