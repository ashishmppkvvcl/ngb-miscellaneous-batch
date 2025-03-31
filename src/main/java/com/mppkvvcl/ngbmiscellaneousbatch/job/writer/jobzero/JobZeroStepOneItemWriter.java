package com.mppkvvcl.ngbmiscellaneousbatch.job.writer.jobzero;

import com.mppkvvcl.ngbmiscellaneousbatch.interfaces.bean.ConfiguratorInterface;
import com.mppkvvcl.ngbmiscellaneousbatch.job.holder.jobone.ConfigHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.math.BigDecimal;
import java.util.List;

public class JobZeroStepOneItemWriter implements ItemWriter<ConfiguratorInterface> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobZeroStepOneItemWriter.class);

    public void write(List<? extends ConfiguratorInterface> list) {
        String methodName = "write(): ";
        LOGGER.info(methodName + "called");
        if (list == null || list.size() != 3) {
            LOGGER.error(methodName + "Expected three item in list, but got " + list.size());
            return;
        }

        for (ConfiguratorInterface configurator : list) {
            if ("TDS-194Q-FY-THRESHOLD-AMOUNT".equals(configurator.getPropertyName())) {
                ConfigHolder.TDS_194Q_FY_THRESHOLD_AMOUNT = Long.valueOf(configurator.getPropertyValue());
            } else if ("TDS-194Q-PAN-AVAILABLE-TDS-PERCENTAGE".equals(configurator.getPropertyName())) {
                ConfigHolder.TDS_194Q_PAN_AVAILABLE_TDS_PERCENTAGE = new BigDecimal(configurator.getPropertyValue());
            } else if ("TDS-194Q-PAN-NOT-AVAILABLE-TDS-PERCENTAGE".equals(configurator.getPropertyName())) {
                ConfigHolder.TDS_194Q_PAN_NOT_AVAILABLE_TDS_PERCENTAGE = new BigDecimal(configurator.getPropertyValue());
            }
        }

        LOGGER.info(methodName + "Setting Config TDS_194Q_FY_THRESHOLD_AMOUNT holder to " + ConfigHolder.TDS_194Q_FY_THRESHOLD_AMOUNT);
        LOGGER.info(methodName + "Setting Config TDS_194Q_PAN_AVAILABLE_TDS_PERCENTAGE holder to " + ConfigHolder.TDS_194Q_PAN_AVAILABLE_TDS_PERCENTAGE);
        LOGGER.info(methodName + "Setting Config TDS_194Q_PAN_NOT_AVAILABLE_TDS_PERCENTAGE holder to " + ConfigHolder.TDS_194Q_PAN_NOT_AVAILABLE_TDS_PERCENTAGE);
    }
}
