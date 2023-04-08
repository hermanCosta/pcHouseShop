package com.pchouseshop.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(Employee.class)
public abstract class Employee_ {

	public static volatile SingularAttribute<Employee, Long> idEmployee;
	public static volatile SingularAttribute<Employee, String> password;
	public static volatile SingularAttribute<Employee, String> accessLevel;
	public static volatile SingularAttribute<Employee, Person> person;
	public static volatile SingularAttribute<Employee, String> username;

	public static final String ID_EMPLOYEE = "idEmployee";
	public static final String PASSWORD = "password";
	public static final String ACCESS_LEVEL = "accessLevel";
	public static final String PERSON = "person";
	public static final String USERNAME = "username";

}

