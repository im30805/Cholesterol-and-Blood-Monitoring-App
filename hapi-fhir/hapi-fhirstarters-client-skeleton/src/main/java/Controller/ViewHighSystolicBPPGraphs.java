package Controller;

import com.frontangle.ichart.chart.XYChart;
import com.frontangle.ichart.chart.axis.NumericalInterval;
import com.frontangle.ichart.chart.axis.XAxis;
import com.frontangle.ichart.chart.axis.YAxis;
import com.frontangle.ichart.chart.datapoint.DataPoint;
import com.frontangle.ichart.scaling.LinearNumericalAxisScaling;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * The ViewHighSystolicBPPGraphs class implements an action listener that allows the patients' graphs to be generated
 * separately into individual frames
 */

public class ViewHighSystolicBPPGraphs implements ActionListener {
   private ArrayList<ArrayList<ArrayList<String>>> monitoredSystolicBPList ;
   private ArrayList<String> monitoredPatientNames;

   /**
    * Constructor
    * @param monitoredPatientNames the patient names that are monitored and have high systolic BP
    * @param monitoredSystolicBPList the patients' latest 5 readings of their systolic BP
    */
   public ViewHighSystolicBPPGraphs(ArrayList<String> monitoredPatientNames, ArrayList<ArrayList<ArrayList<String>>> monitoredSystolicBPList ){
      this.monitoredPatientNames = monitoredPatientNames;
      this.monitoredSystolicBPList  = monitoredSystolicBPList;

   }

   /**
    * The action to be taken when an event has happened (e.g. clicking a button)
    * In this case the code below adds the BP values into an ArrayList, generates a JFrame and adds the graph to the
    * JFrame (as an example, if there were 3 patients being monitored and have high systolic BP, 3 JFrames
    * with their respective graphs will be generated all at once)
    *
    * @param e the event that was fired
    */
   @Override
   public void actionPerformed(ActionEvent e) {
      YAxis yAxis = new YAxis(new LinearNumericalAxisScaling(0.0, 250.0, new NumericalInterval(4, 50.0), new NumericalInterval(
         2, 0.5), null), "y");
      XAxis xAxis = new XAxis(new LinearNumericalAxisScaling(0.0, 5.0, 1.0, null, null), "x");

      for(int i = 0; i < monitoredSystolicBPList.size(); i++){
         ArrayList<DataPoint> values = new ArrayList<>();
         JFrame frame = new JFrame();

         for(int j = 0; j < monitoredSystolicBPList.get(i).size(); j++) {
            //System.out.println("Values... " + monitoredSystolicBPList.get(i).get(j).get(0));
            values.add(new DataPoint(j, Double.parseDouble(monitoredSystolicBPList.get(i).get(j).get(0))));
         }

         XYChart chart = new XYChart(monitoredPatientNames.get(i), "Number of latest readings",
            "Systolic BP [mmHg]", values, yAxis, xAxis);
         frame.add(chart);
         frame.setSize(1500, 1500);
         frame.setVisible(true);
      }

   }
}
