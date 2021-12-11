package bankUI.component;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Date;

public class HistoryTable extends JTable {
    private TableRowSorter<TableModel> sorter;
    static String column[] = {"TYPE", "SENDER", "CTYPE", "AMOUNT", "RECEIVER", "DATE"};
    static String data[][] = {{"Deposit", "", "USD", "1000.0", "12345", new Date().toString()},
            {"Withdrawal", "12345", "CYN", "1200.0", "", new Date().toString()},
            {"Transfer", "12345", "HKD", "1300.0", "23456", new Date().toString()}};
    public HistoryTable(){
        super(data, column);
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

        if (realColumnIndex == column.length - 1) { //Sport column
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
            table.getColumn(column[i]).setCellRenderer(renderer);
            TableColumn tableColumn = table.getColumnModel().getColumn(i);
            if(i == 0 || i == 2 || i == 3) {
                tableColumn.setPreferredWidth(20);
            }
        }


    }
}
