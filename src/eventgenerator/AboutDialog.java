// 
// Decompiled by Procyon v0.6-prerelease
// 

package eventgenerator;

import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import java.awt.Frame;
import javax.swing.JButton;
import java.awt.event.WindowListener;
import java.awt.event.ActionListener;
import javax.swing.JDialog;

public class AboutDialog extends JDialog implements ActionListener, WindowListener
{
    private static final String NEWLINE = System.getProperty("line.separator");
    private static final String[][] props = new String[][] {
        { "Version", EventGen.PROG_VERSION },
        { "Author", "Michael Myers" },
        { "Java version", System.getProperty("java.version") },
        { "OS name", System.getProperty("os.name") },
        { "OS version", System.getProperty("os.version") }
    };
    private static final String[] columns = new String[] { "Property", "Value" };
    private JButton okButton;
    
    public AboutDialog(final Frame parent, final boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
    }
    
    private void initComponents() {
        final JPanel buttonPanel = new JPanel();
        this.okButton = new JButton();
        final JPanel iconPanel = new JPanel();
        final JPanel jPanel2 = new JPanel();
        final JPanel jPanel3 = new JPanel();
        final JPanel jPanel4 = new JPanel();
        setTitle("About");
        setResizable(false);
        addWindowListener(this);
        okButton.setText("OK");
        okButton.addActionListener(this);
        buttonPanel.add(okButton);
        add(buttonPanel, "South");
        iconPanel.add(new JLabel(new ImageIcon(getClass().getResource("/eventgenerator/eg.gif"))));
        add(iconPanel, "West");
        jPanel2.setPreferredSize(new Dimension(33, 57));
        add(jPanel2, "East");
        add(jPanel3, "North");
        final JLabel headerLabel = new JLabel(EventGen.PROG_NAME);
        headerLabel.setFont(headerLabel.getFont().deriveFont(16.0f));
        jPanel4.add(headerLabel);
        final JScrollPane jScrollPane1 = new JScrollPane(new JTable(AboutDialog.props, AboutDialog.columns));
        jPanel4.add(jScrollPane1);
        add(jPanel4, "Center");
        pack();
    }
    
    public void actionPerformed(final ActionEvent evt) {
        if (evt.getSource() == okButton) {
            doClose();
        }
    }
    
    public void windowActivated(final WindowEvent evt) {
    }
    
    public void windowClosed(final WindowEvent evt) {
    }
    
    public void windowClosing(final WindowEvent evt) {
        if (evt.getSource() == this) {
            doClose();
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
    
    private void doClose() {
        setVisible(false);
        dispose();
    }
    
    private String addText() {
        String message = "";
        message = message + "Version:\t\t" + EventGen.PROG_VERSION + AboutDialog.NEWLINE;
        message += "By:\t\tMichael Myers";
        return message;
    }
    
    private String addText2() {
        String message = "";
        message = message + "Java version:\t\t" + System.getProperty("java.version") + AboutDialog.NEWLINE;
        message = message + "OS:\t\t" + System.getProperty("os.name") + " v. " + System.getProperty("os.version");
        return message;
    }
}
