// 
// Decompiled by Procyon v0.6-prerelease
// 

package eventgenerator;

import java.util.HashMap;
import java.util.Map;

public enum Mode
{
    COUNTRY(EventGen.countryListArray, EventGen.tagListArray), 
    PROVINCE(EventGen.provinceNameArray, EventGen.provinceIdArray);
    
    private String defaultName;
    private Map<String, String> countryToTag;
    private Map<String, String> tagToCountry;
    
    private Mode(final String[] names, final String[] ids) {
        defaultName = names[0];
        countryToTag = new HashMap<String, String>(names.length);
        tagToCountry = new HashMap<String, String>(names.length);
        for (int i = 0; i < names.length; ++i) {
            countryToTag.put(names[i], ids[i]);
            tagToCountry.put(ids[i], names[i]);
        }
    }
    
    public String getDefaultName() {
        return defaultName;
    }
    
    public Map<String, String> getCountryToTag() {
        return countryToTag;
    }
    
    public Map<String, String> getTagToCountry() {
        return tagToCountry;
    }
}
