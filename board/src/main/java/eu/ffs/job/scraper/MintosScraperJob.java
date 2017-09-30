package eu.ffs.job.scraper;

import eu.ffs.repository.CredentialRepository;
import eu.ffs.repository.entity.CredentialId;
import eu.ffs.repository.entity.Credentials;
import eu.ffs.scraper.MintosScraper;
import org.apache.xerces.impl.dv.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.util.Optional;

/**
 * Created by j on 16.09.17.
 */
@Service
public class MintosScraperJob {

    @Autowired
    CredentialRepository credentialRepository;

    public void perform() throws MalformedURLException, InterruptedException {
        Optional<Credentials> credentials = java.util.Optional.ofNullable(credentialRepository.findOne(CredentialId.MINTOS));
        if (credentials.isPresent()) {
            new MintosScraper().getExport(
                    credentials.get().getCredentialToken().getUser(),
                    new String(Base64.decode(credentials.get().getCredentialToken().getPassword())));
        }
    }
}
