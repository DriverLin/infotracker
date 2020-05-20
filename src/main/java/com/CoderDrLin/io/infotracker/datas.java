package com.CoderDrLin.io.infotracker;

import com.CoderDrLin.io.parser.saveData;

import java.util.ArrayList;
import java.util.List;

public class datas {
    static List<saveData> data = new ArrayList();
    static void add(saveData saveData){
        data.add(saveData);
    }

    public static List<saveData> getData() {
        return data;
    }

    public static  String getString() {
        StringBuffer sb = new StringBuffer();
        for (saveData datum : data) {
            sb.append(datum.toString()+"\n");
        }
        return sb.toString();
    }
}
