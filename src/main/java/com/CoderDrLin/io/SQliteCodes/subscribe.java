package com.CoderDrLin.io.SQliteCodes;

import com.alibaba.fastjson.JSONObject;

public class subscribe{
    private int id;
    private String  keyWordList;
    private String addDate;
    private String disabled;

    public void setId(int id) {
        this.id = id;
    }

    public void setDisabled(String disabled) {
        this.disabled = disabled;
    }

    public void setAddDate(String addDate) {
        this.addDate = addDate;
    }

    public void setKeyWordList(String keyWordList) {
        this.keyWordList = keyWordList;
    }

    public int getId() {
        return id;
    }

    public String getKeyWordList() {
        return keyWordList;
    }

    public String getAddDate() {
        return addDate;
    }
    public boolean getDisabled() {
        return disabled.equals("true");
    }
    public String getJSON(){
        JSONObject job = new JSONObject();
        job.put("id",id);
        job.put("keyWordList",keyWordList);
        job.put("add",addDate);
        job.put("disabled",disabled.equals("true"));
        return job.toJSONString();
    }
}
