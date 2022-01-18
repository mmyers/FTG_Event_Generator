// 
// Decompiled by Procyon v0.6-prerelease
// 

package eventgenerator;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.Font;
import javax.swing.SpinnerNumberModel;
import javax.swing.JSpinner;
import java.awt.Component;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Collections;
import javax.swing.JComboBox;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import javax.swing.AbstractCellEditor;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import javax.swing.table.TableCellEditor;
import java.util.Vector;
import java.util.Arrays;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTable;

public class CountryIDTable extends JTable {
    private int lastID;
    private static final int MIN_ID = 1;
    private static final int MAX_ID = Integer.MAX_VALUE;
    private Mode mode;
    private static int clickCountToStart = 2;
    
    public CountryIDTable() {
        this(Mode.COUNTRY);
    }
    
    public CountryIDTable(final Mode mode) {
        super(new DefaultTableModel(new String[] { "Country/Province", "Starting ID" }, 0));
        this.mode = mode;
        initDefaults();
    }
    
    private void initDefaults() {
        lastID = 16000;
        ((DefaultTableModel)getModel()).addRow(new Vector<Object>(Arrays.asList(mode.getDefaultName(), lastID)));
    }
    
    public void addRow() {
        updateMaxID();
        lastID += 10;
        final DefaultTableModel defaultTableModel = (DefaultTableModel)getModel();
        defaultTableModel.addRow(new Vector<Object>(Arrays.asList(mode.getDefaultName(), lastID)));
    }
    
    public void removeRow(final int row) {
        ((DefaultTableModel)getModel()).removeRow(row);
        updateMaxID();
    }
    
    private void updateMaxID() {
        lastID = 0;
        for (int i = 0; i < getModel().getRowCount(); ++i) {
            final int tmp = (int)getModel().getValueAt(i, 1);
            if (tmp > lastID) {
                lastID = tmp;
            }
        }
    }
    
    @Override
    public TableCellEditor getCellEditor(final int row, final int column) {
        switch (column) {
            case 0:
                final String ctry = (String)getModel().getValueAt(row, column);
                return new ComboBoxCellEditor(getUnusedCountries(ctry), ctry);
            case 1:
                return new SpinnerCellEditor((int)getModel().getValueAt(row, column));
            default:
                throw new RuntimeException("Invalid column: " + column);
        }
    }
    
    private Set<String> getUnusedCountries() {
        final Map<String, String> ret = new HashMap<String, String>(mode.getCountryToTag());
        for (int i = 0; i < getModel().getRowCount(); ++i) {
            final String tmp = (String)getModel().getValueAt(i, 0);
            if (!mode.getDefaultName().equals(tmp)) {
                ret.remove(tmp);
            }
        }
        return ret.keySet();
    }
    
    private Set<String> getUnusedCountries(final String toKeep) {
        final Map<String, String> ret = new HashMap<String, String>(mode.getCountryToTag());
        for (int i = 0; i < getModel().getRowCount(); ++i) {
            final String tmp = (String)getModel().getValueAt(i, 0);
            if (!mode.getDefaultName().equals(tmp) && !toKeep.equals(tmp)) {
                ret.remove(tmp);
            }
        }
        return ret.keySet();
    }
    
    public List<NameTagID> getCountries() {
        final List<NameTagID> ret = new ArrayList<NameTagID>(getModel().getRowCount());
        for (int i = 0; i < getModel().getRowCount(); ++i) {
            final String nm = (String)getModel().getValueAt(i, 0);
            ret.add(new NameTagID(nm, (String)mode.getCountryToTag().get(nm), (int)getModel().getValueAt(i, 1)));
        }
        return ret;
    }
    
    public Mode getMode() {
        return mode;
    }
    
    public void setMode(final Mode mode) {
        if (this.mode == mode) {
            return;
        }
        this.mode = mode;
        clear();
    }
    
    public void clear() {
        for (int i = 0; i < this.getModel().getRowCount(); ++i) {
            ((DefaultTableModel)this.getModel()).removeRow(0);
        }
        initDefaults();
    }
    
    public void setRow(final int row, final String name, final int id) {
        ((DefaultTableModel)getModel()).setValueAt(name, row, 0);
        ((DefaultTableModel)getModel()).setValueAt(id, row, 1);
    }
    
    public void setClicksToEdit(final int num) {
        if (num < 1 || num > 4) {
            return;
        }
        clickCountToStart = num;
    }
    
    private abstract class MyCellEditor extends AbstractCellEditor implements TableCellEditor {
        @Override
        public boolean isCellEditable(final EventObject e) {
            return !(e instanceof MouseEvent) || ((MouseEvent)e).getClickCount() >= CountryIDTable.clickCountToStart;
        }
    }
    
    private class ComboBoxCellEditor extends MyCellEditor {
        private String currentValue;
        private JComboBox comboBox;
        
        public ComboBoxCellEditor(final Set<String> data, final String value) {
            final Vector<String> keys = new Vector<String>(data);
            Collections.sort(keys);
            comboBox = new JComboBox(keys);
            comboBox.setEditable(false);
            currentValue = value;
            comboBox.setSelectedItem(value);
            comboBox.addItemListener(new ItemListener() {
                public void itemStateChanged(final ItemEvent e) {
                    currentValue = (String)comboBox.getSelectedItem();
                    fireEditingStopped();
                }
            });
        }
        
        public Object getCellEditorValue() {
            return currentValue;
        }
        
        public Component getTableCellEditorComponent(final JTable table, final Object value, final boolean isSelected, final int row, final int column) {
            this.currentValue = (String)value;
            return comboBox;
        }
    }
    
    private class SpinnerCellEditor extends MyCellEditor {
        private int currentValue;
        private JSpinner spinner;
        
        public SpinnerCellEditor(final int value) {
            this.spinner = new JSpinner(new SpinnerNumberModel(value, MIN_ID, MAX_ID, 1));
            this.currentValue = value;
            this.spinner.setFont(new Font("Tahoma", 0, 11));
            this.spinner.setEditor(new JSpinner.NumberEditor(this.spinner, "#########"));
            this.spinner.addChangeListener(new ChangeListener() {
                public void stateChanged(final ChangeEvent e) {
                    SpinnerCellEditor.this.currentValue = (int)SpinnerCellEditor.this.spinner.getValue();
                }
            });
        }
        
        public Object getCellEditorValue() {
            return this.currentValue;
        }
        
        public Component getTableCellEditorComponent(final JTable table, final Object value, final boolean isSelected, final int row, final int column) {
            this.currentValue = (int)value;
            return this.spinner;
        }
    }
    
    public static final class NameTagID {
        public String name;
        public String tag;
        public int id;
        
        private NameTagID(final String name, final String tag, final int id) {
            this.name = name;
            this.tag = tag;
            this.id = id;
        }
    }
}
