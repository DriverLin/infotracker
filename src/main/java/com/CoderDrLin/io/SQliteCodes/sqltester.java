package com.CoderDrLin.io.SQliteCodes;

import java.sql.SQLException;

public class sqltester {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        MySQLiteDataManager msm = new MySQLiteDataManager();

        msm.getSh().executeUpdate("DELETE  FROM dataRecords;");
        msm.getSh().executeUpdate("DELETE  FROM keyWords;");
        msm.getSh().executeUpdate("DELETE  FROM subscribe;");
        msm.getSh().executeUpdate("DELETE  FROM websiteConfiguration;");


        int id = msm.insertWebsiteConfiguration("http://xc.hfut.edu.cn/120/list.htm","{\n" +
                "  \"mainElement\":\"//*[@id=\\\"container\\\"]/div/div[4]/div[3]/div/div/table[1]/tbody/tr/td[2]/a\",\n" +
                "  \"title\":\"./text()\",\n" +
                "  \"href\":\"./@href\"\n" +
                "}");
        msm.insertKeyWords("五四",id);

//
//
//        msm.insertDataRecords("datajson",1,2);
//        msm.insertKeyWords("华为",-1);
//        msm.insertSubscribe("[0,1,2]");
//        msm.insertWebsiteConfiguration("baidu.com","{ok:ok}");
    }
}
