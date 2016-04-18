package pl.tomo.provider;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

import pl.tomo.entity.User;
import pl.tomo.service.PatientService;
import pl.tomo.service.UserService;


//http://stackoverflow.com/a/6162091/5753094

@Component
public class SessionEndedListener implements ApplicationListener<SessionDestroyedEvent>{
	
	private Logger logger = Logger.getLogger(SessionEndedListener.class);
	
	@Autowired
	UserService userService;
	
	@Autowired PatientService patientService;

	@Override
	public void onApplicationEvent(SessionDestroyedEvent event) {
		for (SecurityContext securityContext : event.getSecurityContexts())
        {
            Authentication authentication = securityContext.getAuthentication();

            if (authentication != null){
    	        String userName = authentication.getName();
    	        logger.info("User: " + userName + " has logout or session expired");
   	        
    	        if(userName.contains("demo")) {
    	        	List<User> users = userService.findAllByName(userName);
    	        	for (User user : users) {
    					if(user.getDemoNo()!=0) {
    						userService.delete(user);    						
    					}
    				}
    	        }
    	    }
        }
		
	}

}
