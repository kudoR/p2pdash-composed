package eu.ffs.job.scraper;

import eu.ffs.scraper.MintosScraper;
import org.apache.xerces.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;

/**
 * Created by j on 16.09.17.
 */
@Service
public class MintosScraperJob {

    @Value("${ffs.mintos.user}")
    private String mintosUser;

    @Value("${ffs.mintos.password}")
    private String mintosPassword;

    public void perform() throws MalformedURLException, InterruptedException {
        new MintosScraper().getExport(mintosUser, new String(Base64.decode(mintosPassword)));
    }
}
