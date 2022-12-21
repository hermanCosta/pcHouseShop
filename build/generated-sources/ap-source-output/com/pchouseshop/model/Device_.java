package com.pchouseshop.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Device.class)
public abstract class Device_ {

	public static volatile SingularAttribute<Device, String> serialNumber;
	public static volatile SingularAttribute<Device, String> model;
	public static volatile SingularAttribute<Device, Integer> idDevice;
	public static volatile SingularAttribute<Device, String> brand;

	public static final String SERIAL_NUMBER = "serialNumber";
	public static final String MODEL = "model";
	public static final String ID_DEVICE = "idDevice";
	public static final String BRAND = "brand";

}

