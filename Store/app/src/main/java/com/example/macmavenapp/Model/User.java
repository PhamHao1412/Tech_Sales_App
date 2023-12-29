package com.example.macmavenapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class User implements Serializable {
	@SerializedName("id")
	@Expose
	private String id;

	@SerializedName("login_Type")
	@Expose
	private String login_Type;

	@SerializedName("role")
	@Expose
	private String role;

	@SerializedName("password")
	@Expose
	private String password;

	@SerializedName("user_Name")
	@Expose
	private String user_Name;

	@SerializedName("avatar")
	@Expose
	private String avatar;

	@SerializedName("email")
	@Expose
	private String email;

	@SerializedName("phone_Number")
	@Expose
	private String phone_Number;

	@SerializedName("address")
	@Expose
	private String address;

//	@SerializedName("order")
//	@Expose
	private List<Order> order;

	//	@SerializedName("cart")
//	@Expose
	private List<Cart> cart;



	public User() {

	}

	public String getId() {
		return id;
	}

	public String getLogin_Type() {
		return login_Type;
	}

	public String getRole() {
		return role;
	}

	public String getPassword() {
		return password;
	}

	public String getUser_Name() {
		return user_Name;
	}

	public String getAvatar() {
		return avatar;
	}

	public String getEmail() {
		return email;
	}

	public String getPhone_Number() {
		return phone_Number;
	}

	public List<Order> getOrder() {
		return order;
	}

	public List<Cart> getCart() {
		return cart;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLogin_Type(String login_Type) {
		this.login_Type = login_Type;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUser_Name(String user_Name) {
		this.user_Name = user_Name;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPhone_Number(String phone_Number) {
		this.phone_Number = phone_Number;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
	}

	public void setCart(List<Cart> cart) {
		this.cart = cart;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public User(String id, String login_Type, String role, String password, String user_Name, String avatar, String email, String phone_Number, String address, List<Order> order, List<Cart> cart) {
		this.id = id;
		this.login_Type = login_Type;
		this.role = role;
		this.password = password;
		this.user_Name = user_Name;
		this.avatar = avatar;
		this.email = email;
		this.phone_Number = phone_Number;
		this.address = address;
		this.order = order;
		this.cart = cart;
	}

	@Override
	public String toString() {
		return "User{" +
				"id='" + id + '\'' +
				", login_Type='" + login_Type + '\'' +
				", role='" + role + '\'' +
				", password='" + password + '\'' +
				", user_Name='" + user_Name + '\'' +
				", avatar='" + avatar + '\'' +
				", email='" + email + '\'' +
				", phone_Number='" + phone_Number + '\'' +
				", address='" + address + '\'' +
				", order=" + order +
				", cart=" + cart +
				'}';
	}
}
