package com.mppkvvcl.ngbmiscellaneousbatch.interfaces.dao;

public interface PaymentConsumerFYMappingDAOInterface<T> extends DAOInterface<T> {

    public T getByConsumerNoAndFinancialYear(String consumerNo, String financialYear);
}
