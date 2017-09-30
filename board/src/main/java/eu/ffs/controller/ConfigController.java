package eu.ffs.controller;

import eu.ffs.ConfigService;
import eu.ffs.repository.ConfigRepository;
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

        handleSavePathForKey(CONFIG_REPO_KEY_MINTOS, input_path_mintos);
        handleSavePathForKey(CONFIG_REPO_KEY_TWINO, input_path_twino);
        handleSavePathForKey(CONFIG_REPO_KEY_VIVENTOR, input_path_viventor);

        return new ModelAndView("redirect:/");
    }

    private void handleSavePathForKey(String key, String value) {
       configService.updateInputPath(key, value);
    }

}
