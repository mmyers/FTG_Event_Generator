// 
// Decompiled by Procyon v0.6-prerelease
// 

package eventgenerator;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import eug.shared.GenericObject;
import eug.parser.EUGFileIO;
import java.util.Date;
import javax.swing.JOptionPane;
import java.util.List;
import java.text.DateFormat;

public class EventGen
{
    public static final String PROG_NAME = "EU2 Event Generator";
    public static final String PROG_VERSION = "1.21";
    private boolean random = false;
    private boolean sequentialDates = true;
    @Deprecated
    private boolean aiOnly = false;
    private boolean includeDates = true;
    private boolean byCountry = true;
    private boolean includeCountry = true;
    private boolean exportText = false;
    public static final int minTextColumn = 1;
    public static final int maxTextColumn = 11;
    private int textColumn = minTextColumn;
    public static final Integer[] allowedColumns = new Integer[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11 };
    private int yearIncrement = 10;
    private int idIncrement = 1;
    private int numOfActs = 1;
    private String headComment = "";
    private String eventName = "AI_EVENT";
    private String eventDesc = "AI_TEXT";;
    private int startYear = 1419;
    private Month startMonth = Month.january;
    private int startDay = 0;
    private int offset = 30;
    private int endYear = 1820;
    private Month endMonth = Month.december;
    private int endDay = 30;
    private String actAName = "action a";
    private String actBName = "action b";
    private String actCName = "action c";
    private String actDName = "action d";
    private String actA = "command = { }";
    private String actB = "command = { }";
    private String actC = "command = { }";
    private String actD = "command = { }";
    private String trigger = "";
    private static final String userName = Config.getString(Config.KEY_USER_NAME);
    public static final DateFormat dateFormatter = DateFormat.getDateInstance(3);
    public static final String DEFAULT_COUNTRY_TAG = "XYZ";
    public static final int DEFAULT_PROVINCE_ID = -512;
    private static final String dataPath = "./Data/";
    private static final String countryListPath = "./Data/countryNames.txt";
    private static final String tagListPath = "./Data/countryTags.txt";
    private static final String provinceIdListPath = "./Data/provinceIds.txt";
    private static final String provinceNameListPath = "./Data/provinceNames.txt";
    public static String[] countryListArray;
    public static String[] tagListArray;
    public static String[] provinceIdArray;
    public static String[] provinceNameArray;
    
