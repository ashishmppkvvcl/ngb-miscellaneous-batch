package com.mppkvvcl.ngbmiscellaneousbatch.interfaces.bean;

import com.mppkvvcl.ngbinterface.interfaces.BeanInterface;

public interface ConfiguratorInterface extends BeanInterface {

    public long getId();

    public void setId(long id);

    public String getPropertyName();

    public void setPropertyName(String propertyName);

    public String getPropertyValue();

    public void setPropertyValue(String propertyValue);
}
