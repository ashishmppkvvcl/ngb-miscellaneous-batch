package com.mppkvvcl.ngbmiscellaneousbatch;

import com.mppkvvcl.ngbmiscellaneousbatch.utility.GlobalResources;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
public class NgbMiscellaneousBatchApplication {
    //private static Logger LOGGER = LoggerFactory.getLogger(NgbMiscellaneousBatchApplication.class);
    //private static ApplicationContext APPLICATION_CONTEXT;

    public static void main(String[] args) {
        setLoggingFile();
        SpringApplication.run(NgbMiscellaneousBatchApplication.class, args);

        //APPLICATION_CONTEXT = SpringApplication.run(NgbMiscellaneousBatchApplication.class, args);
        //test();
    }

    private static void setLoggingFile() {
        String loggingFile = System.getenv("LOGGER_HOME") + "/ngb-miscellaneous-batch/" + GlobalResources.getBatchName();
        System.setProperty("logging.file", loggingFile);
    }

    /*private static void test() {
        APPLICATION_CONTEXT.getBean(JobScheduler.class).launchMasterBatch();
    }*/
}
