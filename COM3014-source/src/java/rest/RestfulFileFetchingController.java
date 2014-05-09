/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rest;

import java.io.File;
import javax.servlet.ServletContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ServletContextAware;

/**
 *
 * @author Michael
 */
@Controller
public class RestfulFileFetchingController implements ServletContextAware{
    private ServletContext sc;
    
    @RequestMapping(value="getMoreImages/{index}/{shunt}", method=RequestMethod.GET)
    public String getMoreImages(ModelMap model, @PathVariable("index") int index, @PathVariable("shunt") int shunt){
        //TODO implement this
        int firstFile = index * 6;
        //File directory BECAUSE JAVA
        File directory = new File(sc.getRealPath("")+File.separator+"uploads");
        
        File[] allFiles = directory.listFiles();
        if (allFiles!=null){
            int oldestFile = (allFiles.length)-1;
        
            System.out.println(allFiles.length);
            for (int i = 0; i<6; i++){
                try{
                    String filenameParameter = "filename"+(i+1);
                    String idParameter = "userID"+(i+1);

                    String filename = allFiles[oldestFile-(i+firstFile+shunt)].getName();
                    model.addAttribute(filenameParameter, filename);

                    System.out.println(filename);
                    String splitFilename = filename.split("-")[1].split("\\.")[0];
                    model.addAttribute(idParameter, splitFilename);
                }catch (ArrayIndexOutOfBoundsException e){
                    model.addAttribute("filename"+(i+1), "");
                    model.addAttribute("userID"+(i+1), "");
                }
            }
        }
        return "imageJSON";
    }

    @Override
    public void setServletContext(ServletContext sc) {
        this.sc = sc;
    }
}
