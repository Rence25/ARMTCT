package sb;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.Timer;


import java.util.ArrayList;
import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import testAssessor.CoverageReporter;


import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;


import java.io.File;
import java.io.IOException;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.StackedBarRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.TextAnchor;
import org.jfree.chart.labels.ItemLabelAnchor;
import org.jfree.chart.labels.ItemLabelPosition;
import org.jfree.chart.ChartPanel;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import javax.swing.*;
import org.jfree.chart.*;
import org.jfree.chart.plot.*;
import org.jfree.data.category.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;


public class CoverageWindow extends JFrame {
    JFrame window1, frame1;
	JLabel config = new JLabel("<html><body>Welcome to test results....!!</body></html>"), testLabel;
	// JButton updateButton;
	private int count = 0;
	JTextPane sourceCodePane;
	String testMaterial;
	Timer htimer;
	public DefaultCategoryDataset dataset1;
    public JFreeChart chart1, chart2;
    List<String> methodLines;

	
    private String printSourceCode() throws IOException {
        String sourceFilePath = "CharterFlight.java";
		testMaterial = sourceFilePath;
        File sourceFile = new File(sourceFilePath);
        
        try (BufferedReader reader = new BufferedReader(new FileReader(sourceFile))) {
            String line;
            int lineNumber = 1;
            StringBuilder codeBuilder = new StringBuilder();
            
			codeBuilder.append("<html> <table cellspacing='0'; cellpadding='0';>");
            while ((line = reader.readLine()) != null) {
				if(lineNumber%2==0)
			     codeBuilder.append("<tr style='background-color:#90EE90;'><td>");
			    else
				 codeBuilder.append("<tr style='background-color:#FFA500;'><td>");
               codeBuilder.append(String.format("%-5s %s%n", "<b>" + lineNumber + "</b>", line));
                lineNumber++;
				codeBuilder.append("</td></tr>");
            }
           codeBuilder.append("</table></html> ");
            return (codeBuilder.toString());
        }
		catch(Exception e)
		{
			System.out.println(e);
		}
		return ("");
    }
	
private String extractMethodName(String methodLine) {
    // Remove leading/trailing whitespaces
    methodLine = methodLine.trim();

    // Use regular expression to extract the method name
    // Assuming the method declaration follows the format: <access_modifier> <return_type> <method_name>(<parameters>)
    String regex = "(\\w+\\s+){2}(\\w+)(?=\\()";

    // Find the first occurrence of the method name using regex
    Pattern pattern = Pattern.compile(regex);
    Matcher matcher = pattern.matcher(methodLine);

    if (matcher.find()) {
        return matcher.group();
    } else {
        // Return an empty string if the method name couldn't be extracted
        return "*";
    }
}

	
	CoverageWindow() {
		//window1 = this;
		setTitle("ARMTCT CoverageWindow");

		getContentPane().setLayout(null);
		getContentPane().setBackground(Color.GRAY);
		
		config.setVisible(true);
		getContentPane().add( config );
        config.setName("configName");
        config.setBounds( 25, 25, config.getPreferredSize().width, config.getPreferredSize().height );
		
		testLabel = new JLabel(" ");
		testLabel.setVisible(true);
		getContentPane().add( testLabel );
        testLabel.setName("testName");
        testLabel.setBounds( 25, 118, testLabel.getPreferredSize().width, testLabel.getPreferredSize().height );
	
		
	    // updateButton = new JButton("Coverage Synchronizer");
		// updateButton.setName("updateButton");
		// updateButton.setFocusPainted(false);
		// getContentPane().add( updateButton );
		// updateButton.setBounds( 25, 0, updateButton.getPreferredSize().width, updateButton.getPreferredSize().height);
		
		DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Lines Executed Percent",CoverageReporter.linesCovStatus*100);
        dataset.setValue("Lines Pending Execution Percent ", (1-CoverageReporter.linesCovStatus)*100);

        //-------------------------------------------
		sourceCodePane = new JTextPane();
		JScrollPane js = new JScrollPane();
		js.getViewport().add(sourceCodePane);
		JFrame jk = new JFrame();



        try {
        String demoPage = printSourceCode();
		sourceCodePane.setContentType("text/html");
		sourceCodePane.setText(demoPage);
		
		jk.setTitle(testMaterial);
		jk.getContentPane().add(js);
		jk.pack();
		jk.setSize(423,500);
		jk.setVisible(true); 
        } catch (IOException e) {
            e.printStackTrace();
        }
       //---------------------------------------------
		
		// Create the chart
        JFreeChart chart = ChartFactory.createPieChart("Pie Chart", dataset);

       //Customize the chart
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setLabelGenerator(new StandardPieSectionLabelGenerator("{0} = {1}"));

        //Create the chart panel and add it to a frame

		ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(580, 490));
        JPanel panel = new JPanel();
        panel.add(chartPanel);
        JFrame frame = new JFrame("Line Coverage");
        frame.setContentPane(panel);
        frame.pack();
        frame.setVisible(true);
		

