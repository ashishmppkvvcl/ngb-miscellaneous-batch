package com.mppkvvcl.ngbmiscellaneousbatch.service.ngb;

import com.mppkvvcl.ngbdao.daos.BatchCounterDAO;
import com.mppkvvcl.ngbinterface.interfaces.BatchCounterInterface;
import com.mppkvvcl.ngbmiscellaneousbatch.utility.GlobalResources;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class BatchCounterService {
    private static final Logger LOGGER = GlobalResources.getLogger(BatchCounterService.class);

    @Autowired
    private BatchCounterDAO counterDAO;

    @Autowired
    private GlobalResources globalResources;

    public BatchCounterInterface insert(final BatchCounterInterface counter) {
        final String methodName = "insert() : ";
        LOGGER.info(methodName + "called");
        if (counter == null) {
            LOGGER.info(methodName + "Input param(s) is null");
            return null;
        }

        setAuditDetails(counter);
        return counterDAO.add(counter);
    }

    private void setAuditDetails(final BatchCounterInterface counter) {
        final String methodName = "setAuditDetails() : ";
        LOGGER.info(methodName + "called");
        if (counter != null) {
            counter.setUpdatedBy(globalResources.getProjectDetail());
            counter.setUpdatedOn(GlobalResources.getCurrentDate());
        }
    }

    @Transactional("ngbTransactionManager")
    public Integer updateReferenceIdByHeader(long referenceId, String header) {
        final String methodName = "updateReferenceIdByHeader() : ";
        LOGGER.info(methodName + "called ");
        if (StringUtils.isEmpty(header)) {
            LOGGER.info(methodName + "Input param(s) is null");
            return -1;
        }

        return counterDAO.updateReferenceIdByHeader(referenceId, globalResources.getProjectDetail(), GlobalResources.getCurrentDate(), header);
    }
}
