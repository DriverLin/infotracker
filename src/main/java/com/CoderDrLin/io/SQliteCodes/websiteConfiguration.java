package com.CoderDrLin.io.SQliteCodes;

import com.alibaba.fastjson.JSONObject;

public class websiteConfiguration{
    private int id;
    private String url;
    private String config;
    private String addDate;
    private String disabled;
    public void setUrl(String url) {
        this.url = url;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public void setConfig(String config) {
        this.config = config;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddDate() {
        return addDate;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getConfig() {
        return config;
    }

    public boolean getDisabled() {
        return disabled.equals("true");
    }
    public String getJSON(){
        JSONObject job = new JSONObject();
        job.put("id",id);
        job.put("url",url);
        job.put("config",config);
        job.put("add",addDate);
        job.put("disabled",disabled.equals("true"));
        return job.toJSONString();
    }
}
