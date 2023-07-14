package sb;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
//import java.net.*;

public class ShowSource {

   static String demoPage = 
      "<html>"+
      "<table cellspacing='0'; cellpadding='0';>"+
      "<tr style='background-color:#90EE90;'><td>001&nbsp;&nbsp;&nbsp;</td><td>public static int fn(int x) {</td></tr>"+
      "<tr style='background-color:#FFA500;'><td>002&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;if (x==0)</td></tr>"+
      "<tr style='background-color:#90EE90;'><td>003&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;return -x;</td></tr>"+
      "<tr style='background-color:red;'><td>004&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;else</td></tr>"+
      "<tr style='background-color:red;'><td>005&nbsp;&nbsp;&nbsp;</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;return x;</td></tr>"+
      "<tr style='background-color:#90EE90;'><td>006&nbsp;&nbsp;&nbsp;</td><td>}</td></tr>"+
      "</table>"+
      "</html>";
	
	public static void main(String args[]) throws IOException {  
		JTextPane tp = new JTextPane();
		JScrollPane js = new JScrollPane();
		js.getViewport().add(tp);
		JFrame jf = new JFrame();
		jf.getContentPane().add(js);
		jf.pack();
		jf.setSize(400,500);
		jf.setVisible(true); 

		tp.setContentType("text/html");
		tp.setText(demoPage);

	}
}