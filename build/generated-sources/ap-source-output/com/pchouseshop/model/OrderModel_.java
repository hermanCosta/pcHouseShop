package com.pchouseshop.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OrderModel.class)
public abstract class OrderModel_ {

	public static volatile SingularAttribute<OrderModel, Integer> idOrder;
	public static volatile SingularAttribute<OrderModel, Double> total;
	public static volatile SingularAttribute<OrderModel, Double> due;
	public static volatile SingularAttribute<OrderModel, Date> picked;
	public static volatile SingularAttribute<OrderModel, Date> created;
	public static volatile SingularAttribute<OrderModel, Company> company;
	public static volatile SingularAttribute<OrderModel, Date> finished;
	public static volatile SingularAttribute<OrderModel, Employee> employee;
	public static volatile SingularAttribute<OrderModel, Device> device;
	public static volatile SingularAttribute<OrderModel, Customer> customer;
	public static volatile SingularAttribute<OrderModel, String> status;

	public static final String ID_ORDER = "idOrder";
	public static final String TOTAL = "total";
	public static final String DUE = "due";
	public static final String PICKED = "picked";
	public static final String CREATED = "created";
	public static final String COMPANY = "company";
	public static final String FINISHED = "finished";
	public static final String EMPLOYEE = "employee";
	public static final String DEVICE = "device";
	public static final String CUSTOMER = "customer";
	public static final String STATUS = "status";

}

