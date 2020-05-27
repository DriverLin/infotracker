package com.CoderDrLin.io.SQliteCodes;

import com.alibaba.fastjson.JSONObject;
import org.springframework.jdbc.core.RowMapper;

import java.io.File;
import java.math.BigInteger;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

public class MySQLiteDataManager {
    private SqliteHelper sh = null;
    public MySQLiteDataManager() throws SQLException, ClassNotFoundException {
        File dbFile = new File("src/main/DB/data.db");
        if(dbFile.exists()){
             sh = new SqliteHelper("src/main/DB/data.db");
        }
    }
    public SqliteHelper getSh(){
        return sh;
    }

    /********添加网页配置********
     * @param url 链接
     * @param configJSON 对应的JSON
     * @return
     */
    public int insertWebsiteConfiguration(String url,String configJSON){
        if(sh == null){
            return -2;//空
        }
        try{
            String max = "SELECT MAX(ID) FROM websiteConfiguration ";
            List<Integer> result =  sh.executeQuery(max, (resultSet, i) -> resultSet.getInt("MAX(ID)"));
            int maxID = result.get(0);
            int id = maxID + 1;
            Date time = new Date(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String current = sdf.format(time);
            Map<String,Object> insertData = new HashMap<>();
            insertData.put("id",id);
            insertData.put("url",url);
            insertData.put("config",configJSON);
            insertData.put("addDate",current);
            insertData.put("disabled","false");
            sh.executeInsert("websiteConfiguration",insertData);
            return id;
        }catch (Exception e){
            e.printStackTrace();
            return -1;//出现错误
        }
    }

    /********获取网站配置********
     * id获取 -1获取全部 记得检查disabled
     * @param id
     * @return
     */
    public List<websiteConfiguration> getWebsiteConfiguration(int id){
        if(sh == null){
            return new ArrayList<>();
        }
        String exec;
        if(id == -1){
            exec = "SELECT * FROM websiteConfiguration ";
        }else{
            exec = String.format("SELECT * FROM websiteConfiguration WHERE ID = %d", id);
        }
        try {
            List<websiteConfiguration> result =  sh.executeQueryList(exec,websiteConfiguration.class);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
    /*禁用某项，任何地方在使用时应当先检查disabled属性*/
    public boolean disableWebsiteConfiguration(int id){
        if(sh == null){
            return false;
        }
        String exec = String.format("UPDATE websiteConfiguration SET disabled = 'true' WHERE ID = %d;", id);
        try {
            sh.executeUpdate(exec);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /********插入关键词*******
     *关键词应当与网页配置绑定
     * 单个关键词绑定多个网页则设置多个关键词
     * 绑定全部则只需设置-1
     * @param keyword 关键词
     * @param webSiteIndex 绑定网页ID
     * @return
     */
    public int insertKeyWords(String keyword,int webSiteIndex){
        if(sh == null){
            return -2;//空
        }
        try{
            String max = "SELECT MAX(ID) FROM keyWords ";
            List<Integer> result =  sh.executeQuery(max, (resultSet, i) -> resultSet.getInt("MAX(ID)"));
            int maxID = result.size() == 0 ? -1:result.get(0);
            int id = maxID + 1;
            Date time = new Date(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String current = sdf.format(time);
            Map<String,Object> insertData = new HashMap<>();
            insertData.put("id",id);
            insertData.put("keyword",keyword);
            insertData.put("websiteIndex",webSiteIndex);
            insertData.put("addDate",current);
            insertData.put("disabled","false");
            sh.executeInsert("keyWords",insertData);
            return id;
        }catch (Exception e){
            e.printStackTrace();
            return -1;//出现错误
        }
    }
    /********通过网站id获取关键词********
     * 遍历查询是按照网站去更新的
     * 所以查询关键词应是websiteIndex
     * 同时还会返回所有-1的
     * @param webSiteID 所绑定的网页链接
     * @return
     */
    public List<keyWords> getKeyWordsByWebsiteIndex(int webSiteID){
        if(sh == null){
            return new ArrayList<>();
        }
        List<keyWords> result = new ArrayList<>();
        String exec;

        try {
            if(webSiteID == -1){
                result.addAll(sh.executeQueryList("SELECT * FROM KeyWords WHERE websiteIndex = -1",keyWords.class));
            }
            result.addAll(sh.executeQueryList (String.format("SELECT * FROM KeyWords WHERE websiteIndex = %d", webSiteID),keyWords.class));
            return result;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }

    }

    /********通过ID获取关键词 用户展示会用********
     * @param id 关键词ID
     * @return
     */
    public List<keyWords> getKeyWordsById(int id){
        if(sh == null){
            return new ArrayList<>();
        }
        try {
            return sh.executeQueryList (String.format("SELECT * FROM KeyWords WHERE ID = %d", id),keyWords.class);
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public boolean disableKeyWords(int id){
        if(sh == null){
            return false;
        }
        String exec = String.format("UPDATE keyWords SET disabled = 'true' WHERE ID = %d;", id);
        try {
            sh.executeUpdate(exec);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    /********添加爬取记录********
     * 记录不应当脱离关键词和网站而存在
     * @param dataJSON 记录数据
     * @param webSiteIndex 网站index
     * @param keyWordIndex 关键词index
     * @return
     */
    public int insertDataRecords(String dataJSON,int webSiteIndex,int keyWordIndex){
        if(sh == null){
            return -2;//空
        }
        try{
            String max = "SELECT MAX(ID) FROM dataRecords ";
            List<Integer> result =  sh.executeQuery(max, (resultSet, i) -> resultSet.getInt("MAX(ID)"));
            int maxID = result.size() == 0 ? -1:result.get(0);
            int id = maxID + 1;
            Date time = new Date(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String current = sdf.format(time);
            Map<String,Object> insertData = new HashMap<>();
            insertData.put("id",id);
            insertData.put("dataJSON",dataJSON);
            insertData.put("websiteIndex",webSiteIndex);
            insertData.put("keyWordIndex",keyWordIndex);
            insertData.put("addDate",current);
            insertData.put("disabled","false");
            sh.executeInsert("dataRecords",insertData);
            return id;
        }catch (Exception e){
            e.printStackTrace();
            return -1;//出现错误
        }
    }

    /********通过ID获取记录********
     * 通过ID 可以获取记录
     * @param id
     * @return
     */
    public List<dataRecords> getDataRecordsById(int id){
        if(sh == null){
            return new ArrayList<>();
        }
        String exec = String.format("SELECT * FROM dataRecords WHERE ID = %d", id);
        try {
            return sh.executeQueryList(exec,dataRecords.class);
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }

    }

    /********通过日期获取记录********
     * 筛选出   添加日>date的记录
     * @param date
     * @return
     */
    public List<dataRecords> getDataRecordsByDate(String date){
        if(sh == null){
            return new ArrayList<>();
        }
        String exec = String.format("SELECT * FROM dataRecords WHERE addDate > %s", date);
        try {
            return sh.executeQueryList(exec,dataRecords.class);
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }

    }


    public boolean disableDataRecords(int id){
        if(sh == null){
            return false;
        }
        String exec = String.format("UPDATE dataRecords SET disabled = 'true' WHERE ID = %d;", id);
        try {
            sh.executeUpdate(exec);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public int insertSubscribe(String keyWordListJSON){//订阅 [indexOfKeywords] 订阅 = [关键词]
        if(sh == null){
            return -2;//空
        }
        try{
            String max = "SELECT MAX(ID) FROM subscribe ";
            List<Integer> result =  sh.executeQuery(max, (resultSet, i) -> resultSet.getInt("MAX(ID)"));
            int maxID = result.size() == 0 ? -1:result.get(0);
            int id = maxID + 1;
            Date time = new Date(System.currentTimeMillis());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String current = sdf.format(time);
            Map<String,Object> insertData = new HashMap<>();
            insertData.put("id",id);
            insertData.put("keyWordList",keyWordListJSON);
            insertData.put("addDate",current);
            insertData.put("disabled","false");
            sh.executeInsert("subscribe",insertData);
            return id;
        }catch (Exception e){
            e.printStackTrace();
            return -1;//出现错误
        }
    }
    public List<subscribe> getSubscribe(int id){
        if(sh == null){
            return new ArrayList<>();
        }
        String exec = String.format("SELECT * FROM subscribe WHERE ID = %d", id);
        try {
            return sh.executeQueryList(exec,subscribe.class);
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }

    }
    public boolean disableSubscribe(int id){
        if(sh == null){
            return false;
        }
        String exec = String.format("UPDATE subscribe SET disabled = 'true' WHERE ID = %d;", id);
        try {
            sh.executeUpdate(exec);
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}


class websiteConfiguration{
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

class subscribe{
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

class keyWords{
    private int id;
    private String keyword;
    private String addDate;
    private String disabled;

    public void setKeyWordList(String keyWordList) {
        this.keyword = keyWordList;
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

    public String getKeyWordList() {
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

class dataRecords{
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