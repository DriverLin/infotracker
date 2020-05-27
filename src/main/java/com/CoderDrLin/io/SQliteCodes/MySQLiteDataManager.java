package com.CoderDrLin.io.SQliteCodes;


import com.CoderDrLin.io.messagePrinter;

import java.io.File;
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
        messagePrinter.print("insertWebsiteConfiguration","insert",url,configJSON);
        if(sh == null){
            messagePrinter.print("insertWebsiteConfiguration","sh is null",url,configJSON);
            return -2;//空
        }
        try{
            String isExisted = String.format("SELECT * FROM websiteConfiguration WHERE url = '%s' AND config = '%s' ",url,configJSON);
            List<websiteConfiguration> result =  sh.executeQueryList(isExisted,websiteConfiguration.class);
            if(result.size() == 1){
                messagePrinter.print("insertWebsiteConfiguration","existed",url,configJSON);
                return result.get(0).getId();//存在 则直接返回
            }
            String max = "SELECT MAX(ID) FROM websiteConfiguration ";
            List<Integer> maxIDList =  sh.executeQuery(max, (resultSet, i) -> resultSet.getInt("MAX(ID)"));
            int maxID = maxIDList.size() == 1 ? maxIDList.get(0):-1 ;
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
            messagePrinter.print("insertWebsiteConfiguration","ok",url,configJSON);
            return id;
        }catch (Exception e){
            e.printStackTrace();
            messagePrinter.print("insertWebsiteConfiguration","error",url,configJSON,e.getMessage());
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
        messagePrinter.print("insertKeyWords","insert",keyword,webSiteIndex+"");
        if(sh == null){
            messagePrinter.print("insertKeyWords","sh is null",keyword,webSiteIndex+"");
            return -2;//空
        }
        try{
            String isExisted = String.format("SELECT * FROM keyWords WHERE keyword = '%s' AND websiteIndex = %d ",keyword,webSiteIndex);
            List<keyWords> result =  sh.executeQueryList(isExisted,keyWords.class);
            if(result.size() == 1){
                messagePrinter.print("insertKeyWords","existed",keyword,webSiteIndex+"");
                return result.get(0).getId();//存在 则直接返回
            }
            String max = "SELECT MAX(ID) FROM keyWords ";
            List<Integer> maxIDList =  sh.executeQuery(max, (resultSet, i) -> resultSet.getInt("MAX(ID)"));
            int maxID = maxIDList.size() == 1 ? maxIDList.get(0):-1 ;
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
            messagePrinter.print("insertKeyWords","ok",keyword,webSiteIndex+"");
            return id;
        }catch (Exception e){
            e.printStackTrace();
            messagePrinter.print("insertKeyWords","error",keyword,webSiteIndex+"",e.getMessage());
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
        messagePrinter.print("insertDataRecords","insert",dataJSON,webSiteIndex+"",keyWordIndex+"");
        if(sh == null){
            messagePrinter.print("insertDataRecords","sh is null",dataJSON,webSiteIndex+"",keyWordIndex+"");
            return -2;//空
        }
        try{
            String isExisted = String.format("SELECT * FROM dataRecords WHERE dataJSON = '%s' AND webSiteIndex = %d AND keyWordIndex = %d ",dataJSON,webSiteIndex,keyWordIndex);
            List<dataRecords> result =  sh.executeQueryList(isExisted,dataRecords.class);
            if(result.size() == 1){
                messagePrinter.print("insertDataRecords","existed",dataJSON,webSiteIndex+"",keyWordIndex+"");
                return result.get(0).getId();//存在 则直接返回
            }
            String max = "SELECT MAX(ID) FROM dataRecords ";
            List<Integer> maxIDList =  sh.executeQuery(max, (resultSet, i) -> resultSet.getInt("MAX(ID)"));
            int maxID = maxIDList.size() == 1 ? maxIDList.get(0):-1 ;
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
            messagePrinter.print("insertDataRecords","ok",dataJSON,webSiteIndex+"",keyWordIndex+"");
            return id;
        }catch (Exception e){
            e.printStackTrace();
            messagePrinter.print("insertDataRecords","error",dataJSON,webSiteIndex+"",keyWordIndex+"",e.getMessage());
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
        List<dataRecords> result = new ArrayList();

        try {
            if(id == -1){
                result.addAll(sh.executeQueryList("SELECT * FROM dataRecords",dataRecords.class))  ;
            }
            String exec = String.format("SELECT * FROM dataRecords WHERE ID = %d", id);
            result.addAll(sh.executeQueryList(exec,dataRecords.class))  ;
            return  result;
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
        messagePrinter.print("insertSubscribe","insert",keyWordListJSON);
        if(sh == null){
            messagePrinter.print("insertSubscribe","sh is null",keyWordListJSON);
            return -2;//空
        }
        try{
            String isExisted = String.format("SELECT * FROM keyWords WHERE keyWordList = '%s' ",keyWordListJSON);
            List<subscribe> result =  sh.executeQueryList(isExisted,subscribe.class);
            if(result.size() == 1){
                messagePrinter.print("insertSubscribe","existed",keyWordListJSON);
                return result.get(0).getId();//存在 则直接返回
            }
            String max = "SELECT MAX(ID) FROM subscribe ";
            List<Integer> maxIDList =  sh.executeQuery(max, (resultSet, i) -> resultSet.getInt("MAX(ID)"));
            int maxID = maxIDList.size() == 1 ? maxIDList.get(0):-1 ;
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
            messagePrinter.print("insertSubscribe","ok",keyWordListJSON);
            return id;
        }catch (Exception e){
            e.printStackTrace();
            messagePrinter.print("insertSubscribe","error",keyWordListJSON,e.getMessage());
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


