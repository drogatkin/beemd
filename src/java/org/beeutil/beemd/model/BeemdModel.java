// Webbee (C) 2021 Dmitriy Rogatkin
///////   application model class   //////////
// TODO modify the file for the application purpose
package org.beeutil.beemd.model;

import javax.sql.DataSource;
import java.util.Properties;
import java.util.Iterator;
import java.lang.reflect.Field;
import org.aldan3.data.DOService;
import org.aldan3.model.Log;
import org.aldan3.model.ServiceProvider;
import org.aldan3.annot.Inject;

import com.beegman.buzzbee.NotificationServiceImpl;
import com.beegman.buzzbee.Subscriber;

import com.beegman.webbee.model.AppModel;
import com.beegman.webbee.model.Auth;
import com.beegman.webbee.base.BaseBehavior;
import org.beeutil.beemd.serv.Updater;

import org.beeutil.beemd.model.util.BeemdBehavior;
public class BeemdModel extends AppModel {
    
    public static NotificationServiceImpl notifService ;
    
	@Override
	public String getAppName() {
		return "Beemd";
	}
	
	@Override
	protected String getServletName() {
		return "beemd servlet";
	}
 
	@Override
	protected DOService createDataService(DataSource datasource) {
		return new DOService(datasource) {

                };
        }

	@Override
        public BaseBehavior getCommonBehavior() {
		return new BeemdBehavior ();
	}

	@Override
	protected void initServices() {
		super.initServices();
		register(notifService = new NotificationServiceImpl() {
		        @Override
	            public void subscribe(String resourceId, Subscriber subscriber) throws NotifException {
	                try {
	                    super.subscribe( ((Updater)getService(Updater.class.getName())).register(resourceId).toString(), subscriber);
	                } catch(NullPointerException npe) {
	                    throw new NotifException("Couldn't register file name");
	                }
	            }
	            
		    }.init(new Properties(), this).start());
		register(inject(new Updater(this)));
	}
	
	@Override
	protected void deactivateServices() {
		super.deactivateServices();
		((Updater)unregister(getService(Updater.class.getName()))).destroy();
		((NotificationServiceImpl) unregister(getService(notifService.getPreferredServiceName()))).destroy();
		notifService =  null;
	}

    @Override
	public <T> T inject(T obj) {
		if (obj == null) {
			return null;
		}
		for (Field fl : obj.getClass().getDeclaredFields()) { // use cl.getFields() for public with inheritance
			if (fl.getAnnotation(Inject.class) != null) {
				try {
					Class<?> type = fl.getType();
					Object serv = lookupService(type);
				//	System.err.printf("Injecting "+serv+" for "+fl+" of "+type+"\n");
					assureAccessible(fl).set(obj, serv);
				} catch (Exception e) {
					Log.l.error("Exception in injection for " + fl, e);
				}
			}
		}
		return obj;
	}

	public Object lookupService(Class<?> type) {
		Iterator<ServiceProvider> i = iterator();
		while (i.hasNext()) {
			ServiceProvider sp = i.next();
			// System.err.printf("Checking %s assign %b from %s%n", sp, type.isAssignableFrom(sp.getClass()), type );
			if (sp.getClass() == type || type.isAssignableFrom(sp.getClass()) ) 
				return sp;
		}
		return null;
	}
	
	static protected Field assureAccessible(Field fl) {
		if (fl.isAccessible())
			return fl;
		fl.setAccessible(true);
		return fl;
	}
}