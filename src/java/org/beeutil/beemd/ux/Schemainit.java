package org.beeutil.beemd.ux;

import com.beegman.webbee.block.Systemsetup;
import com.beegman.webbee.model.Setup;
import org.beeutil.beemd.model.BeemdModel;

public class Schemainit extends Systemsetup<Setup, BeemdModel> {

	@Override
	protected String getDefaultModelPackage() {
             return getAppModel().getClass().getPackage().getName();
        }
}
