package com.example.macmavenapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Category implements Serializable {
	@SerializedName("id")
	@Expose
	private int id;

	@SerializedName("category_Name")
	@Expose
	private String category_Name;

	@SerializedName("category_Image")
	@Expose
	private String category_Image;
	private List<Product> product;

	public Category(int id, String category_Name, String category_Image, List<Product> product) {
		this.id = id;
		this.category_Name = category_Name;
		this.category_Image = category_Image;
		this.product = product;
	}

	public String getCategory_Image() {
		return category_Image;
	}

	public void setCategory_Image(String category_Image) {
		this.category_Image = category_Image;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setCategory_Name(String category_Name) {
		this.category_Name = category_Name;
	}

	public void setProduct(List<Product> product) {
		this.product = product;
	}

	public int getId() {
		return id;
	}

	public String getCategory_Name() {
		return category_Name;
	}

	public List<Product> getProduct() {
		return product;
	}

	@Override
	public String toString() {
		return "Category{" +
				"id=" + id +
				", category_Name='" + category_Name + '\'' +
				", category_Image='" + category_Image + '\'' +
				", product=" + product +
				'}';
	}
}