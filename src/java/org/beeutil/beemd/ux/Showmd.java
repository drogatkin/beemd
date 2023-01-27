package org.beeutil.beemd.ux;

import org.aldan3.model.ProcessException;
import org.aldan3.data.DODelegator;
import com.beegman.webbee.block.Form;
import org.beeutil.beemd.model.BeemdModel;
 import org.aldan3.model.DataObject;
 
import org.beeutil.beemd.model.MdData;
import java.io.File;

public class Showmd extends Form<MdData, BeemdModel> {

	@Override
	protected MdData getFormModel() {
		return new MdData(getAppModel());
	}

	@Override
	protected MdData loadModel(MdData jdo) {
	    String path = getParameterValue("location", "", 0);
	    File doc = new File(path);
	    if (doc.exists() && doc.getName().endsWith(MdData.MD)) {
	        jdo.path = path;
	        jdo.name = doc.getName();
	        jdo.document = jdo.getDocumentData(jdo.path);
	    }
                return jdo;
	}

	@Override
	protected Object storeModel(MdData jdo) {
	
		return null;
	}
	
	

}