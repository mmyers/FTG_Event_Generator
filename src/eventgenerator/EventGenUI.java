// 
// Decompiled by Procyon v0.6-prerelease
// 

package eventgenerator;

import eug.parser.EUGFileIO;
import eug.parser.ParserSettings;
import eug.shared.ObjectVariable;
import eug.specific.eu2.Event;
import java.util.Arrays;
import eug.specific.eu2.EU2Scenario;
import eug.specific.eu2.EventDatabase;
import eug.shared.InlineComment;
import java.util.List;
import javax.swing.filechooser.FileFilter;
import java.awt.Frame;
import javax.swing.JFileChooser;
import java.io.File;
import javax.swing.Icon;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import java.awt.event.WindowEvent;
import java.awt.event.MouseEvent;
import java.awt.event.FocusEvent;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import javax.swing.KeyStroke;
import java.awt.FlowLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.AbstractButton;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.LayoutManager;
import java.awt.GridBagLayout;
import java.awt.Dimension;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import java.awt.Insets;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import eug.shared.GenericObject;
import java.awt.Component;
import javax.swing.UIManager;
import javax.swing.JTable;
import javax.swing.JMenuBar;
import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JMenuItem;
import javax.swing.ImageIcon;
import javax.swing.event.ChangeListener;
import java.awt.event.FocusListener;
import java.awt.event.MouseListener;
import java.awt.event.WindowListener;
import java.awt.event.ActionListener;
import javax.swing.JFrame;

public class EventGenUI extends JFrame implements ActionListener, WindowListener, MouseListener, FocusListener, ChangeListener
{
    private EventGen generator;
    private TemplateManager templateManager;
    private String lastSaveFile;
    private String lastLoadFile;
    public static final ImageIcon icon = new ImageIcon(EventGenUI.class.getResource("/eventgenerator/eg.gif"));
    private static final String yearFormatString = "####";
    private static final String dayFormatString = "####";
    private JMenuItem aboutMenuItem;
    private JTextArea actACommandArea;
    private JTextField actANameField;
    private JTextArea actBCommandArea;
    private JTextField actBNameField;
    private JTextArea actCCommandArea;
    private JTextField actCNameField;
    private JTextArea actDCommandArea;
    private JTextField actDNameField;
    private JPanel actionsPanel;
    private JButton addRowButton;
    private JCheckBoxMenuItem aiOnlyMenuItem;
    private ButtonGroup countryProvButtonGroup;
    private JRadioButton countryRadioButton;
    private JPanel ctryTablePanel;
    private JPanel datesPanel;
    private JTextArea descArea;
    private JSpinner endDateSpinner;
    private JSpinner endDaySpinner;
    private JComboBox endMonthComboBox;
    private JTextField eventNameField;
    private JButton exitButton;
    private JMenuItem exitMenuItem;
    private JCheckBox exportTextCheckBox;
    private JCheckBoxMenuItem exportTextMenuItem;
    private JMenu fileMenu;
    private JPanel firstPanel;
    private JButton generateButton;
    private JMenuItem generateMenuItem;
    private JPanel headCommentPanel;
    private JTextArea headCommentTextArea;
    private JMenu helpMenu;
    private JPanel idIncrPanel;
    private JCheckBoxMenuItem inclDatesMenuItem;
    private JCheckBox includeCountryCheckBox;
    private JCheckBox includeDatesCheckBox;
    private JSpinner incrIDSpinner;
    private JLabel jLabel1;
    private JLabel jLabel11;
    private JLabel jLabel12;
    private JLabel jLabel13;
    private JLabel jLabel14;
    private JLabel jLabel15;
    private JLabel jLabel16;
    private JLabel jLabel19;
    private JLabel jLabel2;
    private JLabel jLabel20;
    private JLabel jLabel21;
    private JLabel jLabel22;
    private JLabel jLabel23;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JPanel jPanel1;
    private JPanel jPanel2;
    private JPanel jPanel27;
    private JPanel jPanel3;
    private JPanel jPanel4;
    private JPanel jPanel5;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JScrollPane jScrollPane3;
    private JScrollPane jScrollPane4;
    private JScrollPane jScrollPane5;
    private JScrollPane jScrollPane6;
    private JScrollPane jScrollPane7;
    private JScrollPane jScrollPane8;
    private JSeparator jSeparator1;
    private JSeparator jSeparator2;
    private JSeparator jSeparator3;
    private JSeparator jSeparator4;
    private JSeparator jSeparator5;
    private JSeparator jSeparator6;
    private JSeparator jSeparator7;
    private JSeparator jSeparator8;
    private JSeparator jSeparator9;
    private JMenuItem loadMenuItem;
    private JPanel lowerPanel;
    private JMenuBar menuBar;
    private JComboBox numActsComboBox;
    private JSpinner numOfEventsSpinner;
    private JSpinner offsetSpinner;
    private JMenu optionsMenu;
    private JPanel optionsPanel;
    private JButton previewButton;
    private JRadioButton provRadioButton;
    private JCheckBox randomCheckBox;
    private JCheckBoxMenuItem randomMenuItem;
    private JButton removeRowButton;
    private JMenuItem saveTemplateMenuItem;
    private JPanel secondPanel;
    private JCheckBox seqDatesCheckBox;
    private JCheckBoxMenuItem seqDatesMenuItem;
    private JMenuItem setNameMenuItem;
    private JMenuItem setTextColumnMenuItem;
    private JSpinner startDateSpinner;
    private JSpinner startDaySpinner;
    private JComboBox startMonthComboBox;
    private JTable table;
    private JMenu templatesMenu;
    private JTextArea triggerArea;
    private JMenuItem yearIncrMenuItem;
    private JSpinner yearIncrSpinner;
    
    public EventGenUI() {
        lastSaveFile = Config.getString(Config.KEY_LAST_SAVE_FILE);
        lastLoadFile = Config.getString(Config.KEY_LAST_LOAD_FILE);
        if (UIManager.getSystemLookAndFeelClassName().contains("Windows")) {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
            catch (Exception ex) {}
        }
        generator = new EventGen();
        templateManager = new TemplateManager();
        initComponents();
        setLocationRelativeTo(null);
    }
    
