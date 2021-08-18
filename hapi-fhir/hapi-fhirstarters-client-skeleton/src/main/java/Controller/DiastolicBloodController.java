package Controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;



public class DiastolicBloodController implements ActionListener{
	private JTextField inputDiastolicBlood;
	private DefaultTableModel model;
	private JTable table;
	private JRadioButton toggleCholesterolBtn;
	private JRadioButton toggleBloodPressureButton;
	private JRadioButton defaultViewButton;
	
	public DiastolicBloodController(JTextField inputDiastolicBlood, JTable table, DefaultTableModel model, JRadioButton toggleCholesterolBtn, JRadioButton toggleBloodPressureButton, JRadioButton defaultViewButton) {
		this.inputDiastolicBlood = inputDiastolicBlood;
	    this.table = table;
	    this.model = model;
	    this.toggleCholesterolBtn = toggleCholesterolBtn;
	    this.toggleBloodPressureButton =  toggleBloodPressureButton;
	    this.defaultViewButton =  defaultViewButton;
	}
	
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String diastolicInput = inputDiastolicBlood.getText();
		int x = Integer.parseInt(diastolicInput);

		if (diastolicInput != null || diastolicInput != "") {
			if (toggleBloodPressureButton.isSelected()) {
				RowColorRenderer rowRenderer = new RowColorRenderer(2,x); // to enable the red highlighting feature
		        TableColumn column = table.getColumnModel().getColumn(1);
		        column.setCellRenderer(rowRenderer);
			}
			else if (defaultViewButton.isSelected()) {
				RowColorRenderer rowRenderer = new RowColorRenderer(4,x); // to enable the red highlighting feature
		        TableColumn column = table.getColumnModel().getColumn(4);
		        column.setCellRenderer(rowRenderer);
			}

		
		}
	}
	private class RowColorRenderer extends DefaultTableCellRenderer {

	      private int colNo = 0;
	      private int val;

	      RowColorRenderer(int col,int val) {
	         colNo = col;
	         this.val = val;
	      }

	      @Override
	      public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
	         Component comp = super.getTableCellRendererComponent(table, value,isSelected, hasFocus, row, column);
	         JComponent jc = (JComponent) comp;
	         
	         if (table.getValueAt(row, colNo) != null) {
		            String str = table.getValueAt(row, colNo).toString();
		            String digits = str.replaceAll("[^0-9.]", "");
		            Double theStr = Double.parseDouble(digits);
		
		            if (theStr > val) {
		               jc.setForeground(Color.BLUE);
		            }
		            else if (theStr == 0 || theStr < val) {
		               jc.setForeground(table.getForeground());
		            }
		         }

	         

	         return jc;

	      }
	   }

}
