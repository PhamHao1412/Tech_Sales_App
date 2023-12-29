package com.example.macmavenapp.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Cart implements Serializable {
	@SerializedName("id")
	@Expose
	private int id;

	@SerializedName("count")
	@Expose
	private int count;

//	@SerializedName("user")
//	@Expose
	private User user;

//	@SerializedName("product")
//	@Expose
	private Product product;

	public void setId(int id) {
		this.id = id;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public int getId() {
		return id;
	}

	public int getCount() {
		return count;
	}

	public User getUser() {
		return user;
	}

	public Product getProduct() {
		return product;
	}

	public Cart(int id, int count, User user, Product product) {
		this.id = id;
		this.count = count;
		this.user = user;
		this.product = product;
	}

	@Override
	public String toString() {
		return "Cart{" +
				"id=" + id +
				", count=" + count +
				", user=" + user +
				", product=" + product +
				'}';
	}

}