    private void initComponents() {
        this.countryProvButtonGroup = new ButtonGroup();
        this.optionsPanel = new JPanel();
        this.randomCheckBox = new JCheckBox();
        this.includeDatesCheckBox = new JCheckBox();
        this.seqDatesCheckBox = new JCheckBox();
        this.jPanel2 = new JPanel();
        this.jLabel1 = new JLabel();
        this.yearIncrSpinner = new JSpinner();
        this.jSeparator9 = new JSeparator();
        this.exportTextCheckBox = new JCheckBox();
        final JPanel centerPanel = new JPanel();
        this.firstPanel = new JPanel();
        this.jPanel5 = new JPanel();
        this.jPanel4 = new JPanel();
        final JPanel numEventsPanel = new JPanel();
        this.jLabel12 = new JLabel();
        this.numOfEventsSpinner = new JSpinner();
        this.jSeparator6 = new JSeparator();
        this.idIncrPanel = new JPanel();
        this.jLabel23 = new JLabel();
        this.incrIDSpinner = new JSpinner();
        this.headCommentPanel = new JPanel();
        this.jScrollPane7 = new JScrollPane();
        this.headCommentTextArea = new JTextArea();
        this.ctryTablePanel = new JPanel();
        this.jPanel1 = new JPanel();
        this.countryRadioButton = new JRadioButton();
        this.provRadioButton = new JRadioButton();
        final JPanel jPanel9 = new JPanel();
        this.jScrollPane8 = new JScrollPane();
        this.table = new CountryIDTable();
        this.jPanel3 = new JPanel();
        this.addRowButton = new JButton();
        this.removeRowButton = new JButton();
        this.includeCountryCheckBox = new JCheckBox();
        this.jSeparator2 = new JSeparator();
        this.secondPanel = new JPanel();
        final JPanel triggerPanel = new JPanel();
        this.jScrollPane2 = new JScrollPane();
        this.triggerArea = new JTextArea();
        final JPanel eventNameDescPanel = new JPanel();
        this.eventNameField = new JTextField();
        this.jScrollPane1 = new JScrollPane();
        this.descArea = new JTextArea();
        this.jSeparator1 = new JSeparator();
        this.datesPanel = new JPanel();
        final JPanel startDatePanel = new JPanel();
        this.jLabel21 = new JLabel();
        this.startDaySpinner = new JSpinner();
        this.jLabel19 = new JLabel();
        this.startMonthComboBox = new JComboBox();
        this.jLabel2 = new JLabel();
        this.startDateSpinner = new JSpinner();
        final JPanel offsetPanel = new JPanel();
        this.jLabel4 = new JLabel();
        this.offsetSpinner = new JSpinner();
        final JPanel endDatePanel = new JPanel();
        this.jLabel22 = new JLabel();
        this.endDaySpinner = new JSpinner();
        this.jLabel20 = new JLabel();
        this.endMonthComboBox = new JComboBox();
        this.jLabel3 = new JLabel();
        this.endDateSpinner = new JSpinner();
        this.jSeparator5 = new JSeparator();
        final JPanel numActsPanel = new JPanel();
        this.jLabel11 = new JLabel();
        this.numActsComboBox = new JComboBox();
        this.actionsPanel = new JPanel();
        final JPanel actAPanel = new JPanel();
        final JPanel jPanel10 = new JPanel();
        this.jLabel13 = new JLabel();
        this.actANameField = new JTextField();
        this.jScrollPane3 = new JScrollPane();
        this.actACommandArea = new JTextArea();
        final JPanel actBPanel = new JPanel();
        this.jPanel27 = new JPanel();
        this.jLabel14 = new JLabel();
        this.actBNameField = new JTextField();
        this.jScrollPane4 = new JScrollPane();
        this.actBCommandArea = new JTextArea();
        final JPanel actCPanel = new JPanel();
        final JPanel jPanel11 = new JPanel();
        this.jLabel15 = new JLabel();
        this.actCNameField = new JTextField();
        this.jScrollPane5 = new JScrollPane();
        this.actCCommandArea = new JTextArea();
        final JPanel actDPanel = new JPanel();
        final JPanel jPanel12 = new JPanel();
        this.jLabel16 = new JLabel();
        this.actDNameField = new JTextField();
        this.jScrollPane6 = new JScrollPane();
        this.actDCommandArea = new JTextArea();
        this.lowerPanel = new JPanel();
        this.generateButton = new JButton();
        this.previewButton = new JButton();
        this.exitButton = new JButton();
        this.menuBar = new JMenuBar();
        this.fileMenu = new JMenu();
        this.generateMenuItem = new JMenuItem();
        this.loadMenuItem = new JMenuItem();
        this.templatesMenu = new JMenu();
        for (final GenericObject template : this.templateManager.getTemplates()) {
            final JMenuItem jmi = new JMenuItem(template.getString("name"));
            jmi.setToolTipText(template.getString("desc"));
            jmi.addActionListener(new TemplateMenuItemListener(template.getString("file")));
            this.templatesMenu.add(jmi);
        }
        this.jSeparator7 = new JSeparator();
        this.saveTemplateMenuItem = new JMenuItem();
        this.jSeparator4 = new JSeparator();
        this.exitMenuItem = new JMenuItem();
        this.optionsMenu = new JMenu();
        this.randomMenuItem = new JCheckBoxMenuItem();
        this.inclDatesMenuItem = new JCheckBoxMenuItem();
        this.seqDatesMenuItem = new JCheckBoxMenuItem();
        this.yearIncrMenuItem = new JMenuItem();
        this.aiOnlyMenuItem = new JCheckBoxMenuItem();
        this.jSeparator3 = new JSeparator();
        this.exportTextMenuItem = new JCheckBoxMenuItem();
        this.setTextColumnMenuItem = new JMenuItem();
        this.jSeparator8 = new JSeparator();
        this.setNameMenuItem = new JMenuItem();
        this.helpMenu = new JMenu();
        this.aboutMenuItem = new JMenuItem();
        this.setDefaultCloseOperation(3);
        this.setTitle(EventGen.PROG_NAME);
        this.setIconImage(EventGenUI.icon.getImage());
        this.setResizable(false);
        this.addWindowListener(this);
        this.optionsPanel.setBorder(BorderFactory.createTitledBorder("Options"));
        this.randomCheckBox.setText("Random");
        this.randomCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.randomCheckBox.setHorizontalTextPosition(10);
        this.randomCheckBox.setMargin(new Insets(0, 0, 0, 0));
        this.randomCheckBox.addActionListener(this);
        this.optionsPanel.add(this.randomCheckBox);
        this.includeDatesCheckBox.setSelected(true);
        this.includeDatesCheckBox.setText("Include dates");
        this.includeDatesCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.includeDatesCheckBox.setHorizontalTextPosition(10);
        this.includeDatesCheckBox.setMargin(new Insets(0, 0, 0, 0));
        this.includeDatesCheckBox.addActionListener(this);
        this.optionsPanel.add(this.includeDatesCheckBox);
        this.seqDatesCheckBox.setSelected(true);
        this.seqDatesCheckBox.setText("Sequential dates");
        this.seqDatesCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.seqDatesCheckBox.setHorizontalTextPosition(10);
        this.seqDatesCheckBox.setMargin(new Insets(0, 0, 0, 0));
        this.seqDatesCheckBox.addActionListener(this);
        this.optionsPanel.add(this.seqDatesCheckBox);
        this.jLabel1.setText("Year increment:");
        this.jPanel2.add(this.jLabel1);
        this.yearIncrSpinner.setModel(new SpinnerNumberModel(10, -100, 100, 1));
        this.yearIncrSpinner.setPreferredSize(new Dimension(40, 20));
        this.yearIncrSpinner.addChangeListener(this);
        this.jPanel2.add(this.yearIncrSpinner);
        this.optionsPanel.add(this.jPanel2);
        this.jSeparator9.setOrientation(1);
        this.jSeparator9.setPreferredSize(new Dimension(5, 30));
        this.optionsPanel.add(this.jSeparator9);
        this.exportTextCheckBox.setText("Export text");
        this.exportTextCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.exportTextCheckBox.setHorizontalTextPosition(10);
        this.exportTextCheckBox.setMargin(new Insets(0, 0, 0, 0));
        this.exportTextCheckBox.addActionListener(this);
        this.optionsPanel.add(this.exportTextCheckBox);
        this.getContentPane().add(this.optionsPanel, "North");
        centerPanel.setLayout(new GridBagLayout());
        this.firstPanel.setLayout(new GridBagLayout());
        this.jPanel5.setLayout(new BorderLayout());
        this.jPanel4.setBorder(BorderFactory.createEtchedBorder());
        this.jLabel12.setText("Number of events:");
        numEventsPanel.add(this.jLabel12);
        this.numOfEventsSpinner.setModel(new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        this.numOfEventsSpinner.setPreferredSize(new Dimension(40, 20));
        numEventsPanel.add(this.numOfEventsSpinner);
        this.jPanel4.add(numEventsPanel);
        this.jSeparator6.setOrientation(1);
        this.jSeparator6.setPreferredSize(new Dimension(2, 30));
        this.jPanel4.add(this.jSeparator6);
        this.jLabel23.setText("ID increment:");
        this.idIncrPanel.add(this.jLabel23);
        this.incrIDSpinner.setModel(new SpinnerNumberModel(1, Integer.MIN_VALUE, Integer.MAX_VALUE, 1));
        this.incrIDSpinner.setPreferredSize(new Dimension(40, 20));
        this.incrIDSpinner.addChangeListener(this);
        this.idIncrPanel.add(this.incrIDSpinner);
        this.jPanel4.add(this.idIncrPanel);
        this.jPanel5.add(this.jPanel4, "North");
        this.headCommentPanel.setLayout(new BorderLayout());
        this.headCommentPanel.setBorder(BorderFactory.createTitledBorder("Head Comment"));
        this.jScrollPane7.setPreferredSize(new Dimension(300, 52));
        this.headCommentTextArea.setColumns(20);
        this.headCommentTextArea.setFont(new Font("Tahoma", 0, 11));
        this.headCommentTextArea.setRows(5);
        this.headCommentTextArea.addFocusListener(this);
        this.jScrollPane7.setViewportView(this.headCommentTextArea);
        this.headCommentPanel.add(this.jScrollPane7, "Center");
        this.jPanel5.add(this.headCommentPanel, "Center");
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = 1;
        this.firstPanel.add(this.jPanel5, gridBagConstraints);
        this.ctryTablePanel.setLayout(new BorderLayout());
        this.ctryTablePanel.setBorder(BorderFactory.createTitledBorder("Country/Province and Event ID"));
        this.jPanel1.setLayout(new GridLayout(1, 0));
        this.countryProvButtonGroup.add(this.countryRadioButton);
        this.countryRadioButton.setSelected(true);
        this.countryRadioButton.setText("Country-based");
        this.countryRadioButton.setToolTipText("Events happen for specified countries");
        this.countryRadioButton.setHorizontalAlignment(0);
        this.countryRadioButton.addActionListener(this);
        this.jPanel1.add(this.countryRadioButton);
        this.countryProvButtonGroup.add(this.provRadioButton);
        this.provRadioButton.setText("Province-based");
        this.provRadioButton.setToolTipText("Events happen for owners of specified provinces");
        this.provRadioButton.setHorizontalAlignment(0);
        this.provRadioButton.addActionListener(this);
        this.jPanel1.add(this.provRadioButton);
        this.ctryTablePanel.add(this.jPanel1, "North");
        jPanel9.setLayout(new BorderLayout());
        jPanel9.setPreferredSize(new Dimension(350, 80));
        this.jScrollPane8.setPreferredSize(new Dimension(452, 500));
        this.table.addMouseListener(this);
        this.jScrollPane8.setViewportView(this.table);
        jPanel9.add(this.jScrollPane8, "Center");
        this.jPanel3.setLayout(new GridBagLayout());
        this.addRowButton.setText("Add");
        this.addRowButton.setToolTipText("Add a row to the table");
        this.addRowButton.addActionListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = 2;
        gridBagConstraints.insets = new Insets(0, 0, 1, 0);
        this.jPanel3.add(this.addRowButton, gridBagConstraints);
        this.removeRowButton.setText("Remove");
        this.removeRowButton.setToolTipText("Remove the selected row from the table");
        this.removeRowButton.setEnabled(false);
        this.removeRowButton.addActionListener(this);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        this.jPanel3.add(this.removeRowButton, gridBagConstraints);
        jPanel9.add(this.jPanel3, "East");
        this.ctryTablePanel.add(jPanel9, "Center");
        this.includeCountryCheckBox.setSelected(true);
        this.includeCountryCheckBox.setText("Include country tag in event");
        this.includeCountryCheckBox.setToolTipText("<html>If selected, the events will include a \"country = xxx\" field.<br>Otherwise, the selected countries will only be used for expanding the ${tag} macro.</html>");
        this.includeCountryCheckBox.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        this.includeCountryCheckBox.setMargin(new Insets(0, 0, 0, 0));
        this.includeCountryCheckBox.addActionListener(this);
        this.ctryTablePanel.add(this.includeCountryCheckBox, "Last");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = 1;
        this.firstPanel.add(this.ctryTablePanel, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = 2;
        centerPanel.add(this.firstPanel, gridBagConstraints);
        this.jSeparator2.setPreferredSize(new Dimension(800, 2));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = 2;
        centerPanel.add(this.jSeparator2, gridBagConstraints);
        this.secondPanel.setLayout(new GridLayout(1, 0));
        triggerPanel.setLayout(new BorderLayout());
        triggerPanel.setBorder(BorderFactory.createTitledBorder("Trigger"));
        this.jScrollPane2.setPreferredSize(new Dimension(300, 100));
        this.triggerArea.setFont(new Font("Tahoma", 0, 11));
        this.triggerArea.setWrapStyleWord(true);
        this.triggerArea.setName("trigger");
        this.triggerArea.addFocusListener(this);
        this.jScrollPane2.setViewportView(this.triggerArea);
        triggerPanel.add(this.jScrollPane2, "Center");
        this.secondPanel.add(triggerPanel);
        eventNameDescPanel.setLayout(new BorderLayout(0, 1));
        eventNameDescPanel.setBorder(BorderFactory.createTitledBorder("Event Name and Description"));
        this.eventNameField.setText("AI_EVENT");
        this.eventNameField.setName("name");
        this.eventNameField.setPreferredSize(new Dimension(300, 19));
        this.eventNameField.addFocusListener(this);
        eventNameDescPanel.add(this.eventNameField, "North");
        this.jScrollPane1.setPreferredSize(new Dimension(300, 100));
        this.descArea.setFont(new Font("Tahoma", 0, 11));
        this.descArea.setLineWrap(true);
        this.descArea.setText("AI_TEXT");
        this.descArea.setWrapStyleWord(true);
        this.descArea.setName("desc");
        this.descArea.addFocusListener(this);
        this.jScrollPane1.setViewportView(this.descArea);
        eventNameDescPanel.add(this.jScrollPane1, "Center");
        this.secondPanel.add(eventNameDescPanel);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = 2;
        centerPanel.add(this.secondPanel, gridBagConstraints);
        this.jSeparator1.setPreferredSize(new Dimension(800, 2));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = 2;
        centerPanel.add(this.jSeparator1, gridBagConstraints);
        startDatePanel.setBorder(BorderFactory.createTitledBorder("Start Date"));
        this.jLabel21.setText("Day");
        startDatePanel.add(this.jLabel21);
        this.startDaySpinner.setModel(new SpinnerNumberModel(1, -1, 30, 1));
        this.startDaySpinner.setToolTipText("Starting day of the event (required unless starting month is \"none\").");
        this.startDaySpinner.setEditor(new JSpinner.NumberEditor(this.startDaySpinner, dayFormatString));
        this.startDaySpinner.addChangeListener(this);
        startDatePanel.add(this.startDaySpinner);
        this.jLabel19.setText("Month");
        startDatePanel.add(this.jLabel19);
        this.startMonthComboBox.setModel(new DefaultComboBoxModel(Month.values()));
        this.startMonthComboBox.setToolTipText("Starting month of the event. If \"none\", no month is given.");
        this.startMonthComboBox.addActionListener(this);
        startDatePanel.add(this.startMonthComboBox);
        this.jLabel2.setText("Year");
        startDatePanel.add(this.jLabel2);
        this.startDateSpinner.setModel(new SpinnerNumberModel(1419, 1, 9999, 1));
        this.startDateSpinner.setToolTipText("Starting year of the event (required).");
        this.startDateSpinner.setEditor(new JSpinner.NumberEditor(this.startDateSpinner, yearFormatString));
        this.startDateSpinner.setPreferredSize(new Dimension(50, 20));
        this.startDateSpinner.addChangeListener(this);
        startDatePanel.add(this.startDateSpinner);
        this.datesPanel.add(startDatePanel);
        this.jLabel4.setText("Offset");
        offsetPanel.add(this.jLabel4);
        this.offsetSpinner.setModel(new SpinnerNumberModel(30, -1, Integer.MAX_VALUE, 1));
        this.offsetSpinner.setToolTipText("Offset of the event, in days. If -1, no offset is given.");
        this.offsetSpinner.setEditor(new JSpinner.NumberEditor(this.offsetSpinner, dayFormatString));
        this.offsetSpinner.setPreferredSize(new Dimension(50, 20));
        this.offsetSpinner.addChangeListener(this);
        offsetPanel.add(this.offsetSpinner);
        this.datesPanel.add(offsetPanel);
        endDatePanel.setBorder(BorderFactory.createTitledBorder("End Date"));
        this.jLabel22.setText("Day");
        endDatePanel.add(this.jLabel22);
        this.endDaySpinner.setModel(new SpinnerNumberModel(30, -1, 30, 1));
        this.endDaySpinner.setToolTipText("Ending day of the event (required if end year is not -1 and end month is not \"none\").");
        this.endDaySpinner.setEditor(new JSpinner.NumberEditor(this.endDaySpinner, dayFormatString));
        this.endDaySpinner.addChangeListener(this);
        endDatePanel.add(this.endDaySpinner);
        this.jLabel20.setText("Month");
        endDatePanel.add(this.jLabel20);
        this.endMonthComboBox.setModel(new DefaultComboBoxModel(Month.values()));
        this.endMonthComboBox.setSelectedItem(Month.december);
        this.endMonthComboBox.setToolTipText("Ending month of the event. If \"none\", no month is given.");
        this.endMonthComboBox.addActionListener(this);
        endDatePanel.add(this.endMonthComboBox);
        this.jLabel3.setText("Year");
        endDatePanel.add(this.jLabel3);
        this.endDateSpinner.setModel(new SpinnerNumberModel(1819, -1, 9999, 1));
        this.endDateSpinner.setToolTipText("Ending year of the event. If -1, no end year is given.");
        this.endDateSpinner.setEditor(new JSpinner.NumberEditor(this.endDateSpinner, yearFormatString));
        this.endDateSpinner.setPreferredSize(new Dimension(50, 20));
        this.endDateSpinner.addChangeListener(this);
        endDatePanel.add(this.endDateSpinner);
        this.datesPanel.add(endDatePanel);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = 2;
        centerPanel.add(this.datesPanel, gridBagConstraints);
        this.jSeparator5.setPreferredSize(new Dimension(800, 2));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = 2;
        centerPanel.add(this.jSeparator5, gridBagConstraints);
        this.jLabel11.setText("Number of actions");
        numActsPanel.add(this.jLabel11);
        this.numActsComboBox.setMaximumRowCount(4);
        this.numActsComboBox.setModel(new DefaultComboBoxModel(new String[] { "1", "2", "3", "4" }));
        this.numActsComboBox.addActionListener(this);
        numActsPanel.add(this.numActsComboBox);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = 2;
        centerPanel.add(numActsPanel, gridBagConstraints);
        this.actionsPanel.setLayout(new GridLayout(2, 2, 2, 2));
        this.actionsPanel.setBorder(BorderFactory.createTitledBorder("Actions"));
        this.actionsPanel.setMinimumSize(new Dimension(616, 270));
        actAPanel.setLayout(new BorderLayout());
        actAPanel.setName("action_a");
        jPanel10.setLayout(new BorderLayout(5, 0));
        this.jLabel13.setText("Name:");
        jPanel10.add(this.jLabel13, "West");
        this.actANameField.setText("action a");
        this.actANameField.setName("name");
        this.actANameField.setPreferredSize(new Dimension(200, 20));
        this.actANameField.addActionListener(this);
        this.actANameField.addFocusListener(this);
        jPanel10.add(this.actANameField, "Center");
        actAPanel.add(jPanel10, "North");
        this.jScrollPane3.setMinimumSize(new Dimension(300, 100));
        this.actACommandArea.setFont(new Font("Tahoma", 0, 11));
        this.actACommandArea.setRows(7);
        this.actACommandArea.setText("command = { }");
        this.actACommandArea.setWrapStyleWord(true);
        this.actACommandArea.addFocusListener(this);
        this.jScrollPane3.setViewportView(this.actACommandArea);
        actAPanel.add(this.jScrollPane3, "Center");
        this.actionsPanel.add(actAPanel);
        actBPanel.setLayout(new BorderLayout());
        actBPanel.setName("action_b");
        this.jPanel27.setLayout(new BorderLayout());
        this.jLabel14.setText("Name:");
        this.jLabel14.setEnabled(false);
        this.jPanel27.add(this.jLabel14, "West");
        this.actBNameField.setText("action b");
        this.actBNameField.setEnabled(false);
        this.actBNameField.setName("name");
        this.actBNameField.addActionListener(this);
        this.actBNameField.addFocusListener(this);
        this.jPanel27.add(this.actBNameField, "Center");
        actBPanel.add(this.jPanel27, "North");
        this.jScrollPane4.setMinimumSize(new Dimension(300, 100));
        this.actBCommandArea.setFont(new Font("Tahoma", 0, 11));
        this.actBCommandArea.setRows(7);
        this.actBCommandArea.setText("command = { }");
        this.actBCommandArea.setWrapStyleWord(true);
        this.actBCommandArea.setEnabled(false);
        this.actBCommandArea.addFocusListener(this);
        this.jScrollPane4.setViewportView(this.actBCommandArea);
        actBPanel.add(this.jScrollPane4, "Center");
        this.actionsPanel.add(actBPanel);
        actCPanel.setLayout(new BorderLayout());
        actCPanel.setName("action_c");
        jPanel11.setLayout(new BorderLayout(5, 0));
        this.jLabel15.setText("Name:");
        this.jLabel15.setEnabled(false);
        jPanel11.add(this.jLabel15, "West");
        this.actCNameField.setText("action c");
        this.actCNameField.setEnabled(false);
        this.actCNameField.setName("name");
        this.actCNameField.setPreferredSize(new Dimension(200, 20));
        this.actCNameField.addActionListener(this);
        this.actCNameField.addFocusListener(this);
        jPanel11.add(this.actCNameField, "Center");
        actCPanel.add(jPanel11, "North");
        this.jScrollPane5.setMinimumSize(new Dimension(300, 100));
        this.actCCommandArea.setFont(new Font("Tahoma", 0, 11));
        this.actCCommandArea.setRows(7);
        this.actCCommandArea.setText("command = { }");
        this.actCCommandArea.setWrapStyleWord(true);
        this.actCCommandArea.setEnabled(false);
        this.actCCommandArea.addFocusListener(this);
        this.jScrollPane5.setViewportView(this.actCCommandArea);
        actCPanel.add(this.jScrollPane5, "Center");
        this.actionsPanel.add(actCPanel);
        actDPanel.setLayout(new BorderLayout());
        actDPanel.setName("action_d");
        jPanel12.setLayout(new BorderLayout(5, 0));
        this.jLabel16.setText("Name:");
        this.jLabel16.setEnabled(false);
        jPanel12.add(this.jLabel16, "West");
        this.actDNameField.setText("action d");
        this.actDNameField.setEnabled(false);
        this.actDNameField.setPreferredSize(new Dimension(200, 20));
        this.actDNameField.addActionListener(this);
        this.actDNameField.addFocusListener(this);
        jPanel12.add(this.actDNameField, "Center");
        actDPanel.add(jPanel12, "North");
        this.jScrollPane6.setMinimumSize(new Dimension(300, 100));
        this.actDCommandArea.setFont(new Font("Tahoma", 0, 11));
        this.actDCommandArea.setRows(7);
        this.actDCommandArea.setText("command = { }");
        this.actDCommandArea.setWrapStyleWord(true);
        this.actDCommandArea.setEnabled(false);
        this.actDCommandArea.addFocusListener(this);
        this.jScrollPane6.setViewportView(this.actDCommandArea);
        actDPanel.add(this.jScrollPane6, "Center");
        this.actionsPanel.add(actDPanel);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = 1;
        centerPanel.add(this.actionsPanel, gridBagConstraints);
        this.getContentPane().add(centerPanel, "Center");
        this.lowerPanel.setLayout(new FlowLayout(2));
        this.lowerPanel.setBorder(BorderFactory.createEtchedBorder());
        this.generateButton.setMnemonic('G');
        this.generateButton.setText("Generate");
        this.generateButton.setToolTipText("Generate the event(s)");
        this.generateButton.addActionListener(this);
        this.lowerPanel.add(this.generateButton);
        this.previewButton.setMnemonic('P');
        this.previewButton.setText("Preview");
        this.previewButton.setToolTipText("Open a dialog showing what will be generated");
        this.previewButton.addActionListener(this);
        this.lowerPanel.add(this.previewButton);
        this.exitButton.setMnemonic('x');
        this.exitButton.setText("Exit");
        this.exitButton.setToolTipText("Exit the generator");
        this.exitButton.setMaximumSize(new Dimension(77, 23));
        this.exitButton.setPreferredSize(new Dimension(77, 23));
        this.exitButton.addActionListener(this);
        this.lowerPanel.add(this.exitButton);
        this.getContentPane().add(this.lowerPanel, "South");
        this.fileMenu.setMnemonic('F');
        this.fileMenu.setText("File");
        this.generateMenuItem.setAccelerator(KeyStroke.getKeyStroke(71, 2));
        this.generateMenuItem.setMnemonic('G');
        this.generateMenuItem.setText("Generate");
        this.generateMenuItem.addActionListener(this);
        this.fileMenu.add(this.generateMenuItem);
        this.loadMenuItem.setAccelerator(KeyStroke.getKeyStroke(76, 2));
        this.loadMenuItem.setMnemonic('L');
        this.loadMenuItem.setText("Load file");
        this.loadMenuItem.addActionListener(this);
        this.fileMenu.add(this.loadMenuItem);
        this.templatesMenu.setText("Templates");
        this.templatesMenu.add(this.jSeparator7);
        this.saveTemplateMenuItem.setText("Save template");
        this.saveTemplateMenuItem.addActionListener(this);
        this.templatesMenu.add(this.saveTemplateMenuItem);
        this.fileMenu.add(this.templatesMenu);
        this.fileMenu.add(this.jSeparator4);
        this.exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(88, 2));
        this.exitMenuItem.setMnemonic('x');
        this.exitMenuItem.setText("Exit");
        this.exitMenuItem.addActionListener(this);
        this.fileMenu.add(this.exitMenuItem);
        this.menuBar.add(this.fileMenu);
        this.optionsMenu.setText("Options");
        this.randomMenuItem.setAccelerator(KeyStroke.getKeyStroke(82, 2));
        this.randomMenuItem.setText("Random");
        this.randomMenuItem.addActionListener(this);
        this.optionsMenu.add(this.randomMenuItem);
        this.inclDatesMenuItem.setAccelerator(KeyStroke.getKeyStroke(68, 2));
        this.inclDatesMenuItem.setSelected(true);
        this.inclDatesMenuItem.setText("Include dates");
        this.inclDatesMenuItem.addActionListener(this);
        this.optionsMenu.add(this.inclDatesMenuItem);
        this.seqDatesMenuItem.setAccelerator(KeyStroke.getKeyStroke(83, 2));
        this.seqDatesMenuItem.setSelected(true);
        this.seqDatesMenuItem.setText("Sequential dates");
        this.seqDatesMenuItem.addActionListener(this);
        this.optionsMenu.add(this.seqDatesMenuItem);
        this.yearIncrMenuItem.setAccelerator(KeyStroke.getKeyStroke(73, 2));
        this.yearIncrMenuItem.setText("Year increment: 10");
        this.yearIncrMenuItem.addActionListener(this);
        this.optionsMenu.add(this.yearIncrMenuItem);
        this.aiOnlyMenuItem.setText("AI only");
        this.aiOnlyMenuItem.setToolTipText("Disabled as of version 1.1");
        this.aiOnlyMenuItem.setEnabled(false);
        this.aiOnlyMenuItem.addActionListener(this);
        this.optionsMenu.add(this.aiOnlyMenuItem);
        this.optionsMenu.add(this.jSeparator3);
        this.exportTextMenuItem.setText("Export text");
        this.exportTextMenuItem.addActionListener(this);
        this.optionsMenu.add(this.exportTextMenuItem);
        this.setTextColumnMenuItem.setText("Set text.csv column...");
        this.setTextColumnMenuItem.addActionListener(this);
        this.optionsMenu.add(this.setTextColumnMenuItem);
        this.optionsMenu.add(this.jSeparator8);
        this.setNameMenuItem.setText("Set user name...");
        this.setNameMenuItem.addActionListener(this);
        this.optionsMenu.add(this.setNameMenuItem);
        this.menuBar.add(this.optionsMenu);
        this.helpMenu.setMnemonic('H');
        this.helpMenu.setText("Help");
        this.aboutMenuItem.setAccelerator(KeyStroke.getKeyStroke(65, 2));
        this.aboutMenuItem.setMnemonic('A');
        this.aboutMenuItem.setText("About");
        this.aboutMenuItem.addActionListener(this);
        this.helpMenu.add(this.aboutMenuItem);
        this.menuBar.add(this.helpMenu);
        this.setJMenuBar(this.menuBar);
        this.pack();
    }
    
    public void actionPerformed(final ActionEvent evt) {
        if (evt.getSource() == this.generateMenuItem) {
            this.generateMenuItemActionPerformed(evt);
        }
        else if (evt.getSource() == this.loadMenuItem) {
            this.loadMenuItemActionPerformed(evt);
        }
        else if (evt.getSource() == this.saveTemplateMenuItem) {
            this.saveTemplateMenuItemActionPerformed(evt);
        }
        else if (evt.getSource() == this.exitMenuItem) {
            this.exitMenuItemActionPerformed(evt);
        }
        else if (evt.getSource() == this.randomMenuItem) {
            this.randomCheckChanged(evt);
        }
        else if (evt.getSource() == this.inclDatesMenuItem) {
            this.inclDatesCheckChanged(evt);
        }
        else if (evt.getSource() == this.seqDatesMenuItem) {
            this.seqDatesCheckChanged(evt);
        }
        else if (evt.getSource() == this.yearIncrMenuItem) {
            this.yearIncrCheckChanged(evt);
        }
        else if (evt.getSource() == this.aiOnlyMenuItem) {
            this.aiOnlyMenuItemActionPerformed(evt);
        }
        else if (evt.getSource() == this.exportTextMenuItem) {
            this.exportTextMenuItemActionPerformed(evt);
        }
        else if (evt.getSource() == this.setTextColumnMenuItem) {
            this.setTextColumnMenuItemActionPerformed(evt);
        }
        else if (evt.getSource() == this.setNameMenuItem) {
            this.setNameMenuItemActionPerformed(evt);
        }
        else if (evt.getSource() == this.aboutMenuItem) {
            this.aboutMenuItemActionPerformed(evt);
        }
        else if (evt.getSource() == this.randomCheckBox) {
            this.randomCheckChanged(evt);
        }
        else if (evt.getSource() == this.includeDatesCheckBox) {
            this.inclDatesCheckChanged(evt);
        }
        else if (evt.getSource() == this.seqDatesCheckBox) {
            this.seqDatesCheckChanged(evt);
        }
        else if (evt.getSource() == this.exportTextCheckBox) {
            this.exportTextMenuItemActionPerformed(evt);
        }
        else if (evt.getSource() == this.countryRadioButton) {
            this.countryProvButtonChanged(evt);
        }
        else if (evt.getSource() == this.provRadioButton) {
            this.countryProvButtonChanged(evt);
        }
        else if (evt.getSource() == this.addRowButton) {
            this.addRowButtonActionPerformed(evt);
        }
        else if (evt.getSource() == this.removeRowButton) {
            this.removeRowButtonActionPerformed(evt);
        }
        else if (evt.getSource() == this.startMonthComboBox) {
            this.monthChanged(evt);
        }
        else if (evt.getSource() == this.endMonthComboBox) {
            this.monthChanged(evt);
        }
        else if (evt.getSource() == this.numActsComboBox) {
            this.numActsComboBoxActionPerformed(evt);
        }
        else if (evt.getSource() == this.actANameField) {
            this.textFieldActionPerformed(evt);
        }
        else if (evt.getSource() == this.actBNameField) {
            this.textFieldActionPerformed(evt);
        }
        else if (evt.getSource() == this.actCNameField) {
            this.textFieldActionPerformed(evt);
        }
        else if (evt.getSource() == this.actDNameField) {
            this.textFieldActionPerformed(evt);
        }
        else if (evt.getSource() == this.generateButton) {
            this.generateMenuItemActionPerformed(evt);
        }
        else if (evt.getSource() == this.previewButton) {
            this.previewButtonActionPerformed(evt);
        }
        else if (evt.getSource() == this.exitButton) {
            this.exitMenuItemActionPerformed(evt);
        }
        else if (evt.getSource() == this.includeCountryCheckBox) {
            this.includeCountryCheckBoxActionPerformed(evt);
        }
    }
    
    public void focusGained(final FocusEvent evt) {
    }
    
    public void focusLost(final FocusEvent evt) {
        if (evt.getSource() == this.headCommentTextArea) {
            this.textAreaFocusLost(evt);
        }
        else if (evt.getSource() == this.triggerArea) {
            this.textAreaFocusLost(evt);
        }
        else if (evt.getSource() == this.eventNameField) {
            this.textFieldFocusLost(evt);
        }
        else if (evt.getSource() == this.descArea) {
            this.textAreaFocusLost(evt);
        }
        else if (evt.getSource() == this.actANameField) {
            this.textFieldFocusLost(evt);
        }
        else if (evt.getSource() == this.actACommandArea) {
            this.textAreaFocusLost(evt);
        }
        else if (evt.getSource() == this.actBNameField) {
            this.textFieldFocusLost(evt);
        }
        else if (evt.getSource() == this.actBCommandArea) {
            this.textAreaFocusLost(evt);
        }
        else if (evt.getSource() == this.actCNameField) {
            this.textFieldFocusLost(evt);
        }
        else if (evt.getSource() == this.actCCommandArea) {
            this.textAreaFocusLost(evt);
        }
        else if (evt.getSource() == this.actDNameField) {
            this.textFieldFocusLost(evt);
        }
        else if (evt.getSource() == this.actDCommandArea) {
            this.textAreaFocusLost(evt);
        }
    }
    
    public void mouseClicked(final MouseEvent evt) {
        if (evt.getSource() == this.table) {
            this.tableMouseClicked(evt);
        }
    }
    
    public void mouseEntered(final MouseEvent evt) {
    }
    
    public void mouseExited(final MouseEvent evt) {
    }
    
    public void mousePressed(final MouseEvent evt) {
    }
    
    public void mouseReleased(final MouseEvent evt) {
    }
    
    public void windowActivated(final WindowEvent evt) {
    }
    
    public void windowClosed(final WindowEvent evt) {
    }
    
    public void windowClosing(final WindowEvent evt) {
        if (evt.getSource() == this) {
            this.formWindowClosing(evt);
        }
    }
    
    public void windowDeactivated(final WindowEvent evt) {
    }
    
    public void windowDeiconified(final WindowEvent evt) {
    }
    
    public void windowIconified(final WindowEvent evt) {
    }
    
    public void windowOpened(final WindowEvent evt) {
    }
    
    public void stateChanged(final ChangeEvent evt) {
        if (evt.getSource() == this.yearIncrSpinner) {
            this.spinnerChanged(evt);
        }
        else if (evt.getSource() == this.incrIDSpinner) {
            this.spinnerChanged(evt);
        }
        else if (evt.getSource() == this.startDaySpinner) {
            this.spinnerChanged(evt);
        }
        else if (evt.getSource() == this.startDateSpinner) {
            this.spinnerChanged(evt);
        }
        else if (evt.getSource() == this.offsetSpinner) {
            this.spinnerChanged(evt);
        }
        else if (evt.getSource() == this.endDaySpinner) {
            this.spinnerChanged(evt);
        }
        else if (evt.getSource() == this.endDateSpinner) {
            this.spinnerChanged(evt);
        }
    }
    
    private void includeCountryCheckBoxActionPerformed(final ActionEvent evt) {
        generator.setIncludeCountry(this.includeCountryCheckBox.isSelected());
    }
    
    private void setTextColumnMenuItemActionPerformed(final ActionEvent evt) {
        final Integer selection = (Integer)JOptionPane.showInputDialog(this,
                "Choose a column number:",
                "Input",
                JOptionPane.PLAIN_MESSAGE,
                icon,
                EventGen.allowedColumns,
                generator.getTextColumn());
        if (selection != null) {
            generator.setTextColumn(selection);
        }
    }
    
    private void exportTextMenuItemActionPerformed(final ActionEvent evt) {
        this.setExportText(((AbstractButton)evt.getSource()).isSelected());
    }
    
    private void setExportText(final boolean export) {
        generator.setExportText(export);
        exportTextCheckBox.setSelected(export);
        exportTextMenuItem.setSelected(export);
    }
    
    private void saveTemplateMenuItemActionPerformed(final ActionEvent evt) {
        final String name = JOptionPane.showInputDialog(this, "Enter a name for the template:");
        if (name == null) {
            return;
        }
        String desc = JOptionPane.showInputDialog(this, "Enter a short description for the template:");
        if (desc == null) {
            desc = "";
        }
        final JFileChooser chooser = new JFileChooser(new File("Templates/"));
        chooser.setSelectedFile(new File(name + ".txt"));
        if (chooser.showSaveDialog(this) == 0) {
            final File f = chooser.getSelectedFile();
            if (f.exists()) {
                generator.generateTemplate(f.getPath(), JOptionPane.showConfirmDialog(this, "Overwrite " + f.getPath() + "?") == 0);
            }
            else {
                generator.generateTemplate(f.getPath(), true);
            }
            templateManager.registerTemplate(name, desc, f.getPath());
            final StringBuilder append = new StringBuilder().append("Close and reopen ").append(EventGen.PROG_NAME).append(" to get the new template into the menu.");
            JOptionPane.showMessageDialog(this, append.toString());
        }
    }
    
    private void setNameMenuItemActionPerformed(final ActionEvent evt) {
        final String name = Config.getString(Config.KEY_USER_NAME);
        final String check = JOptionPane.showInputDialog(this, "Enter your user name: ", name);
        if (check == null) {
            return;
        }
        Config.setString(Config.KEY_USER_NAME, check);
    }
    
    private void spinnerChanged(final ChangeEvent evt) {
        final JSpinner spinner = (JSpinner)evt.getSource();
        final int value = (int)spinner.getValue();
        if (spinner == incrIDSpinner) {
            generator.setIdIncrement((int)incrIDSpinner.getValue());
        }
        else if (spinner == startDateSpinner) {
            generator.setStartYear(value);
        }
        else if (spinner == startDaySpinner) {
            generator.setStartDay(value);
        }
        else if (spinner == offsetSpinner) {
            generator.setOffset(value);
        }
        else if (spinner == endDateSpinner) {
            generator.setEndYear(value);
        }
        else if (spinner == endDaySpinner) {
            generator.setEndDay(value);
        }
        else if (spinner == yearIncrSpinner) {
            setYearIncrement(value);
        }
        else {
            System.err.println(value);
        }
    }
    
    private void textFieldFocusLost(final FocusEvent evt) {
        this.doTextFieldUpdate((JTextField)evt.getSource());
    }
    
    private void textAreaFocusLost(final FocusEvent evt) {
        final JTextArea textArea = (JTextArea)evt.getSource();
        final String text = textArea.getText();
        if (textArea == actACommandArea) {
            generator.setActA(text);
        }
        else if (textArea == actBCommandArea) {
            generator.setActB(text);
        }
        else if (textArea == actCCommandArea) {
            generator.setActC(text);
        }
        else if (textArea == actDCommandArea) {
            generator.setActD(text);
        }
        else if (textArea == descArea) {
            generator.setEventDesc(text);
        }
        else if (textArea == headCommentTextArea) {
            generator.setHeadComment(text);
        }
        else if (textArea == triggerArea) {
            generator.setTrigger(text);
        }
        else {
            System.err.println(text);
        }
    }
    
    private void textFieldActionPerformed(final ActionEvent evt) {
        doTextFieldUpdate((JTextField)evt.getSource());
    }
    
    private void doTextFieldUpdate(final JTextField textField) {
        final String text = textField.getText();
        if (textField == actANameField) {
            generator.setActAName(text);
        }
        else if (textField == actBNameField) {
            generator.setActBName(text);
        }
        else if (textField == actCNameField) {
            generator.setActCName(text);
        }
        else if (textField == actDNameField) {
            generator.setActDName(text);
        }
        else if (textField == eventNameField) {
            generator.setEventName(text);
        }
        else {
            System.err.println(text);
        }
    }
    
    private void aiOnlyMenuItemActionPerformed(final ActionEvent evt) {
        generator.setAiOnly(this.aiOnlyMenuItem.isSelected());
    }
    
    private void yearIncrCheckChanged(final ActionEvent evt) {
        final String check = JOptionPane.showInputDialog(this, "Enter the number of years to increase the dates by on each event:");
        if (check == "") {
            return;
        }
        try {
            this.setYearIncrement(Integer.parseInt(check));
        }
        catch (RuntimeException ex) {}
    }
    
    private void setYearIncrement(final int incr) {
        if (incr > 100 || incr < -100) {
            return;
        }
        yearIncrMenuItem.setText("Increase dates by: " + incr);
        generator.setYearIncrement(incr);
        yearIncrSpinner.setValue(incr);
    }
    
    private void seqDatesCheckChanged(final ActionEvent evt) {
        setSeqDates(((AbstractButton)evt.getSource()).isSelected());
    }
    
    private void setSeqDates(final boolean seqDates) {
        seqDatesMenuItem.setSelected(seqDates);
        seqDatesCheckBox.setSelected(seqDates);
        yearIncrMenuItem.setEnabled(seqDates);
        jLabel1.setEnabled(seqDates);
        yearIncrSpinner.setEnabled(seqDates);
        generator.setSequentialDates(seqDates);
    }
    
    private void inclDatesCheckChanged(final ActionEvent evt) {
        setInclDates(((AbstractButton)evt.getSource()).isSelected());
    }
    
    private void setInclDates(final boolean inclDates) {
        inclDatesMenuItem.setSelected(inclDates);
        includeDatesCheckBox.setSelected(inclDates);
        generator.setIncludeDates(inclDates);
        seqDatesMenuItem.setEnabled(inclDates);
        seqDatesCheckBox.setEnabled(inclDates);
        if (inclDates && seqDatesMenuItem.isSelected() && seqDatesCheckBox.isSelected()) {
            yearIncrMenuItem.setEnabled(true);
            jLabel1.setEnabled(true);
            yearIncrSpinner.setEnabled(true);
        }
        else {
            yearIncrMenuItem.setEnabled(false);
            jLabel1.setEnabled(false);
            yearIncrSpinner.setEnabled(false);
        }
    }
    
    private void randomCheckChanged(final ActionEvent evt) {
        setRandom(((AbstractButton)evt.getSource()).isSelected());
    }
    
    private void setRandom(final boolean random) {
        jLabel4.setEnabled(!random);
        offsetSpinner.setEnabled(!random);
        generator.setRandom(random);
        randomCheckBox.setSelected(random);
        randomMenuItem.setSelected(random);
    }
    
    private void tableMouseClicked(final MouseEvent evt) {
        if (table.getSelectedRow() == -1 || table.isEditing() || table.getModel().getRowCount() <= 1) {
            removeRowButton.setEnabled(false);
        }
        else {
            removeRowButton.setEnabled(true);
        }
    }
    
    private void removeRowButtonActionPerformed(final ActionEvent evt) {
        ((CountryIDTable)table).removeRow(table.getSelectedRow());
        tableMouseClicked(null);
    }
    
    private void addRowButtonActionPerformed(final ActionEvent evt) {
        ((CountryIDTable)table).addRow();
    }
    
    private void previewButtonActionPerformed(final ActionEvent evt) {
        try {
            final GenericObject events = generator.createEvents(((CountryIDTable)table).getCountries(), (int)numOfEventsSpinner.getValue());
            if (events.isEmpty()) {
                return;
            }
            PreviewDialog.showPreview(this, events.toString());
        }
        catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error creating events!", "Error", 0);
            ex.printStackTrace();
        }
    }
    
