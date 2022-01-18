// 
// Decompiled by Procyon v0.6-prerelease
// 

package eventgenerator;

import eug.specific.eu2.EventDatabase;
import eug.specific.eu2.Event;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import java.awt.Component;
import java.util.Collection;
import java.util.Arrays;
import java.awt.Frame;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.util.Vector;
import javax.swing.event.ListSelectionListener;
import java.awt.event.WindowListener;
import java.awt.event.ActionListener;
import javax.swing.JDialog;

public class LoadDialog extends JDialog implements ActionListener, WindowListener, ListSelectionListener
{
    private static final int RET_CANCEL = 0;
    private static final int RET_OK = 1;
    private static int returnStatus = RET_CANCEL;
    private Vector<String> events;
    private String selectedId;
    private JButton cancelButton;
    private JTextArea eventTextArea;
    private JList idList;
    private JButton okButton;
    
    private LoadDialog(final Frame parent, final boolean modal, final String[] events) {
        super(parent, modal);
        this.selectedId = null;
        this.events = new Vector<String>(Arrays.asList(events));
        this.initComponents();
        this.setLocationRelativeTo(parent);
        this.idListValueChanged(null);
    }
    
    private void initComponents() {
        final JPanel buttonPanel = new JPanel();
        this.okButton = new JButton();
        this.cancelButton = new JButton();
        final JPanel leftPanel = new JPanel();
        final JLabel jLabel1 = new JLabel();
        final JScrollPane jScrollPane1 = new JScrollPane();
        this.idList = new JList(this.events);
        final JPanel rightPanel = new JPanel();
        final JScrollPane jScrollPane2 = new JScrollPane();
        this.eventTextArea = new JTextArea();
        final JLabel jLabel2 = new JLabel();
        this.getContentPane().setLayout(new BorderLayout(5, 0));
        this.setTitle("Load");
        this.addWindowListener(this);
        buttonPanel.setLayout(new FlowLayout(1, 100, 5));
        this.okButton.setText("OK");
        this.okButton.addActionListener(this);
        buttonPanel.add(this.okButton);
        this.cancelButton.setText("Cancel");
        this.cancelButton.addActionListener(this);
        buttonPanel.add(this.cancelButton);
        this.getContentPane().add(buttonPanel, "South");
        leftPanel.setLayout(new BorderLayout());
        jLabel1.setHorizontalAlignment(0);
        jLabel1.setText("Choose an event to load:");
        leftPanel.add(jLabel1, "North");
        this.idList.setSelectionMode(0);
        this.idList.setToolTipText("Select an event to see a preview");
        this.idList.setSelectedIndex(0);
        this.idList.addListSelectionListener(this);
        jScrollPane1.setViewportView(this.idList);
        leftPanel.add(jScrollPane1, "Center");
        this.getContentPane().add(leftPanel, "West");
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setMinimumSize(new Dimension(420, 370));
        this.eventTextArea.setColumns(90);
        this.eventTextArea.setEditable(false);
        this.eventTextArea.setFont(new Font("Tahoma", 0, 11));
        this.eventTextArea.setLineWrap(true);
        this.eventTextArea.setRows(30);
        this.eventTextArea.setWrapStyleWord(true);
        jScrollPane2.setViewportView(this.eventTextArea);
        rightPanel.add(jScrollPane2, "Center");
        jLabel2.setText("Preview:");
        rightPanel.add(jLabel2, "North");
        this.getContentPane().add(rightPanel, "Center");
        this.pack();
    }
    
    public void actionPerformed(final ActionEvent evt) {
        if (evt.getSource() == this.okButton) {
            this.okButtonActionPerformed(evt);
        }
        else if (evt.getSource() == this.cancelButton) {
            this.cancelButtonActionPerformed(evt);
        }
    }
    
    public void windowActivated(final WindowEvent evt) {
    }
    
    public void windowClosed(final WindowEvent evt) {
    }
    
    public void windowClosing(final WindowEvent evt) {
        if (evt.getSource() == this) {
            this.closeDialog(evt);
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
    
    public void valueChanged(final ListSelectionEvent evt) {
        if (evt.getSource() == this.idList) {
            this.idListValueChanged(evt);
        }
    }
    
    private void idListValueChanged(final ListSelectionEvent evt) {
        final String oldId = this.selectedId;
        this.selectedId = (String) this.idList.getSelectedValue();
        if (!this.selectedId.equals(oldId)) {
            this.eventTextArea.setText(EventDatabase.byId.get(Integer.parseInt(this.selectedId)).toString());
            this.eventTextArea.setCaretPosition(0);
        }
    }
    
    private void okButtonActionPerformed(final ActionEvent evt) {
        this.selectedId = (String) this.idList.getSelectedValue();
        this.doClose(RET_OK);
    }
    
    private void cancelButtonActionPerformed(final ActionEvent evt) {
        this.doClose(RET_CANCEL);
    }
    
    private void closeDialog(final WindowEvent evt) {
        this.doClose(RET_CANCEL);
    }
    
    private void doClose(final int retStatus) {
        LoadDialog.returnStatus = retStatus;
        this.setVisible(false);
        this.dispose();
    }
    
    public static Event showDialog(final Frame parent, final String[] events) {
        if (events.length == 0) {
            return null;
        }
        final LoadDialog ld = new LoadDialog(parent, true, events);
        ld.setVisible(true);
        if (LoadDialog.returnStatus == RET_OK) {
            return EventDatabase.byId.get(Integer.parseInt(ld.selectedId));
        }
        return null;
    }
}
