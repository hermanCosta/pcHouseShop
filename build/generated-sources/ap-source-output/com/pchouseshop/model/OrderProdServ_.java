package com.pchouseshop.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(OrderProdServ.class)
public abstract class OrderProdServ_ {

	public static volatile SingularAttribute<OrderProdServ, ProductService> prodServ;
	public static volatile SingularAttribute<OrderProdServ, Double> total;
	public static volatile SingularAttribute<OrderProdServ, Integer> qty;
	public static volatile SingularAttribute<OrderProdServ, Long> idOrderProdServ;
	public static volatile SingularAttribute<OrderProdServ, OrderModel> order;

	public static final String PROD_SERV = "prodServ";
	public static final String TOTAL = "total";
	public static final String QTY = "qty";
	public static final String ID_ORDER_PROD_SERV = "idOrderProdServ";
	public static final String ORDER = "order";

}