    private void formWindowClosing(final WindowEvent evt) {
        exitMenuItemActionPerformed(null);
    }
    
    private void monthChanged(final ActionEvent evt) {
        if (evt.getSource() == startMonthComboBox) {
            final Month month = (Month)startMonthComboBox.getSelectedItem();
            final int oldDays = (int)startDaySpinner.getValue();
            final int value = Math.min(Math.max(oldDays, 0), month.getNumDays());
            final int min = Math.min(0, month.getNumDays());
            final int max = month.getNumDays();
            startDaySpinner.setModel(new SpinnerNumberModel(value, min, max, 1));
            generator.setStartMonth(month);
            spinnerChanged(new ChangeEvent(startDaySpinner));
        }
        else if (evt.getSource() == endMonthComboBox) {
            final Month month = (Month)endMonthComboBox.getSelectedItem();
            final int oldDays = (int)endDaySpinner.getValue();
            final int value = Math.min(Math.max(oldDays, 0), month.getNumDays());
            final int min = Math.min(0, month.getNumDays());
            final int max = month.getNumDays();
            endDaySpinner.setModel(new SpinnerNumberModel(value, min, max, 1));
            generator.setEndMonth(month);
            spinnerChanged(new ChangeEvent(endDaySpinner));
        }
    }
    
