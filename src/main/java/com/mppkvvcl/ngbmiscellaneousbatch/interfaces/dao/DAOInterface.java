package com.mppkvvcl.ngbmiscellaneousbatch.interfaces.dao;

public interface DAOInterface<T> {
    public T add(T t);

    public T update(T t);

    public T get(T t);

    public T getById(long id);
}
