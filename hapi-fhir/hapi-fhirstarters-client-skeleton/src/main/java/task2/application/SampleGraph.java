package task2.application;

import com.frontangle.ichart.chart.XYChart;
import com.frontangle.ichart.chart.bar.XYBarDataSeries;
import com.frontangle.ichart.chart.datapoint.DataPointBar;

import javax.swing.*;
import java.awt.*;

public class SampleGraph {
   public static void main(String args[]) {
      JFrame frame = new JFrame();

      XYBarDataSeries barSeries = new XYBarDataSeries();
      barSeries.add(new DataPointBar("A", 138, Color.BLUE));
      barSeries.add(new DataPointBar("B", 150, Color.GREEN));
      barSeries.add(new DataPointBar("B", 200, Color.ORANGE));

      XYChart barChart = new XYChart("Total Cholesterol (mg/DL)", "Patient", "", barSeries);
      JScrollPane scrollPane = new JScrollPane(barChart);
      scrollPane.setBounds(307, 172, 700, 700);

      frame.add(scrollPane);
      frame.setSize(700, 700);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);


   }
}