    private void loadMenuItemActionPerformed(final ActionEvent evt) {
        loadEvents();
    }
    
    private void countryProvButtonChanged(final ActionEvent evt) {
        if (countryRadioButton.isSelected()) {
            ((CountryIDTable)table).setMode(Mode.COUNTRY);
            generator.setByCountry(true);
        }
        else if (provRadioButton.isSelected()) {
            ((CountryIDTable)this.table).setMode(Mode.PROVINCE);
            generator.setByCountry(false);
        }
    }
    
    private void numActsComboBoxActionPerformed(final ActionEvent evt) {
        final Component[] actBComponents = { jLabel14, actBNameField, jScrollPane4, actBCommandArea };
        final Component[] actCComponents = { jLabel15, actCNameField, jScrollPane5, actCCommandArea };
        final Component[] actDComponents = { jLabel16, actDNameField, jScrollPane6, actDCommandArea };
        switch (Integer.parseInt((String)numActsComboBox.getSelectedItem())) {
            case 1: {
                setEnabled(false, actBComponents);
                setEnabled(false, actCComponents);
                setEnabled(false, actDComponents);
                generator.setNumOfActs(1);
                break;
            }
            case 2: {
                setEnabled(true, actBComponents);
                setEnabled(false, actCComponents);
                setEnabled(false, actDComponents);
                generator.setNumOfActs(2);
                break;
            }
            case 3: {
                setEnabled(true, actBComponents);
                setEnabled(true, actCComponents);
                setEnabled(false, actDComponents);
                generator.setNumOfActs(3);
                break;
            }
            case 4: {
                setEnabled(true, actBComponents);
                setEnabled(true, actCComponents);
                setEnabled(true, actDComponents);
                generator.setNumOfActs(4);
                break;
            }
            default: {
                System.err.println("Error in jComboBox2ActionPerformed!");
                break;
            }
        }
    }
    
