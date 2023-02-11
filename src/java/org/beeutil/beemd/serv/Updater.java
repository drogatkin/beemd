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
    private Thread processor;
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
         processor = new Thread(this, "watch service");
         processor.start();
	   } catch(Exception e) {
	       throw new RuntimeException("The service can't start, because "+e);
	   }
	}
	
	@Override
    public void run() {
	    do {
	        try {
    	       WatchKey wkey =  watcher.take();
    	       Path dir = (Path)wkey.watchable();
        	   for (WatchEvent<?> event: wkey.pollEvents()) {
                     // ENTRY_MODIFY
                     final Path changed = (Path) event.context();
                
                    if (changed.toString().endsWith(MD)) {
                        Path fullPath = dir.resolve(changed);
                        //System.err.printf("changed path %s%n", fullPath);
                        ns.publish(new WebEvent().setAction("updateMD").setId(changed.toString()).setAttributes(fullPath));
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
	
	public Path register(String path) {
	    // check if the path is valid and md file
	    if (path.endsWith(MD)) {
    	      Path filePath = Paths.get(path);
    	      Path dirPath = filePath.getParent();
    	      try {
    	        dirPath.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
    	        //System.err.printf("register path %s%n", filePath.getFileName());
    	        return filePath.getFileName();
    	      } catch(IOException ioe) {
    	          ioe.printStackTrace();
    	      }
	    }
	    return null;
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
	   if (processor != null)
	     processor.interrupt();
	}
}
                 