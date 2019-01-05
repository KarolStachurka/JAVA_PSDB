package psbd.utils;

import javax.swing.table.DefaultTableModel;
import java.util.Vector;

public class MyTableModel extends DefaultTableModel {
    private int boolColumnIndex;
    public MyTableModel(String[] headers, int boolColumnIndex) {
        super(headers, 0);
        this.boolColumnIndex = boolColumnIndex;
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        Class columnClass = String.class;
        if(columnIndex == boolColumnIndex)
        {
            columnClass = Boolean.class;
        }
        return columnClass;
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == boolColumnIndex;
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        if (aValue instanceof Boolean && column == boolColumnIndex) {
            Vector rowData = (Vector)getDataVector().get(row);
            rowData.set(boolColumnIndex, (boolean)aValue);
            fireTableCellUpdated(row, column);
        }
    }
}
