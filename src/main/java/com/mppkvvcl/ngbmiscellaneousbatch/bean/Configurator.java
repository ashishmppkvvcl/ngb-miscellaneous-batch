package com.mppkvvcl.ngbmiscellaneousbatch.bean;

import com.mppkvvcl.ngbmiscellaneousbatch.interfaces.bean.ConfiguratorInterface;

import javax.persistence.*;

@Table(name = "configurator")
@Entity(name = "Configurator")
public class Configurator implements ConfiguratorInterface {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "property_name")
    private String propertyName;

    @Column(name = "property_value")
    private String propertyValue;

    @Override
    public long getId() {
        return id;
    }

    @Override
    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String getPropertyName() {
        return propertyName;
    }

    @Override
    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    @Override
    public String getPropertyValue() {
        return propertyValue;
    }

    @Override
    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Configurator{");
        sb.append("id=").append(id);
        sb.append(", propertyName='").append(propertyName).append('\'');
        sb.append(", propertyValue='").append(propertyValue).append('\'');
        sb.append('}');
        final String objStr = sb.toString();
        sb.setLength(0);
        return objStr;
    }
}
