package bankUI.component;

import model.Transaction;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;
import java.awt.event.MouseEvent;
import java.util.Date;

/**
 * Class for creating history table data
 */
public class HistoryTable extends JTable {
    private TableRowSorter<TableModel> sorter;
    private static String[] columns = {"TYPE", "SENDER", "CTYPE", "AMOUNT", "RECEIVER", "DATE"};
    private String[][] data;

    /**
     * @param transactionList
     */
    public HistoryTable(List<Transaction> transactionList){
        data = new String[transactionList.size()][6];
        for (int i = 0 ; i < transactionList.size() ; i ++) {
            data[i][0] = transactionList.get(i).getTransactionType();
            data[i][1] = transactionList.get(i).getSourceAccountId();
            data[i][2] = transactionList.get(i).getCurrency();
            data[i][3] = String.valueOf(transactionList.get(i).getAmount());
            data[i][4] = transactionList.get(i).getDestinationAccountId();
            data[i][5] = transactionList.get(i).getDate().toString();
        }

        TableModel dataModel = new AbstractTableModel() {
            public String getColumnName(int column) { return columns[column].toString(); }
            public int getRowCount() { return data.length; }
            public int getColumnCount() { return columns.length; }
            public Object getValueAt(int row, int col) { return data[row][col]; }
        };

        setModel(dataModel);
        resetTable(this);
    }

    public TableRowSorter<TableModel> getSorter() {
        return sorter;
    }

    @Override
    public String getToolTipText(MouseEvent e) {
        String tip = null;
        java.awt.Point p = e.getPoint();
        int rowIndex = rowAtPoint(p);
        int colIndex = columnAtPoint(p);
        int realColumnIndex = convertColumnIndexToModel(colIndex);

        if (realColumnIndex == columns.length - 1) { //Sport column
            tip = "The date of this transaction is "
                    + getValueAt(rowIndex, colIndex);
        } else { //another column
            //You can omit this part if you know you don't
            //have any renderers that supply their own tool
            //tips.
            tip = super.getToolTipText(e);
        }
        return tip;
    }

    /**
     * reset the table data
     * @param transactionList
     */
    public void resetData(List<Transaction> transactionList) {
        data = new String[transactionList.size()][6];
        for (int i = 0 ; i < transactionList.size() ; i ++) {
            data[i][0] = transactionList.get(i).getTransactionType();
            data[i][1] = transactionList.get(i).getSourceAccountId();
            data[i][2] = transactionList.get(i).getCurrency();
            data[i][3] = String.valueOf(transactionList.get(i).getAmount());
            data[i][4] = transactionList.get(i).getDestinationAccountId();
            data[i][5] = transactionList.get(i).getDate().toString();
        }
        TableModel dataModel = new AbstractTableModel() {
            public String getColumnName(int column) { return columns[column].toString(); }
            public int getRowCount() { return data.length; }
            public int getColumnCount() { return columns.length; }
            public Object getValueAt(int row, int col) { return data[row][col]; }
        };

        setModel(dataModel);
        resetTable(this);
    }

    /**
     * set the pattern of this history table
     * @param table
     */

    private void resetTable(JTable table){

        // sorter
        sorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(sorter);

        //set table header
        JTableHeader header = table.getTableHeader();
        header.setPreferredSize(new Dimension(header.getWidth(), 35));
        header.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 14));

        //set table row height
        table.setRowHeight(20);

        //set table color
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer(){
            public Component getTableCellRendererComponent(JTable t, Object v, boolean isS, boolean hasF,
                                                           int row, int column) {
                if (row % 2 == 0) {
                    setBackground(Color.PINK);
                } else {
                    setBackground(Color.white);
                }
                return super.getTableCellRendererComponent(t, v , isS, hasF, row, column);
            }
        };
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumn(columns[i]).setCellRenderer(renderer);
            TableColumn tableColumn = table.getColumnModel().getColumn(i);
            if(i == 0 || i == 2 || i == 3) {
                tableColumn.setPreferredWidth(20);
            }
        }


    }
}