    private void setEnabled(final boolean enabled, final Component... components) {
        for (Component c : components) {
            c.setEnabled(enabled);
        }
    }
    
    private void aboutMenuItemActionPerformed(final ActionEvent evt) {
        JOptionPane.showMessageDialog(this,
                EventGen.PROG_NAME + " version " + EventGen.PROG_VERSION + "\nBy Michael Myers",
                "About",
                JOptionPane.INFORMATION_MESSAGE,
                EventGenUI.icon);
    }
    
    private void generateMenuItemActionPerformed(final ActionEvent evt) {
        final JFileChooser chooser = new JFileChooser(lastSaveFile);
        chooser.setFileFilter(new TextFileFilter());
        if (chooser.showSaveDialog(this) == 0) {
            lastSaveFile = chooser.getSelectedFile().getAbsolutePath();
            final List<CountryIDTable.NameTagID> name2ID = ((CountryIDTable)table).getCountries();
            final int numEvents = (int)numOfEventsSpinner.getValue();
            if (new File(lastSaveFile).canRead()) {
                final int check = JOptionPane.showConfirmDialog(this, "Overwrite selected file?", "Question", 1, 3, EventGenUI.icon);
                if (check == 2) {
                    return;
                }
                if (check == 0) {
                    generator.generate(lastSaveFile, true, name2ID, numEvents);
                }
                else {
                    generator.generate(lastSaveFile, false, name2ID, numEvents);
                }
            }
            else {
                generator.generate(lastSaveFile, true, name2ID, numEvents);
            }
        }
    }
    
