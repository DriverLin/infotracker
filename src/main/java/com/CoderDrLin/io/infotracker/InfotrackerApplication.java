package com.CoderDrLin.io.infotracker;

import com.CoderDrLin.io.parser.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.CoderDrLin.io.parser.pagePraser;


@SpringBootApplication
public class InfotrackerApplication {
	public static void main(String[] args) {
		SpringApplication.run(InfotrackerApplication.class, args);
		System.out.println("started");

		new Thread(() -> {
            pagePraser praser = new pagePraser();
            while(true){
                praser.prase(new config(
                        "https://www.dongmanhuayuan.com/",
                        "//a[@class='uk-text-break']",
                        "./text()",
                        "./@href"
                )).forEach(saveData -> {
                    datas.add(saveData);
                } );
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
