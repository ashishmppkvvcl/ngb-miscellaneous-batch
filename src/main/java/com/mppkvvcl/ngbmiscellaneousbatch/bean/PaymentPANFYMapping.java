package com.mppkvvcl.ngbmiscellaneousbatch.bean;

import com.mppkvvcl.ngbmiscellaneousbatch.interfaces.bean.PaymentPANFYMappingInterface;

import javax.persistence.*;
import java.util.Date;

@Entity(name = "PaymentPANFYMapping")
@Table(name = "payment_pan_fy_mapping")
public class PaymentPANFYMapping implements PaymentPANFYMappingInterface {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "pan")
    private String pan;

    @Column(name = "financial_year")
    private String financialYear;

    @Column(name = "payment_amount")
    private long paymentAmount;

    @Column(name = "reference_payment_id")
    private long referencePaymentId;

    @Column(name = "created_by")
    private String createdBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_on")
    private Date createdOn;

    @Column(name = "updated_by")
    private String updatedBy;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_on")
    private Date updatedOn;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getPan() {
        return pan;
    }

    @Override
    public void setPan(String pan) {
        this.pan = pan;
    }

    @Override
    public String getFinancialYear() {
        return financialYear;
    }

    @Override
    public void setFinancialYear(String financialYear) {
        this.financialYear = financialYear;
    }

    @Override
    public long getPaymentAmount() {
        return paymentAmount;
    }

    @Override
    public void setPaymentAmount(long paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    @Override
    public long getReferencePaymentId() {
        return referencePaymentId;
    }

    @Override
    public void setReferencePaymentId(long referencePaymentId) {
        this.referencePaymentId = referencePaymentId;
    }

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public Date getCreatedOn() {
        return createdOn;
    }

    @Override
    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    @Override
    public String getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    @Override
    public Date getUpdatedOn() {
        return updatedOn;
    }

    @Override
    public void setUpdatedOn(Date updatedOn) {
        this.updatedOn = updatedOn;
    }

    @Override
    public String toString() {
        return "PaymentPANFYMapping{" +
                "id=" + id +
                ", pan='" + pan + '\'' +
                ", financialYear='" + financialYear + '\'' +
                ", paymentAmount=" + paymentAmount +
                ", referencePaymentId=" + referencePaymentId +
                ", createdBy='" + createdBy + '\'' +
                ", createdOn=" + createdOn +
                ", updatedBy='" + updatedBy + '\'' +
                ", updatedOn=" + updatedOn +
                '}';
    }
}
