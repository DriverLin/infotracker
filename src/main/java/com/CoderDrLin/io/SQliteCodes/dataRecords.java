package com.CoderDrLin.io.SQliteCodes;

import com.alibaba.fastjson.JSONObject;

public class dataRecords{
    private int id;
    private String dataJSON;
    private int webSiteIndex;
    private int keyWordIndex;
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
    public void setDataJSON(String dataJSON) {
        this.dataJSON = dataJSON;
    }
    public void setKeyWordIndex(int keyWordIndex) {
        this.keyWordIndex = keyWordIndex;
    }
    public void setWebSiteIndex(int webSiteIndex) {
        this.webSiteIndex = webSiteIndex;
    }

    public int getId() {
        return id;
    }

    public int getKeyWordIndex() {
        return keyWordIndex;
    }

    public int getWebSiteIndex() {
        return webSiteIndex;
    }

    public String getAddDate() {
        return addDate;
    }

    public String getDataJSON() {
        return dataJSON;
    }

    public boolean getDisabled() {
        return disabled.equals("true");
    }
    public String getJSON(){
        JSONObject job = new JSONObject();
        job.put("id",id);
        job.put("dataJSON",dataJSON);
        job.put("webSiteIndex",webSiteIndex);
        job.put("keyWordIndex",keyWordIndex);
        job.put("add",addDate);
        job.put("disabled",disabled.equals("true"));
        return job.toJSONString();
    }
}
