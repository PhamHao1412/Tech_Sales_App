package com.example.macmavenapp.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Order_Item implements Serializable {

	@SerializedName("id")
	@Expose
	private int id;

	@SerializedName("count")
	@Expose
	private int count;

	@SerializedName("product")
	@Expose
	private Product product;

	@SerializedName("order")
	@Expose
	private Order order;

	public Order_Item(int id, int count, Product product, Order order) {
		this.id = id;
		this.count = count;
		this.product = product;
		this.order = order;
	}

	@Override
	public String toString() {
		return "Order_Item{" +
				"id=" + id +
				", count=" + count +
				", product=" + product +
				", order=" + order +
				'}';
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}
}