    private void exitMenuItemActionPerformed(final ActionEvent evt) {
        Config.setString(Config.KEY_LAST_LOAD_FILE, lastLoadFile, true);
        Config.setString(Config.KEY_LAST_SAVE_FILE, lastSaveFile, true);
        //InlineComment.spaceBeforeComment = true;
        Config.saveConfig();
        templateManager.saveTemplates();
        System.exit(0);
    }
    
    private void loadEvents() {
        final JFileChooser chooser = new JFileChooser(lastLoadFile);
        chooser.setFileFilter(new TextFileFilter());
        chooser.setSelectedFile(new File(lastLoadFile));
        if (chooser.showOpenDialog(null) != 0) {
            return;
        }
        lastLoadFile = chooser.getSelectedFile().getAbsolutePath();
        EventDatabase.clear();
        EventDatabase.loadEvents(lastLoadFile, null);
        if (EventDatabase.numEvents() == 0) {
            JOptionPane.showMessageDialog(this, "No events found in the selected file!");
            return;
        }
        Integer[] idArray = new Integer[EventDatabase.numEvents()];
        idArray = (Integer[])EventDatabase.byId.keySet().toArray(idArray);
        Arrays.sort(idArray);
        final String[] idNums = new String[idArray.length];
        for (int i = 0; i < idArray.length; ++i) {
            final String s = Integer.toString(idArray[i]);
            idNums[i] = s;
        }
        if (idNums != null) {
            final Event evt = LoadDialog.showDialog(this, idNums);
            if (evt != null) {
                load(evt);
            }
        }
        else {
            System.err.println("Array failed to initialize");
        }
    }
    
