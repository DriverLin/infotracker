package com.CoderDrLin.io.infotracker;

import com.CoderDrLin.io.SQliteCodes.MySQLiteDataManager;
import com.CoderDrLin.io.SQliteCodes.keyWords;
import com.CoderDrLin.io.messagePrinter;
import com.CoderDrLin.io.parser.pagePraser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;



@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class })
public class InfotrackerApplication {
	public static void main(String[] args) throws SQLException, ClassNotFoundException {
		SpringApplication.run(InfotrackerApplication.class, args);
        MySQLiteDataManager msm = new MySQLiteDataManager();
        messagePrinter.print("start");
        new Thread(() -> {
            pagePraser praser = new pagePraser();
            while(true){
                msm.getWebsiteConfiguration(-1).forEach(wcfg -> {
                    if( ! wcfg.getDisabled()){//跳过禁用的
                        int id = wcfg.getId();//获取这个网页配置的ID
                        List<keyWords> kw =  msm.getKeyWordsByWebsiteIndex(id);//通过ID获取所有绑定的关键词
                        praser.prase(wcfg).forEach( data ->{//对于每一条数据
                            for (keyWords keyWord : kw) {
                                if(keyWord.getDisabled()){//跳过禁用的
                                    continue;
                                }
                                boolean PatternResult = Pattern.matches(keyWord.getKeyword(),data.getTitle());
                                if( data.getTitle().contains(keyWord.getKeyword()) || PatternResult ){//正则匹配 和 直接查找
                                    msm.insertDataRecords( data.getJSON() ,wcfg.getId(),keyWord.getId());
                                }
                            }
                        });
                    }
                });
                try {
                    for (int i = 0; i < 5*60 ; i++) {
                        Thread.sleep(1000);
                        System.out.print(i+"\r");
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
	}

}
