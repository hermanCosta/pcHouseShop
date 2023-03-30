package com.pchouseshop.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Deposit.class)
public abstract class Deposit_ {

	public static volatile SingularAttribute<Deposit, Double> amount;
	public static volatile SingularAttribute<Deposit, Long> idDeposit;
	public static volatile SingularAttribute<Deposit, OrderModel> order;

	public static final String AMOUNT = "amount";
	public static final String ID_DEPOSIT = "idDeposit";
	public static final String ORDER = "order";

}

