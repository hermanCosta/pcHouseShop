package com.pchouseshop.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(RepairOrder.class)
public abstract class RepairOrder_ {

	public static volatile SingularAttribute<RepairOrder, Integer> idEmployee;
	public static volatile SingularAttribute<RepairOrder, Integer> idOrder;
	public static volatile SingularAttribute<RepairOrder, Double> total;
	public static volatile SingularAttribute<RepairOrder, Integer> idCompany;
	public static volatile SingularAttribute<RepairOrder, Date> picked;
	public static volatile SingularAttribute<RepairOrder, Date> created;
	public static volatile SingularAttribute<RepairOrder, Date> finished;
	public static volatile SingularAttribute<RepairOrder, Integer> idDevice;
	public static volatile SingularAttribute<RepairOrder, Integer> idCustomer;
	public static volatile SingularAttribute<RepairOrder, String> status;

	public static final String ID_EMPLOYEE = "idEmployee";
	public static final String ID_ORDER = "idOrder";
	public static final String TOTAL = "total";
	public static final String ID_COMPANY = "idCompany";
	public static final String PICKED = "picked";
	public static final String CREATED = "created";
	public static final String FINISHED = "finished";
	public static final String ID_DEVICE = "idDevice";
	public static final String ID_CUSTOMER = "idCustomer";
	public static final String STATUS = "status";

}

