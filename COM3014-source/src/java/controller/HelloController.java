/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import java.net.BindException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import service.HelloService;

/**
 *
 * @author Michael
 */
public class HelloController extends SimpleFormController {
    private HelloService helloService;
    
    public HelloController() {
        //Initialize controller properties here or 
        //in the Web Application Context

        setCommandClass(Name.class);
        setCommandName("name");
        setSuccessView("helloView");
        setFormView("nameView");
    }
    
    public void setHelloService(HelloService helloService){
        this.helloService = helloService;
    }

    //Use onSubmit instead of doSubmitAction 
    //when you need access to the Request, Response, or BindException objects
    @Override
    protected ModelAndView onSubmit(
     Object command) throws Exception {
     ModelAndView mv = new ModelAndView(getSuccessView());
     Name name = (Name)command;
     mv.addObject("helloMessage", helloService.sayHello(name.getValue()));
     return mv;
     }
}
