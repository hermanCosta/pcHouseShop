package com.pchouseshop.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Company.class)
public abstract class Company_ {

	public static volatile SingularAttribute<Company, String> contactOne;
	public static volatile SingularAttribute<Company, String> password;
	public static volatile SingularAttribute<Company, String> address;
	public static volatile SingularAttribute<Company, Long> idCompany;
	public static volatile SingularAttribute<Company, String> name;
	public static volatile SingularAttribute<Company, String> contactTwo;
	public static volatile SingularAttribute<Company, String> email;

	public static final String CONTACT_ONE = "contactOne";
	public static final String PASSWORD = "password";
	public static final String ADDRESS = "address";
	public static final String ID_COMPANY = "idCompany";
	public static final String NAME = "name";
	public static final String CONTACT_TWO = "contactTwo";
	public static final String EMAIL = "email";

}

