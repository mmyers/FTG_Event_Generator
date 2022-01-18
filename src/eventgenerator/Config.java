// 
// Decompiled by Procyon v0.6-prerelease
// 

package eventgenerator;

import eug.parser.EUGFileIO;
import eug.parser.ParserSettings;
import eug.shared.GenericObject;

public final class Config
{
    private static final String CONF_PATH = "eg.conf";
    private static final GenericObject config = EUGFileIO.load(CONF_PATH, ParserSettings.getQuietSettings());
    public static final String KEY_USER_NAME = "user_name";
    public static final String KEY_LAST_SAVE_FILE = "last_save_file";
    public static final String KEY_LAST_LOAD_FILE = "last_load_file";
    
    private Config() {
    }
    
    public static final String getString(final String path) {
        final String[] s = path.split("\\.");
        return normalize(s).getString(s[s.length - 1]);
    }
    
    public static final int getInt(final String path) {
        final String[] s = path.split("\\.");
        return normalize(s).getInt(s[s.length - 1]);
    }
    
    public static final double getDouble(final String path) {
        final String[] s = path.split("\\.");
        return normalize(s).getDouble(s[s.length - 1]);
    }
    
    public static final boolean getBoolean(final String path) {
        final String[] s = path.split("\\.");
        return normalize(s).getString(s[s.length - 1]).equals("yes");
    }
    
    private static GenericObject normalize(final String[] s) {
        GenericObject curr = config;
        for (int i = 0; i < s.length - 1; ++i) {
            curr = curr.getChild(s[i]);
        }
        return curr;
    }
    
    private static GenericObject normalizeForSet(final String[] s) {
        GenericObject curr = config;
        for (int i = 0; i < s.length - 1; ++i) {
            GenericObject tmp = curr.getChild(s[i]);
            if (tmp == null) {
                tmp = curr.createChild(s[i]);
            }
            curr = tmp;
        }
        return curr;
    }
    
    public static final void setString(final String path, final String val, final boolean quotes) {
        final String[] s = path.split("\\.");
        normalizeForSet(s).setString(s[s.length - 1], val, quotes);
    }
    
    public static final void setString(final String path, final String val) {
        setString(path, val, true);
    }
    
    public static final void setInt(final String path, final int val) {
        final String[] s = path.split("\\.");
        normalizeForSet(s).setInt(s[s.length - 1], val);
    }
    
    public static final void setDouble(final String path, final double val) {
        final String[] s = path.split("\\.");
        normalizeForSet(s).setDouble(s[s.length - 1], val);
    }
    
    public static final void setBoolean(final String path, final boolean val) {
        final String[] s = path.split("\\.");
        normalizeForSet(s).setBoolean(s[s.length - 1], val);
    }
    
    public static final void saveConfig() {
        EUGFileIO.save(config, "eg.conf", EUGFileIO.NO_COMMENT, true);
    }
}
