package com.example.kirill.testtask.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by kirill on 18.08.17.
 */

public class Shot extends RealmObject {
    @PrimaryKey
    private String id;

    private String title;
    private String description;
    private String hidpi;
    private String normal;
    private String teaser;
    private long dayLoad;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHidpi() {
        return hidpi;
    }

    public void setHidpi(String hidpi) {
        this.hidpi = hidpi;
    }

    public String getNormal() {
        return normal;
    }

    public void setNormal(String normal) {
        this.normal = normal;
    }

    public String getTeaser() {
        return teaser;
    }

    public void setTeaser(String teaser) {
        this.teaser = teaser;
    }

    public long getDayLoad() {
        return dayLoad;
    }

    public void setDayLoad(long dayLoad) {
        this.dayLoad = dayLoad;
    }

    @Override
    public String toString() {
        return "Shot{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", hidpi='" + hidpi + '\'' +
                ", normal='" + normal + '\'' +
                ", teaser='" + teaser + '\'' +
                ", dayLoad='" + dayLoad + '\'' +
                '}';
    }
}
