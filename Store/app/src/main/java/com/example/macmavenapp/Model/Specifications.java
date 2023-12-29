package com.example.macmavenapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Specifications implements Serializable {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOp_cpu() {
        return op_cpu;
    }

    public void setOp_cpu(String op_cpu) {
        this.op_cpu = op_cpu;
    }

    public String getMemory() {
        return memory;
    }

    public void setMemory(String memory) {
        this.memory = memory;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public String getBattery() {
        return battery;
    }

    public void setBattery(String battery) {
        this.battery = battery;
    }

    public String getConnect() {
        return connect;
    }

    public void setConnect(String connect) {
        this.connect = connect;
    }

    public String getDesign() {
        return design;
    }

    public void setDesign(String design) {
        this.design = design;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("op_cpu")
    @Expose
    private String op_cpu;

    public Specifications(int id, String op_cpu, String memory, String display, String battery, String connect, String design, Product product) {
        this.id = id;
        this.op_cpu = op_cpu;
        this.memory = memory;
        this.display = display;
        this.battery = battery;
        this.connect = connect;
        this.design = design;
        this.product = product;
    }

    @SerializedName("memory")
    @Expose
    private String memory;
    @SerializedName("display")
    @Expose
    private String display;
    @SerializedName("battery")
    @Expose
    private String battery;
    @SerializedName("connect")
    @Expose
    private String connect;
    @SerializedName("design")
    @Expose
    private String design;

    private Product product;

    @Override
    public String toString() {
        return "Specifications{" +
                "id=" + id +
                ", op_cpu='" + op_cpu + '\'' +
                ", memory='" + memory + '\'' +
                ", display='" + display + '\'' +
                ", battery='" + battery + '\'' +
                ", connect='" + connect + '\'' +
                ", design='" + design + '\'' +
                ", product=" + product +
                '}';
    }
}
