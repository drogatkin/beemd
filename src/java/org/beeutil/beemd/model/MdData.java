package org.beeutil.beemd.model;

import org.aldan3.data.SimpleDataObject;
 import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.commonmark.Extension;
import org.commonmark.ext.heading.anchor.HeadingAnchorExtension;
import org.commonmark.ext.gfm.tables.TablesExtension;
import org.commonmark.ext.gfm.strikethrough.StrikethroughExtension;
import org.commonmark.ext.ins.InsExtension;
import org.commonmark.ext.image.attributes.ImageAttributesExtension;
import org.commonmark.ext.task.list.items.TaskListItemsExtension;
import org.aldan3.annot.FormField;
import com.beegman.webbee.util.SimpleCoordinator;
import org.aldan3.util.Stream;
import java.io.File;
import java.util.Date;
import java.io.FileInputStream;
import java.util.List;
import java.util.Arrays;

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
        List<Extension> extensions = Arrays.asList(HeadingAnchorExtension.create(), TablesExtension.create(), StrikethroughExtension.create(),
             InsExtension.create(), ImageAttributesExtension.create(), TaskListItemsExtension.create());

	    Parser parser = Parser.builder().extensions(extensions).build();
        Node document = parser.parse(md);
        HtmlRenderer renderer = HtmlRenderer.builder().extensions(extensions).build();
        return renderer.render(document); 
	}
}