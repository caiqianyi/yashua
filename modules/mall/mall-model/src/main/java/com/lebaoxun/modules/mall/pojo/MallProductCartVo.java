package com.lebaoxun.modules.mall.pojo;

import java.io.Serializable;

import com.lebaoxun.modules.mall.entity.MallCartEntity;
import com.lebaoxun.modules.mall.entity.MallProductEntity;
import com.lebaoxun.modules.mall.entity.MallProductSpecificationEntity;

public class MallProductCartVo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1093110123201288371L;
	private MallCartEntity cart;
	private MallProductEntity product;
	private MallProductSpecificationEntity spec;
	private String sepcAttrName;
	private String specName;
	
	public MallCartEntity getCart() {
		return cart;
	}
	public void setCart(MallCartEntity cart) {
		this.cart = cart;
	}
	public MallProductEntity getProduct() {
		return product;
	}
	public void setProduct(MallProductEntity product) {
		this.product = product;
	}
	public MallProductSpecificationEntity getSpec() {
		return spec;
	}
	public void setSpec(MallProductSpecificationEntity spec) {
		this.spec = spec;
	}
	public String getSepcAttrName() {
		return sepcAttrName;
	}
	public void setSepcAttrName(String sepcAttrName) {
		this.sepcAttrName = sepcAttrName;
	}
	public String getSpecName() {
		return specName;
	}
	public void setSpecName(String specName) {
		this.specName = specName;
	}
}