		Timer timer = new Timer(50, e -> {
			count++;
			//Create the dataset for the pie chart
			dataset.setValue("Lines Executed Percent",CoverageReporter.linesCovStatus*100);
			dataset.setValue("Lines Pending Execution Percent ", (1-CoverageReporter.linesCovStatus)*100);
			panel.repaint();
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			});
        timer.start();
		
		
//Histogram
//Create dataset
dataset1 = new DefaultCategoryDataset();

//Filter lines to include only method definitions
methodLines = new ArrayList<>();
try (BufferedReader reader = new BufferedReader(new FileReader("CharterFlight.java"))) {
    String line;
    while ((line = reader.readLine()) != null) {
        if (line.contains("void") || line.contains("int") || line.contains("String") || line.contains("boolean")) {
            methodLines.add(line);
        }
    }
}
catch(IOException e2)
{
 System.out.println(e2);
}

//Add method lines to the dataset
for (String methodLine : methodLines) {
    String methodName = extractMethodName(methodLine);
	if(methodName.contains("*")){
		
	}
	else{
    dataset1.addValue(CoverageReporter.linesCovStatus * 100, "Executed", methodName);
    dataset1.addValue((1 - CoverageReporter.linesCovStatus) * 100, "Pending", methodName);
	}
}

//Create chart
chart1 = ChartFactory.createStackedBarChart(
    "Method(s) Coverage",
    "Methods",
    "Coverage Percent",
    dataset1,
    PlotOrientation.VERTICAL,
    true,
    true,
    false
);

//Set renderer
StackedBarRenderer renderer = new StackedBarRenderer();
renderer.setBaseItemLabelsVisible(true);
renderer.setSeriesPaint(0, Color.GREEN);
renderer.setSeriesPaint(1, Color.RED);
renderer.setBasePositiveItemLabelPosition(new ItemLabelPosition(
    ItemLabelAnchor.OUTSIDE12, TextAnchor.BOTTOM_CENTER
));
chart1.getCategoryPlot().setRenderer(renderer);

//Set axis
CategoryAxis xAxis = chart1.getCategoryPlot().getDomainAxis();
xAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI / 6.0));

//Display chart
// frame1 = new JFrame("ARMTCT: Bar Chart");
// frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
// frame1.setPreferredSize(new Dimension(420, 300));
// frame1.getContentPane().add(new ChartPanel(chart1));
// frame1.pack();
// frame1.setVisible(true);

//Schedule the updates every second
htimer = new Timer(1000, new UpdateTask());
htimer.start();

	
        // updateButton.addActionListener(new ActionListener() {
            // public void actionPerformed(ActionEvent e) {
             



            // }
        // });

		setSize(550,550);
		setVisible(true);
		
		
}
    // Define a TimerTask for updating the histogram
    private class UpdateTask implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Update the dataset with new values
			for (String methodLine : methodLines) {
				String methodName = extractMethodName(methodLine);
				if(methodName.contains("*")){
					
				}
				else{
				dataset1.addValue(CoverageReporter.linesCovStatus * 100, "Executed", methodName);
				dataset1.addValue((1 - CoverageReporter.linesCovStatus) * 100, "Pending", methodName);
				}
			}
chart2 = ChartFactory.createStackedBarChart(
    "Method(s) Coverage",
    "Methods",
    "Coverage Percent",
    dataset1,
    PlotOrientation.VERTICAL,
    true,
    true,
    false
);

            ChartPanel newChartPanel = new ChartPanel(chart2);
            newChartPanel.setPreferredSize(new Dimension(490, 330));
            JPanel panel = new JPanel();
            panel.add(newChartPanel);
            getContentPane().removeAll();
            setContentPane(panel);
			
			
				testLabel.setText("<html>Coverage Status:LINES "+CoverageReporter.ls+"&nbsp;&nbsp;Percentage Covered:"+CoverageReporter.linesCovStatus
				+"%<br/>Coverage Status:INSTRUCTIONS "+CoverageReporter.is+"<br/>Coverage Status:BRANCHES "+CoverageReporter.brs+"</html>");
				
				
				testLabel.setVisible(true);
		        getContentPane().add( testLabel );
                testLabel.setName("testName");
                testLabel.setBounds( 25, 118, testLabel.getPreferredSize().width, testLabel.getPreferredSize().height );

            // Repaint the frame to reflect the changes
            revalidate();
            repaint();
        }
    }
		
		
	public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
				new CoverageWindow().setVisible(true);
            }
        });		

	}
		
	
}
