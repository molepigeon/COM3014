/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import beans.Name;
import beans.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import service.HelloService;


/**
 *
 * @author Michael, Jade
 */
@Controller
public class HelloController{
       
    @RequestMapping(value="index", method=RequestMethod.GET)
    public String onIndex(ModelMap model){
        return "index";
    }

    //Use onSubmit instead of doSubmitAction 
    //when you need access to the Request, Response, or BindException objects
    //@RequestMapping(value="/submit", method=RequestMethod.POST)
    @RequestMapping(value="landing", method=RequestMethod.GET)
    public String onLanding(ModelMap model){
     return "nameView";
     }
    
    @RequestMapping(value="submit", method=RequestMethod.POST)
    public String onSubmit(ModelMap model, @RequestParam("name") String name){
        Name nameObject = new Name();
        nameObject.setName(name);
        model.addAttribute("helloMessage", HelloService.sayHello(nameObject.getName()));
        return "helloView";
    }
    
    @RequestMapping(value="postuid", method=RequestMethod.POST)
    public String handleAjaxUserId(ModelMap model, @RequestParam("id") String id){
        User user = new User();
        user.setID(id);
        user.setName("Joe Bloggs");
        model.addAttribute("helloMessage", user.getName());
        System.out.println("ID Ajax Request Made: "+id);
        return "helloView";
    }
}
