package org.beeutil.beemd.ux.ws;

import com.beegman.buzzbee.web.NotifEndPoint;
import com.beegman.buzzbee.LogImpl;
import org.beeutil.beemd.model.BeemdModel;
import com.beegman.buzzbee.WebEvent;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/notif/web/", encoders = NotifEndPoint.WebEventEncoder.class)
public class WatcherEP extends NotifEndPoint {
    @OnMessage
  	public void fromClient(String file, Session s) {
  	    ses = s;
  	    try {
              BeemdModel.notifService.subscribe(file, this);
  	    } catch (Exception e) {
  	        LogImpl.log.error(e, "error");
    	}
  	}
  	
  	@Override
    public void notify(WebEvent event) {
    	if (ses != null)
    		try {
    			ses.getBasicRemote().sendObject(event);
    		} catch (Exception e) {
    		    LogImpl.log.error(e, "error");
    		}
    }
}