package com.pchouseshop.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OrderNote.class)
public abstract class OrderNote_ {

	public static volatile SingularAttribute<OrderNote, Integer> idOrderNote;
	public static volatile SingularAttribute<OrderNote, String> note;
	public static volatile SingularAttribute<OrderNote, Date> created;
	public static volatile SingularAttribute<OrderNote, Employee> employee;
	public static volatile SingularAttribute<OrderNote, OrderModel> order;

	public static final String ID_ORDER_NOTE = "idOrderNote";
	public static final String NOTE = "note";
	public static final String CREATED = "created";
	public static final String EMPLOYEE = "employee";
	public static final String ORDER = "order";

}

