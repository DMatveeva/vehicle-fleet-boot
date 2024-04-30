package ru.dmatveeva.vehiclefleetboot.to;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class VehicleTo {

    @JsonProperty
    private Integer id;

    @JsonProperty
    private Integer modelId;

    @JsonProperty
    private String vin;

    @JsonProperty
    private BigDecimal costUsd;

    @JsonProperty
    private String color;

    @JsonProperty
    private int mileage;

    @JsonProperty
    private int productionYear;

    @JsonProperty
    private Integer enterpriseId;

    @JsonProperty
    @JsonFormat(pattern="dd-MM-yyyy HH:mm")
    private LocalDateTime purchaseDate;

    public VehicleTo() {
    }

    public VehicleTo(Integer id, Integer modelId, String vin, BigDecimal costUsd, String color, int mileage, int productionYear, Integer enterpriseId, LocalDateTime purchaseDate) {
        this.id = id;
        this.modelId = modelId;
        this.vin = vin;
        this.costUsd = costUsd;
        this.color = color;
        this.mileage = mileage;
        this.productionYear = productionYear;
        this.enterpriseId = enterpriseId;
        this.purchaseDate = purchaseDate;
    }

    @Override
    public String toString() {
        return "VehicleTo{" +
                "id=" + id +
                ", modelId=" + modelId +
                ", vin='" + vin + '\'' +
                ", costUsd=" + costUsd +
                ", color='" + color + '\'' +
                ", mileage=" + mileage +
                ", productionYear=" + productionYear +
                ", enterpriseId=" + enterpriseId +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public Integer getModelId() {
        return modelId;
    }

    public String getVin() {
        return vin;
    }

    public BigDecimal getCostUsd() {
        return costUsd;
    }

    public String getColor() {
        return color;
    }

    public int getMileage() {
        return mileage;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public Integer getEnterpriseId() {
        return enterpriseId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setModelId(Integer modelId) {
        this.modelId = modelId;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public void setCostUsd(BigDecimal costUsd) {
        this.costUsd = costUsd;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public void setProductionYear(int productionYear) {
        this.productionYear = productionYear;
    }

    public void setEnterpriseId(Integer enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public LocalDateTime getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateTime purchaseDate) {
        this.purchaseDate = purchaseDate;
    }
}
