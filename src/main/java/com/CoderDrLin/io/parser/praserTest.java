package com.CoderDrLin.io.parser;

import java.io.IOException;

public class praserTest {
    public static void main(String[] args) throws IOException {

        config hfut = new config(
                "http://xc.hfut.edu.cn/120/list.htm",
                "//*[@id=\"container\"]/div/div[4]/div[3]/div/div/table[1]/tbody/tr/td[2]/a",
                "./text()",
                "./@href"
        );
        config ithome = new config(
                "https://www.ithome.com/",
                "//*[@id=\"idnlst\"]/div[2]/div[1]/div[1]/ul/li/span[@class='title']/a",
                "./text()",
                "./@href"
        );
        config dongmanhuayuan = new config(
                "https://www.dongmanhuayuan.com/",
                "//a[@class='uk-text-break']",
                "./text()",
                "./@href"
        );
        for (saveData saveData : new pagePraser().prase(dongmanhuayuan)) {
            if(true){//saveData.getTitle().contains("赛")
                System.out.println(saveData);
            }
        }

    }
}
