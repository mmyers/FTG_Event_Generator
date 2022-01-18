// 
// Decompiled by Procyon v0.6-prerelease
// 

package eventgenerator;

import java.io.File;
import javax.swing.filechooser.FileFilter;

public final class TextFileFilter extends FileFilter
{
    private final String[] extensions = new String[] { "txt", "eue" };
    
    @Override
    public boolean accept(final File f) {
        if (f.isDirectory()) {
            return true;
        }
        final String filename = f.getName().toLowerCase();
        for (final String ext : this.extensions) {
            if (filename.endsWith(ext)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String getDescription() {
        return "EU2 event files (TXT and EUE)";
    }
}
