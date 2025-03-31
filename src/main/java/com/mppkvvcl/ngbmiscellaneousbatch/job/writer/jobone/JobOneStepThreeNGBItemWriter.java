package com.mppkvvcl.ngbmiscellaneousbatch.job.writer.jobone;

import com.mppkvvcl.ngbentity.beans.Adjustment;
import com.mppkvvcl.ngbinterface.interfaces.AdjustmentInterface;
import com.mppkvvcl.ngbmiscellaneousbatch.service.ngb.AdjustmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Map;

public class JobOneStepThreeNGBItemWriter implements ItemWriter<Map<String, Object>> {

    private static final Logger LOGGER = LoggerFactory.getLogger(JobOneStepThreeNGBItemWriter.class);

    @Autowired
    private AdjustmentService adjustmentService;

    public void write(List<? extends Map<String, Object>> list) {
        String methodName = "write(): ";
        LOGGER.info(methodName + "called");
        if (list == null || list.size() == 0) {
            LOGGER.info(methodName + "No items to write");
            return;
        }

        int adjustmentInsertCount = 0;

        for (Map<String, Object> entityMap : list) {
            if (entityMap == null) continue;

            AdjustmentInterface adjustment = (Adjustment) entityMap.get("ADJUSTMENT");
            if (adjustment != null) {
                adjustmentService.insert(adjustment);
                adjustmentInsertCount++;
            }
        }

        LOGGER.info(methodName + "Size of List is : " + list.size());
        LOGGER.info(methodName + "Adjustment Insert Count " + adjustmentInsertCount);
    }
}
