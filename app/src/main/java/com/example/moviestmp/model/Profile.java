package com.example.moviestmp.model;

public class Profile {
    private String id;
    private String name;
    private String avatarUrl;
    private int avatarResId;

    public Profile(String id, String name, String avatarUrl) {
        this.id = id;
        this.name = name;
        this.avatarUrl = avatarUrl;
        this.avatarResId = 0;
    }
    public Profile(String id, String name, int avatarResId) {
        this.id = id;
        this.name = name;
        this.avatarUrl = null;
        this.avatarResId = avatarResId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }
    public int getAvatarResId() {
        return avatarResId;
    }

    public void setAvatarResId(int avatarResId) {
        this.avatarResId = avatarResId;
    }
}
