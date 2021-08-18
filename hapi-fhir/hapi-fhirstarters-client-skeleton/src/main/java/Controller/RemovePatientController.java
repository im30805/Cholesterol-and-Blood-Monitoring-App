package Controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * RemovePatientController is a controller class for ViewData, this controller class is responsible
 * to delete selected data for certain patient
 * */
public class RemovePatientController implements ActionListener, MouseListener {
   private JTable table;
   private DefaultTableModel model;
   private JTextField nametxt, cholesteroltxt, timetxt;
   private int position;
   private JFrame frame;

   private boolean cholesterolIsEnabled = true;
   private boolean bloodPressureIsEnabled = true;

   /**
    * Controller Class
    * */
   public RemovePatientController(JTable table, DefaultTableModel model) {
      this.table = table;
      this.model = model;

   }

   /**
    * Action performed when button is pressed, this will delete selected row of data
    * @return None
    * */
   public void actionPerformed(ActionEvent e) {

      // check if there any row
      // if there is no row/ no data added

      if (table.getSelectedRowCount() == 0 ) {
         frame = new JFrame();
         // print this message
         JOptionPane.showMessageDialog(frame, "There is nothing to delete!");
         return;
      }
      else {
         // this is to get the position of removed patient
         for (int i = 0; i < table.getRowCount(); i++) {
            if (table.getValueAt(i, 2).equals(table.getValueAt(table.getSelectedRow(), 2))) {
               position = i;
            }
         }
         position += 1;
         // remove the row
         model.removeRow(table.getSelectedRow());
         table.setModel(model);
      }

      setPosition(position);

   }

   @Override
   /**
    * When click select the whole row
    * */
   public void mouseClicked(MouseEvent i) {
      // TODO Auto-generated method stub
      String name = table.getValueAt(table.getSelectedRow(), 0).toString();
      String cholesterol = table.getValueAt(table.getSelectedRow(), 1).toString();
      String time = table.getValueAt(table.getSelectedRow(), 2).toString();

      nametxt.setText(name);
      cholesteroltxt.setText(cholesterol);
      timetxt.setText(time);

   }
   @Override
   public void mousePressed(MouseEvent e) {
      // TODO Auto-generated method stub

   }
   @Override
   public void mouseReleased(MouseEvent e) {
      // TODO Auto-generated method stub

   }
   @Override
   public void mouseEntered(MouseEvent e) {
      // TODO Auto-generated method stub

   }
   @Override
   public void mouseExited(MouseEvent e) {
      // TODO Auto-generated method stub

   }


   public void setPosition(int position) {
      this.position = position;
   }
   public int getPosition() {
      return this.position;
   }

   public void cholesterolEnabled(boolean bool){
      cholesterolIsEnabled = bool;
      System.out.println("Cholesterol toggled remove - " + bool);
   }

   public void bloodPressureEnabled(boolean bool){
      bloodPressureIsEnabled = bool;
      System.out.println("Blood pressure toggled remove - " + bool);
   }

}
