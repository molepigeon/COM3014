/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controller;

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
import com.google.api.services.plus.Plus;
import com.google.api.services.plus.model.Person;
import java.io.IOException;
import java.math.BigInteger;
import java.security.SecureRandom;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import service.StateService;
import service.UserService;

/**
 *
 * @author Michael, Jade
 */
@Controller
@Scope("request")
public class LoginController{
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
    private static final String APPLICATION_NAME = "Surrey Share";
    
    
    String token;
    
    /**
     * Displays the landing page with a token generated for OAuth.
     * 
     * @param model
     * @return 
     */
    @RequestMapping(value="index", method=RequestMethod.GET)
    public String onIndex(ModelMap model){
        State state = new State();
        state.setState(new BigInteger(130, new SecureRandom()).toString(32));
        StateService.setState(state);
        model.addAttribute("gState", state.getState());
        return "index";
    }
    
    /**
     * Handles the server side of the OAuth login process.
     * 
     * Code modified from Google documentation.
     * 
     * @param model
     * @param code
     * @param id
     * @param accessID
     * @param state
     * @return 
     */
    @RequestMapping(value="postuid", method=RequestMethod.POST)
    public String handleAjaxUserId(ModelMap model,  
            @RequestParam("code") String code,
            @RequestParam("id") String id, 
            @RequestParam("access") String accessID, 
            @RequestParam("state") String state){
        
        State oldState = StateService.getState();
        //Check that the state token matches the one we gave the client
        if (!state.equals(oldState.getState())){
            System.out.println("N");
            return "profileJSON";
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
                return "profileJSON";
            }
            
            if (!tokenInfo.getUserId().equals(tokenResponse.parseIdToken().getPayload().getUserId())) {
                //Token recieved is user ID (+ profile number)
                System.out.println("Token doesn't match user's ID");
                return "profileJSON";
            }
            
            if (!tokenInfo.getIssuedTo().equals(CLIENT_ID)) {
                System.out.println("Token wasn't meant for us");
                return "profileJSON";
            }
            
            token = tokenResponse.toString();
            
            Plus service = new Plus.Builder(TRANSPORT, JSON_FACTORY, credential).setApplicationName(APPLICATION_NAME).build();
            Person thisUser = service.people().get("me").execute();
            
            model.addAttribute("userName", thisUser.getDisplayName());
            model.addAttribute("userAvatar", thisUser.getImage().getUrl());
            model.addAttribute("userURL", thisUser.getUrl());
            User user = new User();
            user.setID(tokenInfo.getUserId());
            user.setName(thisUser.getDisplayName());
            UserService.setUser(user);
        } catch (IOException e){
        }
        return "profileJSON";
    }
}
