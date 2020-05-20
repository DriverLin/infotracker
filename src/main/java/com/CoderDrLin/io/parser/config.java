package com.CoderDrLin.io.parser;

import java.beans.JavaBean;

@JavaBean
public class config{
    public String url = null;//网页链接
    public String mainElement = null ;//主元素
    public String title = null ;//标题
    public String href = null;//href

    public config(String url,String mainElement,String title,String href){
        setUrl(url);
        setMainElement(mainElement);
        setTitle(title);
        setHref(href);
    }
    public void setUrl(String url) {
        this.url = url;
    }
    public void setMainElement(String mainElement) {
        this.mainElement = mainElement;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setHref(String href) {
        this.href = href;
    }
    public String getUrl() {
        return url;
    }
    public String getMainElement() {
        return mainElement;
    }
    public String getTitle() {
        return title;
    }
    public String getHref() {
        return href;
    }
    public boolean isEffective(){
        return url!=null&& mainElement!=null && title!=null && href!=null;
    }
}
