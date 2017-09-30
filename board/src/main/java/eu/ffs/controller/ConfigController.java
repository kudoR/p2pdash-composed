package eu.ffs.controller;

import eu.ffs.ConfigService;
import eu.ffs.repository.ConfigRepository;
import eu.ffs.repository.CredentialRepository;
import eu.ffs.repository.entity.CredentialId;
import eu.ffs.repository.entity.CredentialToken;
import eu.ffs.repository.entity.Credentials;
import eu.ffs.repository.entity.DashboardConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;

import static eu.ffs.Constants.*;

/**
 * Created by j on 06.05.17.
 */
@RestController
public class ConfigController {
    @Autowired
    ConfigRepository configRepository;

    @Autowired
    CredentialRepository credentialRepository;

    @Autowired
    ConfigService configService;

    @RequestMapping("/p2pconfig")
    public Object getConfiguration() {
        Iterable<DashboardConfiguration> configurations = configRepository.findAll();
        HashMap<String, String> configMap = new HashMap<>();
        for (DashboardConfiguration configuration : configurations) {
            configMap.put(configuration.getConfigurationId(), configuration.getStringValue());
        }
        return configMap;
    }

    @PostMapping("/saveInputPath")
    public ModelAndView handleSavePathInput(
            @RequestParam("input_path_twino") String input_path_twino,
            @RequestParam("input_path_mintos") String input_path_mintos,
            @RequestParam("input_path_viventor") String input_path_viventor) {

        handleSaveSetting(CONFIG_REPO_KEY_MINTOS, input_path_mintos);
        handleSaveSetting(CONFIG_REPO_KEY_TWINO, input_path_twino);
        handleSaveSetting(CONFIG_REPO_KEY_VIVENTOR, input_path_viventor);

        return new ModelAndView("redirect:/");
    }

    private void handleSaveSetting(String key, String value) {
        configService.updateInputPath(key, value);
    }

    @PostMapping("/saveCredentials")
    public ModelAndView handleSaveCredentials(
            @RequestParam("twinoUser") String twinoUser,
            @RequestParam("twinoPw") String twinoPw,
            @RequestParam("mintosUser") String mintosUser,
            @RequestParam("mintosPw") String mintosPw,
            @RequestParam("viventorUser") String viventorUser,
            @RequestParam("viventorPw") String viventorPw) {
        saveOrUpdateCredentials(CredentialId.TWINO, twinoUser, twinoPw);
        saveOrUpdateCredentials(CredentialId.MINTOS, mintosUser, mintosPw);
        saveOrUpdateCredentials(CredentialId.VIVENTOR, viventorUser, viventorPw);
        return new ModelAndView("redirect:/");

    }

    private void saveOrUpdateCredentials(CredentialId credentialId, String user, String pw) {
        Credentials credentials = credentialRepository.findOne(credentialId);
        if (credentials == null) {
            credentials = new Credentials();
            credentials.setCredentialId(credentialId);
        }
        CredentialToken credentialToken = new CredentialToken();
        credentialToken.setUser(user);
        credentialToken.setPassword(pw);
        credentials.setCredentialToken(credentialToken);
        credentialRepository.save(credentials);
    }

}
