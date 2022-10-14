package com.pchouseshop.model;

import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value = "org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor")
@StaticMetamodel(ProductService.class)
public abstract class ProductService_ {

	public static volatile SingularAttribute<ProductService, String> note;
	public static volatile SingularAttribute<ProductService, Integer> idCompany;
	public static volatile SingularAttribute<ProductService, Double> price;
	public static volatile SingularAttribute<ProductService, Integer> qty;
	public static volatile SingularAttribute<ProductService, Integer> idProductService;
	public static volatile SingularAttribute<ProductService, String> category;
	public static volatile SingularAttribute<ProductService, String> prodServName;

	public static final String NOTE = "note";
	public static final String ID_COMPANY = "idCompany";
	public static final String PRICE = "price";
	public static final String QTY = "qty";
	public static final String ID_PRODUCT_SERVICE = "idProductService";
	public static final String CATEGORY = "category";
	public static final String PROD_SERV_NAME = "prodServName";

}

