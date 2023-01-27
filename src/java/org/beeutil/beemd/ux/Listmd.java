package org.beeutil.beemd.ux;

import java.util.Collection;
import org.aldan3.model.ProcessException;
import org.aldan3.data.DOService;
import org.aldan3.data.DODelegator;
import org.aldan3.model.DataObject;
import org.aldan3.annot.DataRelation;
import com.beegman.webbee.block.Tabular;

import org.beeutil.beemd.model.BeemdModel;
import org.beeutil.beemd.model.MdData;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
public class Listmd extends Tabular<Collection<MdData>, BeemdModel> {
	protected Collection< MdData > getTabularData(long pos, int size) {
		String path = getParameterValue("location", System.getProperty("user.home"), 0);
		File cwd = new File(path);
		File [] content = cwd.listFiles();
		Arrays.sort(content);
		ArrayList<MdData> result = new ArrayList<>();
		File parent = cwd.getParentFile();
		if (parent != null) {
		     MdData md = new MdData(getAppModel()) ;
		       md . path = ""+parent;
		      md.name =  "..";
		      md.directory = true;
		      result.add(md);
		}
		for (File entry: content) {
		    if (entry.isDirectory()) {
		       MdData md = new MdData(getAppModel()) ;
		       md . path = ""+entry;
		      md.name =  entry.getName();
		      md.directory = true;
		       result.add(md);
		    } else if (entry.getName().endsWith(MdData.MD)) {
		         MdData md = new MdData(getAppModel() );
		       md . path = ""+entry;
		       md.name =  entry.getName();
		       result.add(md);
		    }
		}
		
		return result;
	}
}
