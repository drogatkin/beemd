// Webbee (C) 2021 Dmitriy Rogatkin
///////   application model class   //////////
// TODO modify the file for the application purpose
package org.beeutil.beemd.model;

import javax.sql.DataSource;
import org.aldan3.data.DOService;
import org.aldan3.model.Log;

import com.beegman.webbee.model.AppModel;
import com.beegman.webbee.model.Auth;
import com.beegman.webbee.base.BaseBehavior;

import org.beeutil.beemd.model.util.BeemdBehavior;
public class BeemdModel extends AppModel {

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
	}

}