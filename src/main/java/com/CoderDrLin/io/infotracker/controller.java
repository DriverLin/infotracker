package com.CoderDrLin.io.infotracker;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Controller
@RequestMapping(value = "/image")
public class controller {
    @RequestMapping(value = "/data")
    @ResponseBody
    public String getdata() throws IOException {
        return datas.getString();
    }

}