    static {
        try {
            System.out.print("Loading data... ");
            countryListArray = initArray(countryListPath);
            tagListArray = initArray(tagListPath);
            provinceIdArray = initArray(provinceIdListPath);
            provinceNameArray = initArray(provinceNameListPath);
            System.out.println("Done");
        }
        catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Fatal error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        catch (IOException e2) {
            JOptionPane.showMessageDialog(null, "Error initializing arrays", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
    
    public EventGen() {
    }
    
    public void generate(final String filename, final boolean overwrite, final List<CountryIDTable.NameTagID> name2ID, final int numEvents) {
        final GenericObject events = createEvents(name2ID, numEvents);
        if (events.size() == 0) {
            JOptionPane.showMessageDialog(null, "Failed to create events!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (EUGFileIO.save(events, filename, "Generated by " + EventGen.userName + " using " + PROG_NAME + " version " + PROG_VERSION + " on " + EventGen.dateFormatter.format(new Date()), overwrite)) {
            JOptionPane.showMessageDialog(null, "Events written!", PROG_NAME, JOptionPane.INFORMATION_MESSAGE, EventGenUI.icon);
        }
        else {
            JOptionPane.showMessageDialog(null, "Failed to write events!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void generateTemplate(final String filename, final boolean overwrite) {
        final GenericObject template = createTemplate();
        if (template.size() == 0) {
            JOptionPane.showMessageDialog(null, "Failed to create template!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (EUGFileIO.save(template, filename, "Template generated by " + EventGen.userName + " using " + PROG_NAME + " version " + PROG_VERSION + " on " + EventGen.dateFormatter.format(new Date()), overwrite)) {
            JOptionPane.showMessageDialog(null, "Events written!", PROG_NAME, JOptionPane.INFORMATION_MESSAGE, EventGenUI.icon);
        }
        else {
            JOptionPane.showMessageDialog(null, "Failed to write template!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public GenericObject createEvents(final List<CountryIDTable.NameTagID> name2ID, final int numEvents) {
        if (endYear != -1) {
            if (endYear < startYear) {
                return null;
            }
            if (endYear == startYear && endMonth != Month.none && startMonth != Month.none) {
                if (endMonth.compareTo(startMonth) < 0) {
                    return null;
                }
                if (endMonth == startMonth && endDay <= startDay) {
                    return null;
                }
            }
        }
        final GenericObject root = new GenericObject();
        StringBuilder localizationText = null;
        if (exportText) {
            localizationText = new StringBuilder(numEvents * 30);
            localizationText.append("Cut the following and paste it into text.csv.\n-----Cut here-----\n");
        }
        final int incrYears = sequentialDates ? yearIncrement : 0;
        final int[] ids = new int[0];
        for (final CountryIDTable.NameTagID ctry : name2ID) {
            int id = ctry.id;
            final String country = ctry.name;
            final String tag = ctry.tag;
            final int prov = byCountry ? -1 : Integer.parseInt(tag);
            int tStartYear = startYear;
            int tEndYear = endYear;
            TextProcessor processor;
            if (byCountry) {
                processor = new TextProcessor(tag, country, prov, startYear, endYear, id);
            }
            else {
                processor = new TextProcessor("", country, Integer.parseInt(tag), startYear, endYear, id);
            }
            for (int i = 0; i < numEvents; ++i, id += idIncrement) {
                if (includeDates && sequentialDates && i != 0) {
                    if (tStartYear != -1) {
                        tStartYear += incrYears;
                    }
                    if (tEndYear != -1) {
                        tEndYear += incrYears;
                    }
                }
                processor.setId(id);
                processor.setEventStart(tStartYear);
                processor.setEventDeath(tEndYear);
                final GenericObject event = root.createChild("event");
                event.addInt("id", id);
                event.addBoolean("random", random);
                final GenericObject trigger = event.createChild("trigger");
                if (random) {
                    trigger.addAllChildren(parseRandomTrigger(i, processor));
                }
                else {
                    trigger.addAllChildren(parseTrigger(processor));
                }
                if (includeCountry) {
                    if (byCountry) {
                        if (tag.equals("") && !random) {
                            JOptionPane.showMessageDialog(null, "Non-random events must specify a country or province", "Error", JOptionPane.ERROR_MESSAGE);
                            return new GenericObject();
                        }
                        event.addString("country", tag);
                    }
                    else {
                        event.addInt("province", prov);
                    }
                }
                else if (!random) {
                    JOptionPane.showMessageDialog(null, "Non-random events must specify a country or province", "Error", JOptionPane.ERROR_MESSAGE);
                    return new GenericObject();
                }
                if (exportText) {
                    final String name = processor.processText(eventName);
                    final String desc = processor.processText(eventDesc);
                    exportText("EVENTNAME" + id, name, localizationText);
                    exportText("EVENTHIST" + id, desc, localizationText);
                    event.addString("name", "EVENTNAME" + id, true, (String)null, name);
                    event.addString("desc", "EVENTHIST" + id, true);
                    event.addGeneralComment("-#" + desc, false);
                }
                else {
                    event.addString("name", processor.processText(eventName), true);
                    event.addString("desc", processor.processText(eventDesc), true);
                }
                if (!random && includeDates) {
                    final GenericObject date = event.createChild("date");
                    if (startDay != -1 && startMonth != Month.none) {
                        date.addInt("day", startDay);
                        date.addString("month", startMonth.toString(), false);
                    }
                    date.addInt("year", tStartYear);
                    if (offset != -1) {
                        event.addInt("offset", offset);
                    }
                    if (endYear != -1) {
                        final GenericObject deathdate = event.createChild("deathdate");
                        if (endDay != -1 && endMonth != Month.none) {
                            deathdate.addInt("day", endDay);
                            deathdate.addString("month", endMonth.toString(), false);
                        }
                        deathdate.addInt("year", tEndYear);
                    }
                }
                switch (numOfActs) {
                    case 1: {
                        if (exportText) {
                            final String name = processor.processText(actAName);
                            exportText("ACTIONNAME" + id + "A", name, localizationText);
                            createAction(event, 'a', "ACTIONNAME" + id + "A", processor.processText(actA), name);
                            break;
                        }
                        createAction(event, 'a', processor.processText(actAName), processor.processText(actA));
                        break;
                    }
                    case 2: {
                        if (exportText) {
                            String name = processor.processText(actAName);
                            exportText("ACTIONNAME" + id + "A", name, localizationText);
                            createAction(event, 'a', "ACTIONNAME" + id + "A", processor.processText(actA), name);
                            name = processor.processText(actBName);
                            exportText("ACTIONNAME" + id + "B", name, localizationText);
                            createAction(event, 'b', "ACTIONNAME" + id + "B", processor.processText(actB), name);
                            break;
                        }
                        createAction(event, 'a', processor.processText(actAName), processor.processText(actA));
                        createAction(event, 'b', processor.processText(actBName), processor.processText(actB));
                        break;
                    }
                    case 3: {
                        if (exportText) {
                            String name = processor.processText(actAName);
                            exportText("ACTIONNAME" + id + "A", name, localizationText);
                            createAction(event, 'a', "ACTIONNAME" + id + "A", processor.processText(actA), name);
                            name = processor.processText(actBName);
                            exportText("ACTIONNAME" + id + "B", name, localizationText);
                            createAction(event, 'b', "ACTIONNAME" + id + "B", processor.processText(actB), name);
                            name = processor.processText(actCName);
                            exportText("ACTIONNAME" + id + "C", name, localizationText);
                            createAction(event, 'c', "ACTIONNAME" + id + "C", processor.processText(actC), name);
                            break;
                        }
                        createAction(event, 'a', processor.processText(actAName), processor.processText(actA));
                        createAction(event, 'b', processor.processText(actBName), processor.processText(actB));
                        createAction(event, 'c', processor.processText(actCName), processor.processText(actC));
                        break;
                    }
                    case 4: {
                        if (exportText) {
                            String name = processor.processText(actAName);
                            exportText("ACTIONNAME" + id + "A", name, localizationText);
                            createAction(event, 'a', "ACTIONNAME" + id + "A", processor.processText(actA), name);
                            name = processor.processText(actBName);
                            exportText("ACTIONNAME" + id + "B", name, localizationText);
                            createAction(event, 'b', "ACTIONNAME" + id + "B", processor.processText(actB), name);
                            name = processor.processText(actCName);
                            exportText("ACTIONNAME" + id + "C", name, localizationText);
                            createAction(event, 'c', "ACTIONNAME" + id + "C", processor.processText(actC), name);
                            name = processor.processText(actDName);
                            exportText("ACTIONNAME" + id + "D", name, localizationText);
                            createAction(event, 'd', "ACTIONNAME" + id + "D", processor.processText(actD), name);
                            break;
                        }
                        createAction(event, 'a', processor.processText(actAName), processor.processText(actA));
                        createAction(event, 'b', processor.processText(actBName), processor.processText(actB));
                        createAction(event, 'c', processor.processText(actCName), processor.processText(actC));
                        createAction(event, 'd', processor.processText(actDName), processor.processText(actD));
                        break;
                    }
                }
                processor.setTag(tag);
                processor.setCountryName(country);
                processor.setProvId(prov);
                event.setHeadComment(processor.processText(headComment));
            }
        }
        if (exportText) {
            localizationText.append("-----Cut here-----");
            root.addFileHeaderComment(localizationText.toString(), false);
        }
        //InlineComment.spaceBeforeComment = false;
        return root;
    }
    
    private void exportText(final String name, final String text, final StringBuilder localizationText) {
        localizationText.append(name);
        for (int i = 0; i < textColumn; ++i) {
            localizationText.append(';');
        }
        localizationText.append(text);
        for (int i = textColumn; i < maxTextColumn; ++i) {
            localizationText.append(';');
        }
        localizationText.append('\n');
    }
    
    public GenericObject createTemplate() {
        if (endYear != -1) {
            if (endYear < startYear) {
                return null;
            }
            if (endYear == startYear && endMonth != Month.none && startMonth != Month.none) {
                if (endMonth.compareTo(startMonth) < 0) {
                    return null;
                }
                if (endMonth == startMonth && endDay <= startDay) {
                    return null;
                }
            }
        }
        final GenericObject root = new GenericObject();
        final int[] ids = new int[0];
        final int id = 16000;
        final String tag = byCountry ? "MIN" : "";
        final int prov = byCountry ? -1 : 0;
        final TextProcessor processor = new TextProcessor(DEFAULT_COUNTRY_TAG, "", DEFAULT_PROVINCE_ID, 0, 0, 0);
        final GenericObject event = root.createChild("event");
        event.addInt("id", id);
        event.addBoolean("random", random);
        final GenericObject trigger = event.createChild("trigger");
        if (random) {
            trigger.addAllChildren(parseRandomTrigger(0, processor));
        }
        else {
            trigger.addAllChildren(parseTrigger(processor));
        }
        if (byCountry) {
            if (tag.equals("")) {
                if (!random) {
                    JOptionPane.showMessageDialog(null, "Non-random events must specify a country", "Error", JOptionPane.ERROR_MESSAGE);
                    return new GenericObject();
                }
            }
            else {
                event.addString("country", tag);
            }
        }
        else {
            event.addInt("province", prov);
        }
        event.addString("name", eventName, true);
        event.addString("desc", eventDesc, true);
        if (!random && includeDates) {
            final GenericObject date = event.createChild("date");
            if (startDay != -1 && startMonth != Month.none) {
                date.addInt("day", startDay);
                date.addString("month", startMonth.toString(), false);
            }
            date.addInt("year", startYear);
            if (offset != -1) {
                event.addInt("offset", offset);
            }
            if (endYear != -1) {
                final GenericObject deathdate = event.createChild("deathdate");
                if (endDay != -1 && endMonth != Month.none) {
                    deathdate.addInt("day", endDay);
                    deathdate.addString("month", endMonth.toString(), false);
                }
                deathdate.addInt("year", endYear);
            }
        }
        switch (numOfActs) {
            case 1: {
                createAction(event, 'a', actAName, actA);
                break;
            }
            case 2: {
                createAction(event, 'a', actAName, actA);
                createAction(event, 'b', actBName, actB);
                break;
            }
            case 3: {
                createAction(event, 'a', actAName, actA);
                createAction(event, 'b', actBName, actB);
                createAction(event, 'c', actCName, actC);
                break;
            }
            case 4: {
                createAction(event, 'a', actAName, actA);
                createAction(event, 'b', actBName, actB);
                createAction(event, 'c', actCName, actC);
                createAction(event, 'd', actDName, actD);
                break;
            }
        }
        event.setHeadComment(headComment);
        return root;
    }
    
    private static void createAction(final GenericObject event, final char act, final String actionName, final String commandText) {
        final GenericObject action = event.createChild("action_" + act);
        action.addString("name", actionName, true);
        action.addAllChildren(EUGFileIO.loadFromString(commandText));
    }
    
    private static void createAction(final GenericObject event, final char act, final String actionName, final String commandText, final String comment) {
        final GenericObject action = event.createChild("action_" + act);
        action.addString("name", actionName, true, (String)null, comment);
        action.addAllChildren(EUGFileIO.loadFromString(commandText));
    }
    
    private GenericObject parseTrigger() {
        return parseTrigger(null);
    }
    
    private GenericObject parseTrigger(final TextProcessor processor) {
        GenericObject trigger;
        if (processor != null) {
            trigger = EUGFileIO.loadFromString(processor.processText(this.trigger));
        }
        else {
            trigger = EUGFileIO.loadFromString(this.trigger);
        }
        return trigger;
    }
    
    private GenericObject parseRandomTrigger(final int eventNum) {
        return parseRandomTrigger(eventNum, null);
    }
    
    private GenericObject parseRandomTrigger(final int eventNum, final TextProcessor processor) {
        GenericObject trigger;
        if (processor != null) {
            trigger = EUGFileIO.loadFromString(processor.processText(this.trigger));
        }
        else {
            trigger = EUGFileIO.loadFromString(this.trigger);
        }
        if (includeDates) {
            final int seqDate = sequentialDates ? yearIncrement : 0;
            if (startYear != -1) {
                trigger.addInt("year", startYear + eventNum * seqDate);
            }
            if (endYear != -1) {
                trigger.createChild("NOT").addInt("year", endYear + eventNum * seqDate);
            }
        }
        return trigger;
    }
    
    public boolean isRandom() {
        return random;
    }
    
    public void setRandom(final boolean random) {
        this.random = random;
    }
    
    public boolean isSequentialDates() {
        return sequentialDates;
    }
    
    public void setSequentialDates(final boolean sequentialDates) {
        this.sequentialDates = sequentialDates;
    }
    
    @Deprecated
    public boolean isAiOnly() {
        return aiOnly;
    }
    
    @Deprecated
    public void setAiOnly(final boolean aiOnly) {
        this.aiOnly = aiOnly;
    }
    
    public boolean isIncludeDates() {
        return includeDates;
    }
    
    public void setIncludeDates(final boolean includeDates) {
        this.includeDates = includeDates;
    }
    
    public boolean isByCountry() {
        return byCountry;
    }
    
    public void setByCountry(final boolean byCountry) {
        this.byCountry = byCountry;
    }
    
    public int getYearIncrement() {
        return yearIncrement;
    }
    
    public void setYearIncrement(final int yearIncrement) {
        this.yearIncrement = yearIncrement;
    }
    
    public int getNumOfActs() {
        return numOfActs;
    }
    
    public void setNumOfActs(final int numOfActs) {
        this.numOfActs = numOfActs;
    }
    
    public int getIdIncrement() {
        return idIncrement;
    }
    
    public void setIdIncrement(final int idIncrement) {
        this.idIncrement = idIncrement;
    }
    
    public String getHeadComment() {
        return headComment;
    }
    
    public void setHeadComment(final String headComment) {
        this.headComment = headComment;
    }
    
    public String getEventName() {
        return eventName;
    }
    
    public void setEventName(final String eventName) {
        this.eventName = eventName;
    }
    
    public String getEventDesc() {
        return eventDesc;
    }
    
    public void setEventDesc(final String eventDesc) {
        this.eventDesc = eventDesc;
    }
    
    public int getStartYear() {
        return startYear;
    }
    
    public void setStartYear(final int startYear) {
        this.startYear = startYear;
    }
    
    public Month getStartMonth() {
        return startMonth;
    }
    
    public void setStartMonth(final Month startMonth) {
        this.startMonth = startMonth;
    }
    
    public int getStartDay() {
        return startDay;
    }
    
    public void setStartDay(final int startDay) {
        this.startDay = startDay;
    }
    
    public int getOffset() {
        return offset;
    }
    
    public void setOffset(final int offset) {
        this.offset = offset;
    }
    
    public int getEndYear() {
        return endYear;
    }
    
    public void setEndYear(final int endYear) {
        this.endYear = endYear;
    }
    
    public Month getEndMonth() {
        return endMonth;
    }
    
    public void setEndMonth(final Month endMonth) {
        this.endMonth = endMonth;
    }
    
    public int getEndDay() {
        return endDay;
    }
    
    public void setEndDay(final int endDay) {
        this.endDay = endDay;
    }
    
    public String getActAName() {
        return actAName;
    }
    
    public void setActAName(final String actAName) {
        this.actAName = actAName;
    }
    
    public String getActBName() {
        return actBName;
    }
    
    public void setActBName(final String actBName) {
        this.actBName = actBName;
    }
    
    public String getActCName() {
        return actCName;
    }
    
    public void setActCName(final String actCName) {
        this.actCName = actCName;
    }
    
    public String getActDName() {
        return actDName;
    }
    
    public void setActDName(final String actDName) {
        this.actDName = actDName;
    }
    
    public String getActA() {
        return actA;
    }
    
    public void setActA(final String actA) {
        this.actA = actA;
    }
    
    public String getActB() {
        return actB;
    }
    
    public void setActB(final String actB) {
        this.actB = actB;
    }
    
    public String getActC() {
        return actC;
    }
    
    public void setActC(final String actC) {
        this.actC = actC;
    }
    
    public String getActD() {
        return actD;
    }
    
    public void setActD(final String actD) {
        this.actD = actD;
    }
    
    public String getTrigger() {
        return trigger;
    }
    
    public void setTrigger(final String trigger) {
        this.trigger = trigger;
    }
    
    public boolean isExportText() {
        return exportText;
    }
    
    public void setExportText(final boolean exportText) {
        this.exportText = exportText;
    }
    
    public int getTextColumn() {
        return textColumn;
    }
    
    public void setTextColumn(final int textColumn) {
        this.textColumn = textColumn;
    }
    
    private static String[] initArray(final String path) throws FileNotFoundException, IOException {
        final File inFile = new File(path);
        final BufferedReader br = new BufferedReader(new FileReader(inFile), (int)inFile.length());
        final StringBuilder sb = new StringBuilder((int)inFile.length());
        while (br.ready()) {
            sb.append(br.readLine()).append("zxcv");
        }
        br.close();
        return sb.toString().split("zxcv");
    }
    
    public boolean isIncludeCountry() {
        return includeCountry;
    }
    
    public void setIncludeCountry(final boolean includeCountry) {
        this.includeCountry = includeCountry;
    }
    
}