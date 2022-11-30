package com.example.parcial3.model;

public class Event {
    private int eventId;
    private byte[] img;
    private String date;
    private String description;
    private String latitude;
    private String longitude;
    private int userId;

    public Event() {}

    public Event(int eventId, byte[] img, String date, String description, String latitude, String longitude, int userId) {
        this.eventId = eventId;
        this.img = img;
        this.date = date;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.userId = userId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setImg(byte[] img) {
        this.img = img;
    }

    public byte[] getImg() {
        return img;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
}
