// 
// Decompiled by Procyon v0.6-prerelease
// 

package eventgenerator;

import java.util.regex.Matcher;
import java.util.Date;
import java.util.regex.Pattern;

final class TextProcessor
{
    private String tag;
    private String countryName;
    private int provId;
    private String provName;
    private int eventStart;
    private int eventDeath;
    private int id;
    private static final String TAG = "tag";
    private static final String COUNTRY = "country";
    private static final String PROV_ID = "provid";
    private static final String PROV_NAME = "provname";
    private static final String STARTDATE = "(start)?(date|year)";
    private static final String ENDDATE = "(death|end)(date|year)";
    private static final String TODAY = "today";
    private static final String EVENT_ID = "id";
    private static final String USER = "user(name)?";
    private static final String macroString = "(tag|country|provid|provname|(start)?(date|year)|(death|end)(date|year)|today|id|user(name)?)";
    private static final Pattern macros = Pattern.compile("\\$\\{(tag|country|provid|provname|(start)?(date|year)|(death|end)(date|year)|today|id|user(name)?)\\}", 2);;
    
    TextProcessor(final String tag, final String country, final int provId, final int startYear, final int deathYear, final int id) {
        this.tag = tag;
        this.countryName = country;
        this.provId = provId;
        if (provId < 0) {
            this.provName = null;
        } else {
            this.provName = EventGen.provinceNameArray[indexOf(EventGen.provinceIdArray, Integer.toString(provId))];
        }
        this.eventStart = startYear;
        this.eventDeath = deathYear;
        this.id = id;
    }
    
    String processText(final String text) {
        final Matcher m = macros.matcher(text);
        final StringBuffer sb = new StringBuffer(text.length());
        while (m.find()) {
            final String macro = m.group().substring(2, m.group().length() - 1).toLowerCase();
            if (macro.matches(TAG)) {
                if (this.tag == null) {
                    m.appendReplacement(sb, "<none>");
                }
                else {
                    m.appendReplacement(sb, this.tag);
                }
            }
            else if (macro.matches(COUNTRY)) {
                if (this.countryName == null) {
                    m.appendReplacement(sb, "<none>");
                }
                else {
                    m.appendReplacement(sb, this.countryName);
                }
            }
            else if (macro.matches(PROV_ID)) {
                if (this.provId < 0) {
                    m.appendReplacement(sb, "<none>");
                }
                else {
                    m.appendReplacement(sb, Integer.toString(this.provId));
                }
            }
            else if (macro.matches(PROV_NAME)) {
                if (this.provName == null) {
                    m.appendReplacement(sb, "<none>");
                }
                else {
                    m.appendReplacement(sb, this.provName);
                }
            }
            else if (macro.matches(STARTDATE)) {
                if (this.eventStart < 0) {
                    m.appendReplacement(sb, "<none>");
                }
                else {
                    m.appendReplacement(sb, Integer.toString(this.eventStart));
                }
            }
            else if (macro.matches(ENDDATE)) {
                if (this.eventDeath < 0) {
                    m.appendReplacement(sb, "<none>");
                }
                else {
                    m.appendReplacement(sb, Integer.toString(this.eventDeath));
                }
            }
            else if (macro.matches(TODAY)) {
                m.appendReplacement(sb, EventGen.dateFormatter.format(new Date()));
            }
            else if (macro.matches(EVENT_ID)) {
                m.appendReplacement(sb, Integer.toString(this.id));
            }
            else if (macro.matches(USER)) {
                m.appendReplacement(sb, Config.getString(Config.KEY_USER_NAME));
            }
            else {
                System.err.println("Unknown macro: " + m.group());
                m.appendReplacement(sb, "<none>");
            }
        }
        m.appendTail(sb);
        return sb.toString();
    }
    
    public void setTag(final String tag) {
        this.tag = tag;
    }
    
    public void setCountryName(final String countryName) {
        this.countryName = countryName;
    }
    
    public void setProvId(final int id) {
        this.provId = id;
        if (id < 0) {
            this.provName = null;
        }
        else {
            this.provName = EventGen.provinceNameArray[indexOf(EventGen.provinceIdArray, Integer.toString(id))];
        }
    }
    
    public void setEventStart(final int eventStart) {
        this.eventStart = eventStart;
    }
    
    public void setEventDeath(final int eventDeath) {
        this.eventDeath = eventDeath;
    }
    
    public void setId(final int id) {
        this.id = id;
    }
    
    private static final int indexOf(final Object[] arr, final Object o) {
        for (int i = 0; i < arr.length; ++i) {
            if (arr[i].equals(o)) {
                return i;
            }
        }
        return -1;
    }
}
