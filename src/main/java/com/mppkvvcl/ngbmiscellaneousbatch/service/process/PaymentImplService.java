package com.mppkvvcl.ngbmiscellaneousbatch.service.process;

import com.mppkvvcl.ngbinterface.interfaces.AdjustmentInterface;
import com.mppkvvcl.ngbinterface.interfaces.ConsumerInformationInterface;
import com.mppkvvcl.ngbinterface.interfaces.PaymentInterface;
import com.mppkvvcl.ngbmiscellaneousbatch.bean.PaymentConsumerFYMapping;
import com.mppkvvcl.ngbmiscellaneousbatch.bean.PaymentPANFYMapping;
import com.mppkvvcl.ngbmiscellaneousbatch.factory.ngb.AdjustmentFactory;
import com.mppkvvcl.ngbmiscellaneousbatch.interfaces.bean.PaymentConsumerFYMappingInterface;
import com.mppkvvcl.ngbmiscellaneousbatch.interfaces.bean.PaymentPANFYMappingInterface;
import com.mppkvvcl.ngbmiscellaneousbatch.job.holder.jobone.ConfigHolder;
import com.mppkvvcl.ngbmiscellaneousbatch.service.mis.PaymentConsumerFYMappingService;
import com.mppkvvcl.ngbmiscellaneousbatch.service.mis.PaymentPANFYMappingService;
import com.mppkvvcl.ngbmiscellaneousbatch.service.ngb.ConsumerInformationService;
import com.mppkvvcl.ngbmiscellaneousbatch.utility.GlobalResources;
import com.mppkvvcl.ngbmiscellaneousbatch.utility.MiscellaneousBatchUtility;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service
public class PaymentImplService {
    private static final Logger LOGGER = GlobalResources.getLogger(PaymentImplService.class);

    @Autowired
    private ConsumerInformationService consumerInformationService;

    @Autowired
    private PaymentPANFYMappingService paymentPANFYMappingService;

    @Autowired
    private PaymentConsumerFYMappingService paymentConsumerFYMappingService;

    public Map<String, Object> process(final PaymentInterface ngbPayment) {
        final String methodName = "process() : ";
        LOGGER.info(methodName + "called");

        if (ngbPayment == null) {
            LOGGER.info(methodName + "Input param(s) is null");
            return null;
        }

        final Map<String, Object> outMap = new HashMap<>();
        final String consumerNo = ngbPayment.getConsumerNo();
        final ConsumerInformationInterface consumerInformation = consumerInformationService.getByConsumerNo(consumerNo);
        if (consumerInformation == null) {
            LOGGER.info(methodName + "Consumer information not available. Returning");
            return null;
        }
        final String financialYear = MiscellaneousBatchUtility.getFinancialYearByDate(ngbPayment.getPayDate());
        if (StringUtils.isEmpty(financialYear)) {
            LOGGER.error(methodName + "Financial year calculation failed. Returning null");
            return null;
        }

        final String pan = consumerInformation.getPan();
        if (!StringUtils.isEmpty(pan)) {
            //Creating Adjustment by PAN
            PaymentPANFYMappingInterface paymentPANFYMapping = paymentPANFYMappingService.getByPANAndFinancialYear(pan, financialYear);

            Long baselineAmountToDeduct = ConfigHolder.TDS_194Q_FY_THRESHOLD_AMOUNT;
            if (paymentPANFYMapping == null) {
                paymentPANFYMapping = new PaymentPANFYMapping();
                paymentPANFYMapping.setPan(pan);
                paymentPANFYMapping.setFinancialYear(financialYear);
                paymentPANFYMapping.setPaymentAmount(ngbPayment.getAmount());
                paymentPANFYMapping.setReferencePaymentId(ngbPayment.getId());
            } else {
                if (paymentPANFYMapping.getPaymentAmount() > ConfigHolder.TDS_194Q_FY_THRESHOLD_AMOUNT) {
                    baselineAmountToDeduct = new Long(paymentPANFYMapping.getPaymentAmount());
                }

                paymentPANFYMapping.setPaymentAmount(paymentPANFYMapping.getPaymentAmount() + ngbPayment.getAmount());
                paymentPANFYMapping.setReferencePaymentId(ngbPayment.getId());
            }
            outMap.put("PAYMENT_BY_PAN", paymentPANFYMapping);

            if (paymentPANFYMapping.getPaymentAmount() > ConfigHolder.TDS_194Q_FY_THRESHOLD_AMOUNT) {
                long exceededAmount = paymentPANFYMapping.getPaymentAmount() - baselineAmountToDeduct;
                final BigDecimal adjustmentAmount = new BigDecimal(exceededAmount / 100).multiply(ConfigHolder.TDS_194Q_PAN_AVAILABLE_TDS_PERCENTAGE);
                final AdjustmentInterface adjustment = AdjustmentFactory.build(adjustmentAmount, ngbPayment.getLocationCode(), consumerNo, ngbPayment.getId());
                outMap.put("ADJUSTMENT", adjustment);
            }
        } else {
            //Creating Adjustment by Consumer No
            PaymentConsumerFYMappingInterface paymentConsumerFYMapping = paymentConsumerFYMappingService.getByConsumerAndFinancialYear(consumerNo, financialYear);

            Long baselineAmountToDeduct = ConfigHolder.TDS_194Q_FY_THRESHOLD_AMOUNT;
            if (paymentConsumerFYMapping == null) {
                paymentConsumerFYMapping = new PaymentConsumerFYMapping();
                paymentConsumerFYMapping.setConsumerNo(consumerNo);
                paymentConsumerFYMapping.setFinancialYear(financialYear);
                paymentConsumerFYMapping.setPaymentAmount(ngbPayment.getAmount());
                paymentConsumerFYMapping.setReferencePaymentId(ngbPayment.getId());
            } else {
                if (paymentConsumerFYMapping.getPaymentAmount() > ConfigHolder.TDS_194Q_FY_THRESHOLD_AMOUNT) {
                    baselineAmountToDeduct = new Long(paymentConsumerFYMapping.getPaymentAmount());
                }

                paymentConsumerFYMapping.setPaymentAmount(paymentConsumerFYMapping.getPaymentAmount() + ngbPayment.getAmount());
                paymentConsumerFYMapping.setReferencePaymentId(ngbPayment.getId());
            }
            outMap.put("PAYMENT_BY_CONSUMER", paymentConsumerFYMapping);

            if (paymentConsumerFYMapping.getPaymentAmount() > ConfigHolder.TDS_194Q_FY_THRESHOLD_AMOUNT) {
                long exceededAmount = paymentConsumerFYMapping.getPaymentAmount() - baselineAmountToDeduct;
                final BigDecimal adjustmentAmount = new BigDecimal(exceededAmount / 100).multiply(ConfigHolder.TDS_194Q_PAN_NOT_AVAILABLE_TDS_PERCENTAGE);
                final AdjustmentInterface adjustment = AdjustmentFactory.build(adjustmentAmount, ngbPayment.getLocationCode(), consumerNo, ngbPayment.getId());
                outMap.put("ADJUSTMENT", adjustment);
            }
        }

        return outMap;
    }
}