    private void load(final Event event) {
        if (event.getName().equals("EVENTNAME" + event.getId())) {
            eventNameField.setText(event.go.getCommentAfterVar("name"));
            generator.setEventName(event.go.getCommentAfterVar("name"));
            String desc = event.go.getCommentAfterVar("desc");
            if (desc.startsWith("- ")) {
                desc = desc.substring(2);
            }
            descArea.setText(desc);
            generator.setEventDesc(desc);
            setExportText(true);
        }
        else {
            eventNameField.setText(event.getName());
            generator.setEventName(event.getName());
            descArea.setText(event.getDesc());
            generator.setEventDesc(event.getDesc());
        }
        descArea.setCaretPosition(0);
        setCountryAndID(event);
        triggerArea.setText("");
        triggerArea.setCaretPosition(0);
        final GenericObject trig = event.go.getChild("trigger");
        if (trig != null) {
            if (event.isRandom()) {
                String tmp = trig.getString("year");
                if (!tmp.equals("")) {
                    inclDatesMenuItem.setSelected(true);
                    setInclDates(true);
                    startDateSpinner.setValue(Integer.parseInt(tmp));
                    trig.removeVariable("year");
                }
                else {
                    inclDatesMenuItem.setSelected(false);
                    setInclDates(false);
                    startDateSpinner.setValue(-1);
                }
                endDateSpinner.setValue(-1);
                for (final GenericObject not : trig.getChildren("not")) {
                    tmp = not.getString("year");
                    if (!tmp.equals("")) {
                        inclDatesMenuItem.setSelected(true);
                        endDateSpinner.setValue(Integer.parseInt(tmp));
                        not.removeVariable("year");
                        setInclDates(true);
                        if (not.size() == 0) {
                            trig.removeChild(not);
                            break;
                        }
                        break;
                    }
                }
            }
            final String trigger = trig.childrenToString()
                    .replaceAll(EventGen.DEFAULT_COUNTRY_TAG, "\\${tag}")
                    .replaceAll(Integer.toString(EventGen.DEFAULT_PROVINCE_ID), "\\${provid}");
            triggerArea.setText(trigger);
            generator.setTrigger(trigger);
        }
        if (event.isRandom()) {
            setRandom(true);
        }
        else {
            setRandom(false);
            final GenericObject date = event.go.getChild("date");
            if (date != null) {
                startDateSpinner.setValue(date.getInt("year"));
                final String startMonth = date.getString("month");
                if (startMonth != null) {
                    startMonthComboBox.setSelectedItem(startMonth);
                    startDaySpinner.setValue(date.getInt("day"));
                }
                else {
                    startMonthComboBox.setSelectedItem("none");
                }
                final int offset = event.go.getInt("offset");
                offsetSpinner.setValue(offset);
                final GenericObject deathdate = event.go.getChild("deathdate");
                if (deathdate != null) {
                    endDateSpinner.setValue(deathdate.getInt("year"));
                    final String endMonth = deathdate.getString("month");
                    if (endMonth != null) {
                        endMonthComboBox.setSelectedItem(endMonth);
                        endDaySpinner.setValue(deathdate.getInt("day"));
                    }
                    else {
                        endMonthComboBox.setSelectedItem("none");
                    }
                }
                else {
                    endDateSpinner.setValue(-1);
                }
            }
            else {
                inclDatesMenuItem.setSelected(false);
                setInclDates(false);
            }
        }
        generator.setStartDay((int)startDaySpinner.getValue());
        generator.setStartMonth((Month)startMonthComboBox.getSelectedItem());
        generator.setStartYear((int)startDateSpinner.getValue());
        generator.setOffset((int)offsetSpinner.getValue());
        generator.setEndDay((int)endDaySpinner.getValue());
        generator.setEndMonth((Month)endMonthComboBox.getSelectedItem());
        generator.setEndYear((int)endDateSpinner.getValue());
        getActionA(event);
        getActionB(event);
        getActionC(event);
        getActionD(event);
        headCommentTextArea.setText(event.go.getHeadComment());
        if (!event.go.values.get(0).getInlineComment().equals("")) {
            headCommentTextArea.append((headCommentTextArea.getText().equals("") ? "" : "\n") + event.go.values.get(0).getInlineComment());
        }
        generator.setHeadComment(headCommentTextArea.getText());
    }
    
