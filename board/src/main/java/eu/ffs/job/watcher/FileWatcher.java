package eu.ffs.job.watcher;

import eu.ffs.job.importData.AbstractImportJobConfig;
import eu.ffs.job.importData.MintosImportJobConfig;
import eu.ffs.job.importData.TwinoImportJobConfig;
import eu.ffs.job.importData.ViventorImportJobConfig;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FilenameFilter;
import java.util.stream.Stream;

import static eu.ffs.Constants.*;

@Service
public class FileWatcher {

    @Autowired
    TwinoImportJobConfig twinoImportJobConfig;

    @Autowired
    ViventorImportJobConfig viventorImportJobConfig;

    @Autowired
    MintosImportJobConfig mintosImportJobConfig;

    @Scheduled(fixedRate = 5000)
    public void perform() {
        File f = new File( "/etc/shared/");
        System.out.println("watching directory for exports: " + f.getPath());
        this.initJobImport(f, twinoImportJobConfig, TWINO_EXPORT_FILENAME_PATTERN);
        this.initJobImport(f, viventorImportJobConfig, VIVENTOR_EXPORT_FILENAME_PATTERN);
        this.initJobImport(f, mintosImportJobConfig, MINTOS_EXPORT_FILENAME_PATTERN);
    }

    private void initJobImport(File f, AbstractImportJobConfig importJob, String fileNamePattern) {
        FilenameFilter textFilter = (dir, name) -> name.toLowerCase().startsWith(fileNamePattern);

        File[] files = f.listFiles(textFilter);
        if (files != null && files.length > 0) {
            System.out.println("Matching files: " + files);
            Stream.of(files).forEach(file -> {
                try {
                    importJob.perform(file.getPath());
                } catch (JobParametersInvalidException e) {
                    e.printStackTrace();
                } catch (JobExecutionAlreadyRunningException e) {
                    e.printStackTrace();
                } catch (JobRestartException e) {
                    e.printStackTrace();
                } catch (JobInstanceAlreadyCompleteException e) {
                    e.printStackTrace();
                }
            });
        }
    }

}
