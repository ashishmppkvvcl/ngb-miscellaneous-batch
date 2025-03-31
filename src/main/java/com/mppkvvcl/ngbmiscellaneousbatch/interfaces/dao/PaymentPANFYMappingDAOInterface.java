package com.mppkvvcl.ngbmiscellaneousbatch.interfaces.dao;

public interface PaymentPANFYMappingDAOInterface<T> extends DAOInterface<T> {

    public T getByPanAndFinancialYear(String pan, String financialYear);
}
