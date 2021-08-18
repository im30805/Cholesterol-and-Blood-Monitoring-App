package Controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


public class SystolicBloodController implements ActionListener{
	private JTextField inputSystolicBlood;
	private DefaultTableModel model;
	private JTable table;
	private JButton viewmorebtn;
	private JRadioButton toggleBloodPressureButton;
	private JRadioButton defaultViewButton;
	
	
	public SystolicBloodController(JTextField inputSystolicBlood, JTable table, DefaultTableModel model,
			JButton viewmorebtn, JRadioButton toggleBloodPressureButton, JRadioButton defaultViewButton) {
		// TODO Auto-generated constructor stub
		this.inputSystolicBlood = inputSystolicBlood;
	    this.table = table;
	    this.model = model;
	    this.viewmorebtn = viewmorebtn;
	    this.toggleBloodPressureButton =  toggleBloodPressureButton;
	    this.defaultViewButton =  defaultViewButton;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String systolicInput = inputSystolicBlood.getText();
		int x = Integer.parseInt(systolicInput);
		viewmorebtn.setVisible(true);

		if (systolicInput != null || systolicInput != "") {
			if (toggleBloodPressureButton.isSelected()) {
				RowColorRenderer rowRenderer = new RowColorRenderer(1,x); // to enable the red highlighting feature
		        TableColumn column = table.getColumnModel().getColumn(1);
		        column.setCellRenderer(rowRenderer);
		        System.out.println("AHHHHHHHHHHHHHHHHHHHHHHH");
		        System.out.println(column);
//		        int row = table.getRowCount();
//		        for (int i = 0; i < row; i++) {
//		        	if (table.getValueAt(i, 1)!= null) {
//		        		String str = table.getValueAt(i, 1).toString();
//			            String digits = str.replaceAll("[^0-9.]", "");
//			            Double theStr = Double.parseDouble(digits);
//			            
//		        	}
//		        }
		        
			}
			else if (defaultViewButton.isSelected()) {
				RowColorRenderer rowRenderer = new RowColorRenderer(3,x); // to enable the red highlighting feature
		        TableColumn column = table.getColumnModel().getColumn(3);
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
