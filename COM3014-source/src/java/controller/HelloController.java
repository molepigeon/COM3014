/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import beans.Name;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import service.HelloService;

/**
 *
 * @author Michael
 */
@Controller
@RequestMapping("hello")
public class HelloController{

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
}
