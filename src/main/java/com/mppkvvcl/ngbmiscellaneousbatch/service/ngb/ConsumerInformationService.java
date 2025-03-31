package com.mppkvvcl.ngbmiscellaneousbatch.service.ngb;

import com.mppkvvcl.ngbdao.daos.ConsumerInformationDAO;
import com.mppkvvcl.ngbinterface.interfaces.ConsumerInformationInterface;
import com.mppkvvcl.ngbmiscellaneousbatch.utility.GlobalResources;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ConsumerInformationService {
    private static final Logger logger = GlobalResources.getLogger(ConsumerInformationService.class);

    @Autowired
    private ConsumerInformationDAO consumerInformationDAO;

    public ConsumerInformationInterface getByConsumerNo(String consumerNo) {
        final String methodName = "getByConsumerNoIn() : ";
        logger.info(methodName + "called");
        if (StringUtils.isEmpty(consumerNo)) {
            logger.info(methodName + "Input param(s) is null");
            return null;
        }

        return consumerInformationDAO.getByConsumerNo(consumerNo);
    }
}
