package org.beeutil.beemd.model;

import org.aldan3.data.SimpleDataObject;
 import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.aldan3.annot.FormField;
import com.beegman.webbee.util.SimpleCoordinator;
import org.aldan3.util.Stream;
import java.io.File;
import java.util.Date;
import java.io.FileInputStream;

public class MdData extends SimpleCoordinator<BeemdModel>  {
    public final static String MD = ".md";
    
    public MdData (BeemdModel model) {
        super(model);
    }
    
    @FormField()
	public String name;
	
	@FormField
	public String path;
	
		@FormField
	public boolean directory;

	@FormField(presentSize = 68, presentRows = 6)
	public String document;
	
    @FormField
	public Date last_date;
    
    
    public String getDocumentData(String _path) {
        File pathFile = new File(_path);
        if (!pathFile.exists() || !pathFile.isFile() || !pathFile.getName().endsWith(MD))
            throw new RuntimeException("Invalid file "+_path);
             String md;
        try (FileInputStream fis = new FileInputStream(pathFile)) {
           md = Stream.streamToString(fis, "UTF-8", -1);
        } catch(Exception e) {
            md = e.toString();
        }
	    Parser parser = Parser.builder().build();
        Node document = parser.parse(md);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        return renderer.render(document); 
       // return renderer.toString();
	}
}