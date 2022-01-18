// 
// Decompiled by Procyon v0.6-prerelease
// 

package eventgenerator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;
import eug.parser.EUGFileIO;
import eug.parser.ParserSettings;
import eug.shared.GenericObject;

public class TemplateManager
{
    private static final String TEMPLATES_PATH = "Templates/";
    private static final String TEMPLATE_LIST_PATH = "Templates/template-list.txt";
    private GenericObject templates;
    private boolean unsavedChanges = false;
    
    public TemplateManager() {
        templates = EUGFileIO.load(TEMPLATE_LIST_PATH, ParserSettings.getQuietSettings());
        validateTemplates();
    }
    
    public void registerTemplate(final String name, final String desc, String filename) {
        filename = filename.replace('\\', '/');
        if (!filename.startsWith(TEMPLATES_PATH) && filename.contains(TEMPLATES_PATH)) {
            filename = filename.substring(filename.indexOf(TEMPLATES_PATH));
        }
        for (final GenericObject template : this.templates.children) {
            if (template.getString("name").equals(name)) {
                template.setString("desc", desc, true);
                template.setString("file", filename, true);
                this.unsavedChanges = true;
                return;
            }
        }
        final GenericObject template2 = this.templates.createChild("template");
        template2.addString("name", name, true);
        template2.addString("desc", desc, true);
        template2.addString("file", filename, true);
        this.unsavedChanges = true;
    }
    
    public void saveTemplates() {
        if (this.unsavedChanges) {
            EUGFileIO.save(this.templates, TEMPLATE_LIST_PATH, EUGFileIO.NO_COMMENT);
        }
    }
    
    public List<GenericObject> getTemplates() {
        return this.templates.children;
    }
    
    private void validateTemplates() {
        final List<GenericObject> toRemove = new ArrayList<GenericObject>();
        for (final GenericObject template : this.templates.children) {
            if (!template.contains("name") || !template.contains("desc") || !template.contains("file")) {
                System.err.println("Bad template definition:\n" + template);
                toRemove.add(template);
            }
            else {
                if (new File(template.getString("file")).canRead()) {
                    continue;
                }
                System.err.println("Template " + template.getString("file") + " not found!");
                toRemove.add(template);
            }
        }
        for (final GenericObject bad : toRemove) {
            this.templates.removeChild(bad);
        }
    }
}
