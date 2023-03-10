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
import java.util.Date;
public class Listmd extends Tabular<Collection<MdData>, BeemdModel> {
	protected Collection< MdData > getTabularData(long pos, int size) {
		String path = getParameterValue("location", System.getProperty("user.home"), 0);
		File cwd = new File(path).getAbsoluteFile() ;
		if (".".equals(cwd.getName()))
		    cwd = cwd.getParentFile();
		File [] content = cwd.listFiles();
		Arrays.sort(content);
		ArrayList<MdData> result = new ArrayList<>();
		File parent = cwd.getParentFile();
		//	System.err.printf("parent %s of %s%n", parent, cwd);
		if (parent != null) {
		     MdData md = new MdData(getAppModel()) ;
		       md . path = ""+parent;
		      md.name =  "..";
		      md.directory = true;
		      md.last_date = new Date(parent.lastModified());
		      result.add(md);
		}
		for (File entry: content) {
		    if (entry.isDirectory()) {
		       MdData md = new MdData(getAppModel()) ;
		       md . path = ""+entry;
		      md.name =  entry.getName();
		      md.directory = true;
		      md.last_date = new Date(entry.lastModified());
		       result.add(md);
		    } else if (entry.getName().endsWith(MdData.MD)) {
		         MdData md = new MdData(getAppModel() );
		       md . path = ""+entry;
		       md.name =  entry.getName();
		       md.last_date = new Date(entry.lastModified());
		       result.add(md);
		    }
		}
		
		return result;
	}
}
