package Controller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.Vector;

/**
 * The BloodPressureOnlyViewController modifies the table view to allow the practitioner to only the systolic and
 * diastolic blood pressure values and the dateTime
 */

public class BloodPressureOnlyViewController implements ItemListener {
   private JTable table;
   private JScrollPane pane;
   private DefaultTableModel model;
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
    * */

   public BloodPressureOnlyViewController(JTable table, JScrollPane pane, DefaultTableModel model, AddPatientController addPatientController,
                                          RemovePatientController removePatientController){//, Vector data){
      this.table = table;
      this.pane = pane;
      this.model = model;
      //this.data = data;
      this.addPatientController = addPatientController;
      this.removePatientController = removePatientController;
   }

   /**
    * The action to be taken when an event has happened (e.g. clicking a button)
    * In this case the code modifies the table and model by removing the cholesterol values when the button was selected
    * and sets the boolean flags in AddPatientController and updates the model used
    *
    * If the button is unselected it will set it back to the default view
    *
    * @param e the event that was fired
    */

   @Override
   public void itemStateChanged(ItemEvent e) {
      Vector data;

      if(e.getStateChange()==ItemEvent.SELECTED) {
         data = model.getDataVector();
         System.out.println(data.toString());
         System.out.println("radio button for bp enabled");

         table.setAutoCreateColumnsFromModel(true);

         for(int i = 0; i < table.getColumnCount(); i++){
            if(model.getColumnName(i).equals("TOTAL CHOLESTEROL [mg/DL]")){
               removeColumn(i, model);
            }

         }

         for(int i = 0; i < table.getColumnCount(); i++){
            if(model.getColumnName(i).equals("TIME(C)")){
               removeColumn(i, model);
            }

         }

         //cholesterolEnabled = false;
         addPatientController.cholesterolEnabled(false);
         removePatientController.cholesterolEnabled(false);
         addPatientController.bloodPressureEnabled(true);
         removePatientController.bloodPressureEnabled(true);

         addPatientController.setNewModel(model);

      }


      else if(e.getStateChange()==ItemEvent.DESELECTED){
         System.out.println("radio button for bp disabled");

         table.setAutoCreateColumnsFromModel(false);

         DefaultTableModel defaultTableModel = (DefaultTableModel) table.getModel();
         TableColumn column = new TableColumn(defaultTableModel.getColumnCount());

         column.setHeaderValue("TOTAL CHOLESTEROL [mg/DL]");
         table.addColumn(column);

         data = model.getDataVector();
         String dataString = data.get(0).toString();
         System.out.println(dataString);
         //System.out.println(Arrays.toString(dataString.split(",")));
         String[] dataArr = dataString.split(",");
         System.out.println(dataArr[0].replace('[', ' ').trim());
         System.out.println(dataArr[1].trim());

         defaultTableModel.addColumn("TOTAL CHOLESTEROL [mg/DL]", new String[]{dataArr[1].trim()});
         table.moveColumn(table.getColumnCount()-1, 1);

         TableColumn timeColumn = new TableColumn(defaultTableModel.getColumnCount());

         timeColumn.setHeaderValue("TIME(C)");
         table.addColumn(timeColumn);
         defaultTableModel.addColumn("TIME(C)", new String[]{dataArr[2].trim()});
         table.moveColumn(table.getColumnCount() - 1, 2);

         model = defaultTableModel;
         table.setModel(model);
         //table.setAutoCreateColumnsFromModel(true);

         //cholesterolEnabled = true;
         addPatientController.cholesterolEnabled(true);
         removePatientController.cholesterolEnabled(true);
         addPatientController.bloodPressureEnabled(false);
         removePatientController.bloodPressureEnabled(false);
         //table.setModel(model);

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
