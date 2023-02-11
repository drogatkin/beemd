// Webbee (C) 2021 Dmitriy Rogatkin
///////   application model class   //////////
// TODO modify the file for the application purpose
package org.beeutil.beemd.model;

import javax.sql.DataSource;
import java.util.Properties;
import org.aldan3.data.DOService;
import org.aldan3.model.Log;

import com.beegman.buzzbee.NotificationServiceImpl;

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
		register(notifService = new NotificationServiceImpl().init(new Properties(), this).start());
		register(new Updater(this));
	}
	
	@Override
	protected void deactivateServices() {
		super.deactivateServices();
		((Updater)unregister(getService(Updater.class.getName()))).destroy();
		((NotificationServiceImpl) unregister(getService(notifService.getPreferredServiceName()))).destroy();
		notifService =  null;
	}

}