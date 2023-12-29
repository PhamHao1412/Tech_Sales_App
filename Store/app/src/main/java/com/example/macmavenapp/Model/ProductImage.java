package com.example.macmavenapp.Model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductImage implements Serializable {
	@SerializedName("id")
	@Expose
	private int id;

	@SerializedName("url_Image")
	@Expose
	private String url_Image;

//	@SerializedName("product")
//	@Expose
	private Product product;

	public ProductImage(int id, String url_Image, Product product) {
		this.id = id;
		this.url_Image = url_Image;
		this.product = product;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUrl_Image() {
		return url_Image;
	}

	public void setUrl_Image(String url_Image) {
		this.url_Image = url_Image;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public String toString() {
		return "ProductImage{" +
				"id=" + id +
				", url_Image='" + url_Image + '\'' +
				", product=" + product +
				'}';
	}
}
