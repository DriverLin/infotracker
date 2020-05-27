package com.CoderDrLin.io.SQliteCodes;

import java.sql.SQLException;

public class sqltester {
    public static void main(String[] args) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        MySQLiteDataManager msm = new MySQLiteDataManager();

        msm.getSh().executeUpdate("DELETE  FROM dataRecords;");

        msm.getSh().executeUpdate("DELETE  FROM keyWords;");

        msm.getSh().executeUpdate("DELETE  FROM subscribe;");

        msm.getSh().executeUpdate("DELETE  FROM websiteConfiguration;");



        msm.insertDataRecords("datajson",1,2);
        msm.insertKeyWords("华为",-1);
        msm.insertSubscribe("[0,1,2]");
        msm.insertWebsiteConfiguration("baidu.com","{ok:ok}");
    }
}
