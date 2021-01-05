package com.redhat.appdev.courie.driver.location;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import org.jboss.logging.Logger;

@ServerEndpoint(value = "/events/location-updates/{driverId}", encoders= { DriverLocationEncoder.class })         
@ApplicationScoped
public class DriverLocationEventSocket {
	
	private static final Logger LOG = Logger.getLogger(DriverLocationEventSocket.class);
	
	private Map<String, Session> sessions = new ConcurrentHashMap<>();
	
	@OnOpen
    public void onOpen(@PathParam("driverId") String driverId, Session session) {
		sessions.put(generateKey(driverId, session.getId()), session);
    }

    @OnClose
    public void onClose(@PathParam("driverId") String driverId, Session session) {
    	sessions.remove(generateKey(driverId, session.getId()));
    }

    @OnError
    public void onError(@PathParam("driverId") String driverId, Session session, Throwable throwable) {
    	sessions.remove(generateKey(driverId, session.getId()));
    }

    public void broadcast(DriverLocation location) {
    	
    	LOG.info("Broadcasting DriverLocation:" + location);
    	
    	sessions.forEach((key, session) -> {
    		
    		if (key.equals(generateKey(location.getDriver().getId(), session.getId()))) {
    			
				session.getAsyncRemote().sendObject(location, result ->  {
	                if (result.getException() != null) {
	                    LOG.error("Error sending location update to WS. Event: " + location, result.getException());
	                }
	            });
    		}
    	});
    }
    
    private String generateKey(String driverId, String sessionId) {
    	return driverId + "||" + sessionId;
    }
}
