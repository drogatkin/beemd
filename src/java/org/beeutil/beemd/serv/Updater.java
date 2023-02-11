///// Webbee (C) 2023 D Rogatkin   ////////////
//// TODO modify the file for required functionality
/*  Do not forget to activate the service in model class as below
     void initServices() {
         ...
         register(new Updater(this));
         ...
     }    
*/
        
package org.beeutil.beemd.serv;
import java.util.Collection;
import java.util.HashMap;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchKey;
import java.nio.file.WatchEvent;
import org.aldan3.model.ProcessException;
import org.beeutil.beemd.model.*;
import org.beeutil.beemd.model.BeemdModel; 
import org.aldan3.model.ServiceProvider;
import java.nio.file.WatchService;
import java.nio.file.FileSystems;
import java.nio.file.StandardWatchEventKinds;
import java.io.IOException;
import com.beegman.buzzbee.NotificationService;
import com.beegman.buzzbee.WebEvent;
import com.beegman.buzzbee.NotificationService.NotifException;
import org.aldan3.annot.Inject;

public class Updater implements ServiceProvider, Runnable {
    BeemdModel appModel;
    WatchService watcher;
    @Inject
    NotificationService ns;
    private boolean run;
    public static final String MD = ".md";

    public Updater(BeemdModel m) {
		appModel = m;
		initServ();
	}
	
	protected void initServ() {
	   // init code for your service
	   try {
         watcher = FileSystems.getDefault().newWatchService();
         run = true;
         new Thread(this, "watch service").start();
	   } catch(Exception e) {
	       throw new RuntimeException("The service can't start, because "+e);
	   }
	}
	
	@Override
    public void run() {
	    do {
	        try {
    	       WatchKey wkey =  watcher.take();
        	   for (WatchEvent<?> event: wkey.pollEvents()) {
                     // ENTRY_MODIFY
                     final Path changed = (Path) event.context();
                
                    if (changed.toString().endsWith(MD)) {
                        System.err.printf("changed path %s%n", changed);
                        ns.publish(new WebEvent().setAction("updateMD").setId(changed.toString()));
                    }
               }
        
                 // reset the key
                 boolean valid = wkey.reset();
                 if (!valid) {
                     // object no longer registered
                 }
	        } catch(InterruptedException ie) {
	            run =false;
	        } catch (NotifException ne) {
	            
	        }
	    } while(run);
	    
	}
	
	public void register(String path) {
	    // check if the path is valid and md file
	    if (path.endsWith(MD)) {
	      Path filePath = Paths.get(path);
	      Path dirPath = filePath.getParent();
	      System.err.printf("register path %s%n", dirPath);
	      try {
	        dirPath.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
	      } catch(IOException ioe) {
	          ioe.printStackTrace();
	      }
	    }
	}
	
	@Override
	public String getPreferredServiceName() {
		return getClass().getName();
	}

	@Override
	public Object getServiceProvider() {
		return this;
	}
	
	public void destroy() {
	    run = false;
	    if (watcher != null)
	        try {
	            watcher.close();
	        } catch(IOException ioe) {
	            
	        }
	}
}
                 