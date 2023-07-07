package com.pchouseshop.model;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OrderPayment.class)
public abstract class OrderPayment_ {

	public static volatile SingularAttribute<OrderPayment, Long> IdOrderPayment;
	public static volatile SingularAttribute<OrderPayment, Double> amountDued;
	public static volatile SingularAttribute<OrderPayment, Double> amountPaid;
	public static volatile SingularAttribute<OrderPayment, String> payMethod;
	public static volatile SingularAttribute<OrderPayment, Double> change;
	public static volatile SingularAttribute<OrderPayment, Date> dtTransaction;
	public static volatile SingularAttribute<OrderPayment, OrderModel> order;

	public static final String ID_ORDER_PAYMENT = "IdOrderPayment";
	public static final String AMOUNT_DUED = "amountDued";
	public static final String AMOUNT_PAID = "amountPaid";
	public static final String PAY_METHOD = "payMethod";
	public static final String CHANGE = "change";
	public static final String DT_TRANSACTION = "dtTransaction";
	public static final String ORDER = "order";

}

