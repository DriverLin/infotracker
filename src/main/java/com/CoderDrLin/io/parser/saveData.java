package com.CoderDrLin.io.parser;

import com.alibaba.fastjson.JSONObject;

import java.beans.JavaBean;
import java.net.URL;
import java.util.Objects;

@JavaBean
public class saveData{
    String title;
    URL url;
    public saveData(String title,URL url){
        setTitle(title);
        setUrl(url);
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setUrl(URL url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public URL getUrl() {
        return url;
    }

    @Override
    public String toString() {
        return "saveData{" +
                "title='" + title + '\'' +
                ", url=" + url +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        saveData saveData = (saveData) o;
        return Objects.equals(title, saveData.title) &&
                Objects.equals(url, saveData.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
    public String getJSON(){
        JSONObject job = new JSONObject();
        job.put("title",title);
        job.put("url",url);
        return job.toJSONString();
    }
}
