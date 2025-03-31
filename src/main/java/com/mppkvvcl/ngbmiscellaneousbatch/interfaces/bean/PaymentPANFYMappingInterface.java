package com.mppkvvcl.ngbmiscellaneousbatch.interfaces.bean;

import java.io.Serializable;
import java.util.Date;

public interface PaymentPANFYMappingInterface extends Serializable {

    public long getId();

    public void setId(long id);

    public String getPan();

    public void setPan(String pan);

    public String getFinancialYear();

    public void setFinancialYear(String financialYear);

    public long getPaymentAmount();

    public void setPaymentAmount(long paymentAmount);

    public long getReferencePaymentId();

    public void setReferencePaymentId(long referencePaymentId);

    public String getCreatedBy();

    public void setCreatedBy(String createdBy);

    public Date getCreatedOn();

    public void setCreatedOn(Date createdOn);

    public String getUpdatedBy();

    public void setUpdatedBy(String updatedBy);

    public Date getUpdatedOn();

    public void setUpdatedOn(Date updatedOn);
}
