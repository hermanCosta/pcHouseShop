package com.pchouseshop.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OrderFault.class)
public abstract class OrderFault_ {

	public static volatile SingularAttribute<OrderFault, Fault> fault;
	public static volatile SingularAttribute<OrderFault, Integer> idOrderFault;
	public static volatile SingularAttribute<OrderFault, OrderModel> order;

	public static final String FAULT = "fault";
	public static final String ID_ORDER_FAULT = "idOrderFault";
	public static final String ORDER = "order";

}

