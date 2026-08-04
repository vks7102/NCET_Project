package com.capstone.backend.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "mobility_booths")
public class MobilityBooth {
    @Id
    private String id;
    private String boothId;
    private String boothName;
    private String areaName;
    private String address;
    private String contactPerson;
    private String contactPhone;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private Location location;

    private Integer totalCapacity;
    private Integer currentQueue = 0;
    private Boolean isActive = true;

    public static class Location {
        private String type = "Point";
        private double[] coordinates;

        public Location() {}

        public Location(double longitude, double latitude) {
            this.coordinates = new double[]{longitude, latitude};
        }

        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public double[] getCoordinates() { return coordinates; }
        public void setCoordinates(double[] coordinates) { this.coordinates = coordinates; }
    }

    public MobilityBooth() {}

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getBoothId() { return boothId; }
    public void setBoothId(String boothId) { this.boothId = boothId; }
    public String getBoothName() { return boothName; }
    public void setBoothName(String boothName) { this.boothName = boothName; }
    public String getAreaName() { return areaName; }
    public void setAreaName(String areaName) { this.areaName = areaName; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }
    public String getContactPhone() { return contactPhone; }
    public void setContactPhone(String contactPhone) { this.contactPhone = contactPhone; }
    public Location getLocation() { return location; }
    public void setLocation(Location location) { this.location = location; }
    public Integer getTotalCapacity() { return totalCapacity; }
    public void setTotalCapacity(Integer totalCapacity) { this.totalCapacity = totalCapacity; }
    public Integer getCurrentQueue() { return currentQueue; }
    public void setCurrentQueue(Integer currentQueue) { this.currentQueue = currentQueue; }
    public Boolean getIsActive() { return isActive; }
    public void setIsActive(Boolean isActive) { this.isActive = isActive; }
}
