// 
// Decompiled by Procyon v0.6-prerelease
// 

package eventgenerator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.LayoutManager;
import java.awt.BorderLayout;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import java.awt.Component;
import java.awt.Frame;
import javax.swing.JButton;
import javax.swing.JDialog;

public class PreviewDialog extends JDialog
{
    private String text;
    private JButton closeButton;
    
    private PreviewDialog(final Frame parent, final String text) {
        super(parent, true);
        this.text = text;
        this.initComponents();
        this.setLocationRelativeTo(parent);
    }
    
    private void initComponents() {
        final JPanel upperPanel = new JPanel();
        final JScrollPane jScrollPane1 = new JScrollPane();
        final JTextArea previewTextArea = new JTextArea();
        final JPanel buttonPanel = new JPanel();
        this.closeButton = new JButton();
        final FormListener formListener = new FormListener();
        this.setDefaultCloseOperation(2);
        this.setTitle("Preview");
        upperPanel.setLayout(new BorderLayout());
        previewTextArea.setColumns(90);
        previewTextArea.setEditable(false);
        previewTextArea.setRows(30);
        previewTextArea.setText(this.text);
        previewTextArea.setCaretPosition(0);
        jScrollPane1.setViewportView(previewTextArea);
        upperPanel.add(jScrollPane1, "Center");
        this.getContentPane().add(upperPanel, "Center");
        this.closeButton.setText("Close");
        this.closeButton.addActionListener(formListener);
        buttonPanel.add(this.closeButton);
        this.getContentPane().add(buttonPanel, "South");
        this.pack();
    }
    
    private void closeButtonActionPerformed(final ActionEvent evt) {
        this.setVisible(false);
        this.dispose();
    }
    
    public static void showPreview(final Frame parent, final String text) {
        new PreviewDialog(parent, text).setVisible(true);
    }
    
    private class FormListener implements ActionListener
    {
        FormListener() {
        }
        
        public void actionPerformed(final ActionEvent evt) {
            if (evt.getSource() == PreviewDialog.this.closeButton) {
                PreviewDialog.this.closeButtonActionPerformed(evt);
            }
        }
    }
}
