package Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

/**
 * The CholesterolOnlyViewController modifies the table view to allow the practitioner to only see total
 * cholesterol values and the dateTime
 */

public class CholesterolOnlyViewController implements ItemListener{
   private JTable table;
   private JScrollPane pane;
   private DefaultTableModel model;
   private Vector data;
   //private boolean cholesterolEnabled;
   private AddPatientController addPatientController;
   private RemovePatientController removePatientController;

   /**
    * Constructor
    * @param table the table view
    * @param pane the scroll pane that is used to display the table
    * @param model the model the table is using
    * @param addPatientController the AddPatientController used to set the boolean flags
    * @param removePatientController the RemovePatientController used to set the boolean flags
    */
   public CholesterolOnlyViewController(JTable table, JScrollPane pane, DefaultTableModel model, AddPatientController addPatientController,
                                        RemovePatientController removePatientController){
      this.table = table;
      this.pane = pane;
      this.model = model;
      //this.data = data;
      this.addPatientController = addPatientController;
      this.removePatientController = removePatientController;
   }

   /**
    * The action to be taken when an event has happened (e.g. clicking a button)
    * In this case the code modifies the table and model by removing the BP values when the button was selected and
    * sets the boolean flags in AddPatientController and updates the model used
    *
    * If the button is unselected it will set it back to the default view
    *
    * @param e the event that was fired
    */
   public void itemStateChanged(ItemEvent e) {
      if(e.getStateChange()==ItemEvent.SELECTED) {
         data = model.getDataVector();
         System.out.println(data.toString());
         System.out.println("radio button for cholesterol enabled");

         table.setAutoCreateColumnsFromModel(true);

         for(int i = 0; i < table.getColumnCount(); i++){
            if(model.getColumnName(i).equals("SYSTOLIC BP [mm[Hg]]")){
               removeColumn(i, model);
            }


         }

         for(int i = 0; i < table.getColumnCount(); i++){
            if(model.getColumnName(i).equals("DIASTOLIC BP [mm[Hg]]")){
               removeColumn(i, model);
            }


         }

         for(int i = 0; i < table.getColumnCount(); i++){
            if(model.getColumnName(i).equals("TIME(BP)")){
               removeColumn(i, model);
            }


         }

         addPatientController.bloodPressureEnabled(false);
         removePatientController.bloodPressureEnabled(false);
         addPatientController.cholesterolEnabled(true);
         removePatientController.cholesterolEnabled(true);

         addPatientController.setNewModel(model);


      }

      else if(e.getStateChange()==ItemEvent.DESELECTED){
         System.out.println("radio button for cholesterol disabled");

            /*
            if(!model.getColumnName(1).equals("TIME(C)")){
                model.addColumn("TIME(C)", new int[][]{{1,2}});
                table.setModel(model);
                table.moveColumn(table.getColumnCount() - 1, 2);
            }

            if(!model.getColumnName(1).equals("TOTAL CHOLESTEROL [mg/DL]")){
                model.addColumn("TOTAL CHOLESTEROL [mg/DL]", new int[][]{{1,2}});
                table.setModel(model);
                table.moveColumn(table.getColumnCount() - 1, 1);
            }*/



         table.setAutoCreateColumnsFromModel(false);

         String dataString = data.get(0).toString();
         System.out.println(dataString);
         String[] dataArr = dataString.split(",");
         System.out.println(dataArr[5].replace(']', ' ').trim());
         System.out.println(dataArr[3].trim());
         System.out.println(dataArr[4].trim());
         System.out.println(dataArr[5].trim());


         DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
         TableColumn systolicBPColumn = new TableColumn(defaultTableModel.getColumnCount());

         systolicBPColumn.setHeaderValue("SYSTOLIC BP [mm[Hg]]");
         table.addColumn(systolicBPColumn);
         defaultTableModel.addColumn("SYSTOLIC BP [mm[Hg]]", new String[]{dataArr[3].trim()});
         table.moveColumn(table.getColumnCount()-1, 3);

         TableColumn diastolicBPColumn = new TableColumn(defaultTableModel.getColumnCount());

         diastolicBPColumn.setHeaderValue("DIASTOLIC BP [mm[Hg]]");
         table.addColumn(diastolicBPColumn);
         defaultTableModel.addColumn("DIASTOLIC BP [mm[Hg]]", new String[]{dataArr[4].trim()});
         table.moveColumn(table.getColumnCount() - 1, 4);

         TableColumn timeColumn = new TableColumn(defaultTableModel.getColumnCount());

         timeColumn.setHeaderValue("TIME(BP)");
         table.addColumn(timeColumn);
         defaultTableModel.addColumn("TIME(BP)", new String[]{dataArr[5].replace(']', ' ').trim()});
         table.moveColumn(table.getColumnCount() - 1, 5);

         model = defaultTableModel;

         //table.setAutoCreateColumnsFromModel(true);

         table.setModel(model);

         addPatientController.bloodPressureEnabled(true);
         removePatientController.bloodPressureEnabled(true);
         addPatientController.cholesterolEnabled(true);
         removePatientController.cholesterolEnabled(true);

         //addPatientController.setNewModel(model);

         pane.setViewportView(table);
      }

   }

   /**
    * Utility method to remove columns in the table
    * @param index position of column to be removed
    * @param defaultTableModel the model used by the table
    */
   private void removeColumn(int index, DefaultTableModel defaultTableModel){
      int nRow = defaultTableModel.getRowCount();
      int nCol = defaultTableModel.getColumnCount() - 1;
      Object[][] cells = new Object[nRow][nCol];
      String[] names = new String[nCol];

      for(int j = 0; j < nCol; j++){
         if(j < index){
            names[j] = defaultTableModel.getColumnName(j);
            for(int i = 0; i < nRow; i++){
               cells[i][j]= defaultTableModel.getValueAt(i, j);
            }
         }
         else{
            names[j] = defaultTableModel.getColumnName(j + 1);
            for(int i = 0; i < nRow; i++){
               cells[i][j] = defaultTableModel.getValueAt(i, j+1);
            }
         }
      }

      defaultTableModel = new DefaultTableModel(cells, names);
      model = defaultTableModel;
      table.setModel(model);

      pane.setViewportView(table);

   }


}
