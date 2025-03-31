package com.mppkvvcl.ngbmiscellaneousbatch.service.ngb;

import com.mppkvvcl.ngbdao.daos.AdjustmentDAO;
import com.mppkvvcl.ngbinterface.interfaces.AdjustmentInterface;
import com.mppkvvcl.ngbmiscellaneousbatch.utility.GlobalResources;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdjustmentService {
    private static final Logger LOGGER = GlobalResources.getLogger(AdjustmentService.class);

    @Autowired
    private AdjustmentDAO adjustmentDAO;

    @Autowired
    private GlobalResources globalResources;

    public AdjustmentInterface insert(final AdjustmentInterface adjustment) {
        final String methodName = "insert() : ";
        LOGGER.info(methodName + "called");
        if (adjustment == null) {
            LOGGER.info(methodName + "Input param(s) is null");
            return null;
        }

        setAuditDetails(adjustment);
        LOGGER.info("ADJUSTMENT BEFORE SAVING:::: " + adjustment);
        return null;//TODO:adjustmentDAO.add(adjustment);
    }

    private void setAuditDetails(final AdjustmentInterface adjustment) {
        final String methodName = "setAuditDetails() : ";
        LOGGER.info(methodName + "called");
        if (adjustment != null) {
            adjustment.setCreatedBy(globalResources.getProjectDetail());
            adjustment.setCreatedOn(GlobalResources.getCurrentDate());
        }
    }
}
