package com.CoderDrLin.io.infotracker;

import com.CoderDrLin.io.SQliteCodes.MySQLiteDataManager;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping(value = "/getInfo")
public class controller {
    private MySQLiteDataManager msm;
    public controller() throws SQLException, ClassNotFoundException {
        msm = new MySQLiteDataManager();
    }
    @RequestMapping(value = "/listAll")
    @ResponseBody
    public String getdata() throws Exception {

        StringBuffer sb = new StringBuffer();
        msm.getDataRecordsById(-1).forEach( datas -> {
            sb.append(datas.getDataJSON());
        } );
        return sb.toString();
    }

}
