package com.ascending.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "model")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String modelName;

    @Column(name = "type")
    private String vehicleType;

    @Column(name = "description")
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToMany(mappedBy = "model",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    //@OneToMany(mappedBy = "model", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Config> configs;

    //@JsonIgnore
    @Transient
    @Column(name="brand_id")
    private long brandId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Config> getConfigs() {
        if (configs == null) {
            configs = new HashSet<Config>();
        }
        return configs;
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public void addConfig(Config config) {
        this.getConfigs().add(config);
        config.setModel(this);
    }

    public void setConfigs(Set<Config> configs) {
        this.configs = configs;
    }

    public long getBrandId() {
        return brandId;
    }

    public void setBrandId(long brandId) {
        this.brandId = brandId;
    }


    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", modelName='" + modelName + '\'' +
                ", vehicleType='" + vehicleType + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
