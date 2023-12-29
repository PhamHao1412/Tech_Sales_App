package com.example.macmavenapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;



public class Order implements Serializable {

	@SerializedName("id")
	@Expose
	private int id;

	@SerializedName("total")
	@Expose
	private int total;

	@SerializedName("booking_Date")
	@Expose
	private Date booking_Date;

	@SerializedName("payment_Method")
	@Expose
	private String payment_Method;

	@SerializedName("status")
	@Expose
	private String status;

	@SerializedName("fullname")
	@Expose
	private String fullname;

	@SerializedName("country")
	@Expose
	private String country;

	@SerializedName("address")
	@Expose
	private String address;

	@SerializedName("phone")
	@Expose
	private String phone;

	@SerializedName("email")
	@Expose
	private String email;

	@SerializedName("note")
	@Expose
	private String note;

//	@SerializedName("order_Item")
//	@Expose
	private List<Order_Item> order_Item;

//	@SerializedName("user")
//	@Expose
	private User user;

	public Order(int id, int total, Date booking_Date, String payment_Method, String status, String fullname, String country, String address, String phone, String email, String note, List<Order_Item> order_Item, User user) {
		this.id = id;
		this.total = total;
		this.booking_Date = booking_Date;
		this.payment_Method = payment_Method;
		this.status = status;
		this.fullname = fullname;
		this.country = country;
		this.address = address;
		this.phone = phone;
		this.email = email;
		this.note = note;
		this.order_Item = order_Item;
		this.user = user;
	}

	@Override
	public String toString() {
		return "Order{" +
				"id=" + id +
				", total=" + total +
				", booking_Date=" + booking_Date +
				", payment_Method='" + payment_Method + '\'' +
				", status='" + status + '\'' +
				", fullname='" + fullname + '\'' +
				", country='" + country + '\'' +
				", address='" + address + '\'' +
				", phone='" + phone + '\'' +
				", email='" + email + '\'' +
				", note='" + note + '\'' +
				", order_Item=" + order_Item +
				", user=" + user +
				'}';
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public void setBooking_Date(Date booking_Date) {
		this.booking_Date = booking_Date;
	}

	public void setPayment_Method(String payment_Method) {
		this.payment_Method = payment_Method;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setNote(String note) {
		this.note = note;
	}

	public void setOrder_Item(List<Order_Item> order_Item) {
		this.order_Item = order_Item;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public int getId() {
		return id;
	}

	public int getTotal() {
		return total;
	}

	public Date getBooking_Date() {
		return booking_Date;
	}

	public String getPayment_Method() {
		return payment_Method;
	}

	public String getStatus() {
		return status;
	}

	public String getFullname() {
		return fullname;
	}

	public String getCountry() {
		return country;
	}

	public String getAddress() {
		return address;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}

	public String getNote() {
		return note;
	}

	public List<Order_Item> getOrder_Item() {
		return order_Item;
	}

	public User getUser() {
		return user;
	}
}