    private void getActionA(final Event event) {
        final GenericObject actA = event.go.getChild("action_a");
        if (generator.isExportText()) {
            actANameField.setText(actA.getCommentAfterVar("name"));
        }
        else {
            actANameField.setText(actA.getString("name"));
        }
        actACommandArea.setText("");
        for (final GenericObject child : actA.children) {
            final String command = child.toString()
                    .replaceAll(EventGen.DEFAULT_COUNTRY_TAG, "\\${tag}")
                    .replaceAll(Integer.toString(EventGen.DEFAULT_PROVINCE_ID), "\\${provid}");
            actACommandArea.append(command);
            actACommandArea.append("\n");
        }
        actACommandArea.setCaretPosition(0);
        generator.setActAName(actANameField.getText());
        generator.setActA(actACommandArea.getText());
    }
    
    private void getActionB(final Event event) {
        final GenericObject actB = event.go.getChild("action_b");
        if (actB != null) {
            numActsComboBox.setSelectedItem("2");
            numActsComboBoxActionPerformed(null);
            if (generator.isExportText()) {
                actBNameField.setText(actB.getCommentAfterVar("name"));
            }
            else {
                actBNameField.setText(actB.getString("name"));
            }
            actBCommandArea.setText("");
            for (final GenericObject child : actB.children) {
                final String command = child.toString()
                        .replaceAll(EventGen.DEFAULT_COUNTRY_TAG, "\\${tag}")
                        .replaceAll(Integer.toString(EventGen.DEFAULT_PROVINCE_ID), "\\${provid}");
                actBCommandArea.append(command);
                actBCommandArea.append("\n");
            }
            actBCommandArea.setCaretPosition(0);
        }
        else {
            numActsComboBox.setSelectedItem("1");
            numActsComboBoxActionPerformed(null);
            actBNameField.setText("action b");
            actBCommandArea.setText("");
        }
        generator.setActBName(actBNameField.getText());
        generator.setActB(actBCommandArea.getText());
    }
    
    private void getActionC(final Event event) {
        final GenericObject actC = event.go.getChild("action_c");
        if (actC != null) {
            numActsComboBox.setSelectedItem("3");
            numActsComboBoxActionPerformed(null);
            if (generator.isExportText()) {
                actCNameField.setText(actC.getCommentAfterVar("name"));
            }
            else {
                actCNameField.setText(actC.getString("name"));
            }
            actCCommandArea.setText("");
            for (final GenericObject child : actC.children) {
                final String command = child.toString()
                        .replaceAll(EventGen.DEFAULT_COUNTRY_TAG, "\\${tag}")
                        .replaceAll(Integer.toString(EventGen.DEFAULT_PROVINCE_ID), "\\${provid}");
                actCCommandArea.append(command);
                actCCommandArea.append("\n");
            }
            actCCommandArea.setCaretPosition(0);
        }
        else {
            actCNameField.setText("action c");
            actCCommandArea.setText("");
        }
        generator.setActCName(actCNameField.getText());
        generator.setActC(actCCommandArea.getText());
    }
    
    private void getActionD(final Event event) {
        final GenericObject actD = event.go.getChild("action_d");
        if (actD != null) {
            numActsComboBox.setSelectedItem("4");
            numActsComboBoxActionPerformed(null);
            if (generator.isExportText()) {
                actDNameField.setText(actD.getCommentAfterVar("name"));
            }
            else {
                actDNameField.setText(actD.getString("name"));
            }
            actDCommandArea.setText("");
            for (final GenericObject child : actD.children) {
                final String command = child.toString()
                        .replaceAll(EventGen.DEFAULT_COUNTRY_TAG, "\\${tag}")
                        .replaceAll(Integer.toString(EventGen.DEFAULT_PROVINCE_ID), "\\${provid}");
                actDCommandArea.append(command);
                actDCommandArea.append("\n");
            }
            actDCommandArea.setCaretPosition(0);
        }
        else {
            actDNameField.setText("action d");
            actDCommandArea.setText("");
        }
        generator.setActDName(actDNameField.getText());
        generator.setActD(actDCommandArea.getText());
    }
    
    private void setCountryAndID(final Event event) {
        final String tag = event.getCountry();
        if (!tag.equals("")) {
            final String country = Mode.COUNTRY.getTagToCountry().get(tag);
            countryRadioButton.doClick();
            ((CountryIDTable)table).clear();
            ((CountryIDTable)table).setRow(0, country, event.getId());
        }
        else {
            final int provID = event.getProvince();
            if (provID != -1) {
                final String province = Mode.PROVINCE.getTagToCountry().get(Integer.toString(provID));
                provRadioButton.doClick();
                ((CountryIDTable)table).clear();
                ((CountryIDTable)table).setRow(0, province, event.getId());
            }
            else {
                countryRadioButton.doClick();
                ((CountryIDTable)table).clear();
                ((CountryIDTable)table).setRow(0, Mode.COUNTRY.getDefaultName(), event.getId());
            }
        }
    }
    
    public static void main(final String[] args) {
        new EventGenUI().setVisible(true);
    }
    
    private class TemplateMenuItemListener implements ActionListener
    {
        private String filename;
        
        public TemplateMenuItemListener(final String filename) {
            this.filename = filename;
        }
        
        public void actionPerformed(final ActionEvent e) {
            final GenericObject root = EUGFileIO.load(this.filename, ParserSettings.getQuietSettings());
            if (root.getChildren("event").size() == 0) {
                JOptionPane.showMessageDialog(EventGenUI.this, "No events found in template file " + filename + "!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            load(new Event(root.getChild("event"), null));
        }
    }
}
