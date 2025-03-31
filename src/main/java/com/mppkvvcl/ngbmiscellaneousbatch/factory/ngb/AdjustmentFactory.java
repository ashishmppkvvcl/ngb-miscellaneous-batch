package com.mppkvvcl.ngbmiscellaneousbatch.factory.ngb;

import com.mppkvvcl.ngbentity.beans.Adjustment;
import com.mppkvvcl.ngbinterface.interfaces.AdjustmentInterface;
import com.mppkvvcl.ngbmiscellaneousbatch.utility.MiscellaneousBatchConstants;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class AdjustmentFactory {

    public static AdjustmentInterface build() {
        return new Adjustment();
    }

    public static AdjustmentInterface build(final BigDecimal amount, final String locationCode, final String consumerNo, final long ngbPaymentId) {

        if (amount == null || locationCode == null) {
            return null;
        }

        final AdjustmentInterface adjustment = new Adjustment();
        adjustment.setCode(116);
        adjustment.setConsumerNo(consumerNo);
        adjustment.setLocationCode(locationCode);
        adjustment.setAmount(amount.setScale(3, RoundingMode.HALF_UP));
        adjustment.setPosted(MiscellaneousBatchConstants.FALSE);
        adjustment.setDeleted(MiscellaneousBatchConstants.FALSE);
        adjustment.setApprovalStatus(MiscellaneousBatchConstants.STATUS_APPROVED);
        adjustment.setRemark("" + ngbPaymentId);

        return adjustment;
    }
}
