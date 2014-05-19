/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import beans.User;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import javax.servlet.ServletContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.ServletContextAware;
import org.springframework.web.multipart.MultipartFile;
import service.UserService;


/**
 *
 * @author Michael, Jade
 */
@Controller
@Scope("request")
public class FileController implements ServletContextAware {
    
    ServletContext sc;
    
    //TODO Remove this method
    @RequestMapping("newjsp")
    public String testFileUpload(ModelMap model){
        model.addAttribute("test", UserService.getUser().getName());
        return "newjsp";
    }
    
    @RequestMapping(value="fileupload", method=RequestMethod.POST)
    public String fileUploadHandler(ModelMap model, 
            @RequestParam("imageFile") MultipartFile file){
        User user = UserService.getUser();
        String output;
        if (!file.isEmpty()){
            if (file.getContentType().contains("image")){
                //File has something in it
                try{
                    byte[] bytes = file.getBytes();

                    File directory = new File(sc.getRealPath("")+File.separator+
                            "uploads");
                    System.out.println(directory.getAbsoluteFile());
                    if (!directory.exists()) {
                        directory.mkdirs();
                    }
                    String userID = user.getID();
                    Date now = new Date();
                    File storageFile = new File(directory.getAbsolutePath() + 
                            File.separator + now.getTime() +"-"+userID+".jpg");
                    BufferedOutputStream bos = new BufferedOutputStream(
                            new FileOutputStream(storageFile));
                    bos.write(bytes);
                    bos.close();
                    
                    output = "Uploaded to "+storageFile.getAbsolutePath();
                    System.out.println(output);
                } catch (IOException e){
                    output = "Upload failed";
                }
            } else {
                output = "Not an image";
            }
        } else {
            output = "Not an image";
        }
        model.addAttribute("fileName", output);
        return "fileUploadConfirmation";
    }
    
    @Override
    public void setServletContext(ServletContext sc) {
        this.sc = sc;
    }
    
    
}
