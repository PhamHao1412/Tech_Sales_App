package com.example.macmavenapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;

public class Product implements Serializable {
	@SerializedName("id")
	@Expose
	private int id;

	@SerializedName("product_Name")
	@Expose
	private String product_Name;

	@SerializedName("description")
	@Expose
	private String description;

	@SerializedName("sold")
	@Expose
	private int sold;

	@SerializedName("is_Active")
	@Expose
	private int is_Active;

	@SerializedName("is_Selling")
	@Expose
	private int is_Selling;

	@SerializedName("created_At")
	@Expose
	private Date created_At;

	@SerializedName("price")
	@Expose
	private int price;

	@SerializedName("quantity")
	@Expose
	private int quantity;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	@SerializedName("color")
	@Expose
	private String color;

	@SerializedName("capacity")
	@Expose
	private String capacity; // Provide a default value

	private Category category;

//	@SerializedName("productImage")
//	@Expose
	private List<ProductImage> productImage;

//	@SerializedName("order_Item")
//	@Expose
	private List<Order_Item> order_Item;

//	@SerializedName("order")
//	@Expose
	private List<Cart> cart;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getProduct_Name() {
		return product_Name;
	}

	public void setProduct_Name(String product_Name) {
		this.product_Name = product_Name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getSold() {
		return sold;
	}

	public void setSold(int sold) {
		this.sold = sold;
	}

	public int getIs_Active() {
		return is_Active;
	}

	public void setIs_Active(int is_Active) {
		this.is_Active = is_Active;
	}

	public int getIs_Selling() {
		return is_Selling;
	}

	public void setIs_Selling(int is_Selling) {
		this.is_Selling = is_Selling;
	}

	public Date getCreated_At() {
		return created_At;
	}

	public void setCreated_At(Date created_At) {
		this.created_At = created_At;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<ProductImage> getProductImage() {
		return productImage;
	}

	public void setProductImage(List<ProductImage> productImage) {
		this.productImage = productImage;
	}

	public List<Order_Item> getOrder_Item() {
		return order_Item;
	}

	public void setOrder_Item(List<Order_Item> order_Item) {
		this.order_Item = order_Item;
	}

	public List<Cart> getCart() {
		return cart;
	}

	public void setCart(List<Cart> cart) {
		this.cart = cart;
	}

	public Product(int id, String product_Name, String description, int sold, int is_Active, int is_Selling, Date created_At, int price, int quantity,String color,String capacity, Category category, List<ProductImage> productImage, List<Order_Item> order_Item, List<Cart> cart) {
		this.id = id;
		this.product_Name = product_Name;
		this.description = description;
		this.sold = sold;
		this.is_Active = is_Active;
		this.is_Selling = is_Selling;
		this.created_At = created_At;
		this.price = price;
		this.quantity = quantity;
		this.color = color;
		this.capacity = capacity;
		this.category = category;
		this.productImage = productImage;
		this.order_Item = order_Item;
		this.cart = cart;
	}

	@Override
	public String toString() {
		return "Product{" +
				"id=" + id +
				", product_Name='" + product_Name + '\'' +
				", description='" + description + '\'' +
				", sold=" + sold +
				", is_Active=" + is_Active +
				", is_Selling=" + is_Selling +
				", created_At=" + created_At +
				", price=" + price +
				", quantity=" + quantity +
				", quantity=" + color +
				", quantity=" + capacity +
				", category=" + category +
				", productImage=" + productImage +
				", order_Item=" + order_Item +
				", cart=" + cart +
				'}';
	}
}
