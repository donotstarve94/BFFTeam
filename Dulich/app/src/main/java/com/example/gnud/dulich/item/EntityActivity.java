package com.example.gnud.dulich.item;

/**
 * Created by GNUD on 09/08/2016.
 */
public class EntityActivity {
    private int imageId;
    private String title;
    private boolean check;

    public boolean isCheck() {
        return check;
    }

    public void setCheck(boolean check) {
        this.check = check;
    }

    public EntityActivity(int imageId, String title, boolean check) {
        this.imageId = imageId;
        this.title = title;
        this.check = check;

    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
