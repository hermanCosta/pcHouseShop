package com.pchouseshop.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Customer.class)
public abstract class Customer_ {

	public static volatile SingularAttribute<Customer, Person> person;
	public static volatile SingularAttribute<Customer, Company> company;
	public static volatile SingularAttribute<Customer, Long> idCustomer;

	public static final String PERSON = "person";
	public static final String COMPANY = "company";
	public static final String ID_CUSTOMER = "idCustomer";

}

