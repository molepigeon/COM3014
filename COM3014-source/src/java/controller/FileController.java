/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Jade
 */
@Controller
public class FileController {
    
    @RequestMapping(value="fileupload", method=RequestMethod.POST)
    public void handleAjaxUserId(ModelMap model){
        //Ha not my job.
    }
    
    
}
