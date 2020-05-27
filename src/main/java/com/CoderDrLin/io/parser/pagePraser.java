package com.CoderDrLin.io.parser;

import com.CoderDrLin.io.SQliteCodes.websiteConfiguration;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.seimicrawler.xpath.JXDocument;
import org.seimicrawler.xpath.JXNode;
import org.springframework.util.Assert;

import java.beans.JavaBean;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class pagePraser {
    public List<saveData> prase(config pageConfig) {
        List<saveData> result = new ArrayList<>();
        if (!pageConfig.isEffective()){
            return result;
        }
        try{
            String urlString = pageConfig.getUrl();
            URL targetUrl = new URL(urlString);
            Connection conn = Jsoup.connect(targetUrl.toString());
            Document  doc = conn.get();
            JXDocument jxd = new JXDocument(doc.getAllElements());
            String mainElement = pageConfig.getMainElement();//绝对路径找节点
            List<JXNode> list = jxd.selN(mainElement);

            for (JXNode jxNode : list) {
                String pagetTittle = jxNode.selOne(pageConfig.getTitle()).toString();
                String pageHref = jxNode.selOne(pageConfig.getHref()).toString();
                URL pageUrl = pageHref.contains("www.") ? new URL(pageHref): new URL(targetUrl.getProtocol(),targetUrl.getHost(),pageHref);
                result.add(new saveData(pagetTittle,pageUrl));
            }
            return result;
        }
        catch (Exception e ){
            e.printStackTrace();
            return result;
        }
    }
    public List<saveData> prase(websiteConfiguration wcfg) {
        JSONObject job = JSON.parseObject(wcfg.getConfig());
        List<saveData> result = new ArrayList<>();
        config pageConfig =  new config(
                wcfg.getUrl(),
                (String)job.get("mainElement"),
                (String)job.get("title"),
                (String)job.get("href")
        );
        if (!pageConfig.isEffective()){
            return result;
        }
        try{
            String urlString = pageConfig.getUrl();
            URL targetUrl = new URL(urlString);
            Connection conn = Jsoup.connect(targetUrl.toString());
            Document  doc = conn.get();
            JXDocument jxd = new JXDocument(doc.getAllElements());
            String mainElement = pageConfig.getMainElement();//绝对路径找节点
            List<JXNode> list = jxd.selN(mainElement);

            for (JXNode jxNode : list) {
                String pagetTittle = jxNode.selOne(pageConfig.getTitle()).toString();
                String pageHref = jxNode.selOne(pageConfig.getHref()).toString();
                URL pageUrl = pageHref.contains("www.") ? new URL(pageHref): new URL(targetUrl.getProtocol(),targetUrl.getHost(),pageHref);
                result.add(new saveData(pagetTittle,pageUrl));
            }
            return result;
        }
        catch (Exception e ){
            e.printStackTrace();
            return result;
        }
    }
}
