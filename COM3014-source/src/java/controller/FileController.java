/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


/**
 *
 * @author Michael, Jade
 */
@Controller
public class FileController {
    
    //TODO Remove this method
    @RequestMapping("newjsp")
    public String testFileUpload(){
        return "newjsp";
    }
    
    @RequestMapping(value="fileupload", method=RequestMethod.POST)
    public String fileUploadHandler(ModelMap model, @RequestParam("imageFile") MultipartFile file){
        String output = "Nothing";
        if (!file.isEmpty()){
            //File has something in it
            try{
                byte[] bytes = file.getBytes();
                
                String projectRoot = "C:\\Users\\Michael\\Desktop";
                File directory = new File(projectRoot + File.separator + "uploads");
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                File storageFile = new File(directory.getAbsolutePath() + File.separator + "blah.jpg");
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(storageFile));
                bos.write(bytes);
                bos.close();
                output = "Uploaded to "+storageFile.getAbsolutePath();
            } catch (Exception e){
                System.out.println("Ass.");
                e.printStackTrace();
            }
        } else {
            System.out.println("File empty");
        }
        
        
        model.addAttribute("fileName", output);
        return "fileUploadConfirmation";
    }
    
    
}
