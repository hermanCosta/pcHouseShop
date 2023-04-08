package com.pchouseshop.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Person.class)
public abstract class Person_ {

	public static volatile SingularAttribute<Person, String> firstName;
	public static volatile SingularAttribute<Person, String> lastName;
	public static volatile SingularAttribute<Person, Long> idPerson;
	public static volatile SingularAttribute<Person, String> email;
	public static volatile SingularAttribute<Person, String> contactNo;

	public static final String FIRST_NAME = "firstName";
	public static final String LAST_NAME = "lastName";
	public static final String ID_PERSON = "idPerson";
	public static final String EMAIL = "email";
	public static final String CONTACT_NO = "contactNo";

}

