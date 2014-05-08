/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

import beans.Name;
import beans.State;
import beans.User;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.auth.oauth2.GoogleTokenResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Tokeninfo;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
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
        /*
     * Default HTTP transport to use to make HTTP requests.
     */
    private static final HttpTransport TRANSPORT = new NetHttpTransport();

    /*
     * Default JSON factory to use to deserialize JSON.
     */
    private static final JacksonFactory JSON_FACTORY = new JacksonFactory();

    /*
     * This is the Client ID that you generated in the API Console.
     */
    private static final String CLIENT_ID = "224915916800-pnk6oijt8djtvfekugd1bbjsivhlulip.apps.googleusercontent.com";

    /*
     * This is the Client Secret that you generated in the API Console.
     */
    private static final String CLIENT_SECRET = "nX6qfod5pnaao8G78aix4JbA";

    /*
     * Optionally replace this with your application's name.
     */
    private static final String APPLICATION_NAME = "Google+ Java Quickstart";
    
    State state;
    
    String token;
    
    @RequestMapping(value="index", method=RequestMethod.GET)
    public String onIndex(ModelMap model){
        state = new State();
        state.setState(new BigInteger(130, new SecureRandom()).toString(32));
        model.addAttribute("gState", state.getState());
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
    public String handleAjaxUserId(ModelMap model,  
            @RequestParam("code") String code,
            @RequestParam("id") String id, 
            @RequestParam("access") String accessID, 
            @RequestParam("state") String state){
        
        User user = new User();
        user.setID(id);
        user.setName("Joe Bloggs");
        
        //Check that the state token matches the one we gave the client
        if (!state.equals(this.state.getState())){
            System.out.println("N");
            return "helloView";
        } else {
            System.out.println("Y");
        }
        
        try {
            GoogleTokenResponse tokenResponse = new GoogleAuthorizationCodeTokenRequest(TRANSPORT, JSON_FACTORY,
            CLIENT_ID, CLIENT_SECRET, code, "postmessage").execute();
            GoogleCredential credential = new GoogleCredential.Builder()
                .setJsonFactory(JSON_FACTORY)
                .setTransport(TRANSPORT)
                .setClientSecrets(CLIENT_ID, CLIENT_SECRET).build()
                .setFromTokenResponse(tokenResponse);
            
            Oauth2 oauth2 = new Oauth2.Builder(TRANSPORT, JSON_FACTORY, credential).build();
            Tokeninfo tokenInfo = oauth2.tokeninfo().setAccessToken(credential.getAccessToken()).execute();
            
            if (tokenInfo.containsKey("error")){
                System.out.println("Error in token info");
                return "helloView";
            }
            
            if (!tokenInfo.getUserId().equals(id)) {
                System.out.println("Token doesn't match user's ID");
                return "helloView";
            }
            
            if (!tokenInfo.getIssuedTo().equals(CLIENT_ID)) {
                System.out.println("Token wasn't meant for us");
                return "helloView";
            }
            
            token = tokenResponse.toString();
            
            model.addAttribute("helloMessage", user.getName());
        } catch (IOException e){
        }
        System.out.println("ID Ajax Request Made: "+id);
        return "helloView";
    }
}
