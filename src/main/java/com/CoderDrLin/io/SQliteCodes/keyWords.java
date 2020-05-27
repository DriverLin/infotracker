package com.CoderDrLin.io.SQliteCodes;

import com.alibaba.fastjson.JSONObject;

public class keyWords{
    private int id;
    private String keyword;
    private String addDate;
    private String disabled;

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
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

    public String getKeyword() {
        return keyword;
    }

    public boolean getDisabled() {
        return disabled.equals("true");
    }
    public String getJSON(){
        JSONObject job = new JSONObject();
        job.put("id",id);
        job.put("keyword",keyword);
        job.put("add",addDate);
        job.put("disabled",disabled.equals("true"));
        return job.toJSONString();
    }
}
