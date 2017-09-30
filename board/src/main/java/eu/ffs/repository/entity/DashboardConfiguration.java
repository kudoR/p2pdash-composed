package eu.ffs.repository.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Created by j on 05.05.17.
 */
@Entity
public class DashboardConfiguration implements Serializable {
    @Id
    String configurationId;

    public DashboardConfiguration() {
    }

    public DashboardConfiguration(String configurationId) {
        this.configurationId = configurationId;
    }

    public String getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(String configurationId) {
        this.configurationId = configurationId;
    }

    private String stringValue;

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }
}
