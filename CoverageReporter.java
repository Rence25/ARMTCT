package testAssessor;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeMap;
import java.util.Map;
import java.lang.reflect.*;

//Libraries: jacocoagent.jar and jacococli.jar
import org.jacoco.agent.rt.IAgent; 
import org.jacoco.core.analysis.CoverageBuilder; // jacoco core.jar
import org.jacoco.core.analysis.Analyzer;
import org.jacoco.core.analysis.IClassCoverage;
import org.jacoco.core.analysis.IMethodCoverage;
import org.jacoco.core.analysis.ILine;
import org.jacoco.core.data.ExecutionDataStore;
import org.jacoco.core.data.SessionInfoStore;
import org.jacoco.core.data.ExecutionDataReader;


import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;


public class CoverageReporter {

   private static boolean watch=false; // set to true to printout everything

   private static final String[] coverageNames={"EMPTY","NOT_COVERED","FULLY_COVERED","PARTIALLY_COVERED"};
   public static String ls,is,brs;
   public static float linesCovStatus;
   
   public static String funs="no methods yet";//function names


   // Stores the class file for the class being tested
   private static byte[] classFileData=null;

   static boolean done=false;
   

   public static void printCoverageData() {
	   
	    try{
   
        String filePath = "bin/sb/CharterFlight.class";
        
        File file = new File(filePath);
        URL url = file.toURI().toURL();
        URLClassLoader classLoader = new URLClassLoader(new URL[] { url });
   
   }
   catch(Exception e){
	   e.printStackTrace();
   }
	   
	   
      System.out.println("printCoverageData");
      Counters[][] allResults=null;
      System.out.println("Coverage data:");
      try {
         allResults = getCoverageData("bin/sb/CharterFlight.class");
      } catch (Exception e) {
         e.printStackTrace();
         if ((e.getClass()==java.lang.IllegalStateException.class) &&
               (e.getMessage().contains("JaCoCo agent not started."))) {
            System.out.println("RUNTIME ERROR - use coverage as in Eclipse.");
         }
         else {
            System.out.println("ERROR: exception raisedin getCovDat");
            e.printStackTrace();
         }
      }
	  
	  for (Counters[] classResults : allResults) {
   for (Counters methodResults : classResults) {
      Counters m = methodResults;
      funs = m.theMethod;
      System.out.println("Class: " + m.theClass);
      System.out.println("   Method " + m.theMethod);
      
	  
	  System.out.println("Class: "+m.theClass);
               System.out.println("   Method "+m.theMethod);
               System.out.println("      Status");
               System.out.println("         lines="+coverageNames[m.m_lineCounter_status]);
			   ls=coverageNames[m.m_lineCounter_status];
               System.out.println("         instructions="+coverageNames[m.m_instructionCounter_status]);
			   is=coverageNames[m.m_instructionCounter_status];
               System.out.println("         branches="+coverageNames[m.m_branchCounter_status]);
			   brs=coverageNames[m.m_branchCounter_status];
               System.out.println("      Counters");
			   linesCovStatus = (float)((m.m_lineCounter)/(float)(m.m_lineMissed+m.m_lineCounter))																										;
               System.out.println("         lines="+m.m_lineCounter+"/"+m.m_lineMissed);
               System.out.println("         instructions="+m.m_instructionCounter+"/"+m.m_instructionMissed);
               System.out.println("         branches="+m.m_branchCounter+"/"+m.m_branchMissed);
   }
}


   }

   // This only gets the coverage data for a particular class and method
   // Alter this to filter out all the classes in the required package perhaps?
 private static Counters[][] getCoverageData(String classFileName) throws Exception {
    if (watch) System.out.println("getCoverageData");
    try {
        IAgent agent = org.jacoco.agent.rt.RT.getAgent();
        byte[] executionData = agent.getExecutionData(false);
        CoverageBuilder coverageBuilder = new CoverageBuilder();
        InputStream myInputStream = new ByteArrayInputStream(executionData);
        final ExecutionDataReader reader = new ExecutionDataReader(myInputStream);
        final SessionInfoStore sessionInfoStore = new SessionInfoStore();
        final ExecutionDataStore executionDataStore = new ExecutionDataStore();
        reader.setSessionInfoVisitor(sessionInfoStore);
        reader.setExecutionDataVisitor(executionDataStore);
        reader.read();
        Analyzer analyzer = new Analyzer(executionDataStore, coverageBuilder);
        
        if (classFileData == null) {
            File classFile = new File(classFileName);
            if (!classFile.exists())
                System.out.println("INTERNAL ERROR: class file not found: " + classFileName);
            classFileData = Files.readAllBytes(classFile.toPath());
        }
        
        analyzer.analyzeClass(classFileData, classFileName);
        Collection<IClassCoverage> classes = coverageBuilder.getClasses();
        Counters[][] results = new Counters[classes.size()][];
        int classIndex = 0;

        for (IClassCoverage classCoverage : classes) {
            Collection<IMethodCoverage> methods = classCoverage.getMethods();
            results[classIndex] = new Counters[methods.size()];
            int methodIndex = 0;

            for (IMethodCoverage methodCoverage : methods) {
                results[classIndex][methodIndex] = new Counters();
                results[classIndex][methodIndex].theClass = classCoverage.getName();
                results[classIndex][methodIndex].theMethod = methodCoverage.getName();
                results[classIndex][methodIndex].m_lineCounter_status = methodCoverage.getLineCounter().getStatus();
                results[classIndex][methodIndex].m_lineCounter = methodCoverage.getLineCounter().getCoveredCount();
                results[classIndex][methodIndex].m_lineMissed = methodCoverage.getLineCounter().getMissedCount();
                results[classIndex][methodIndex].m_instructionCounter_status = methodCoverage.getInstructionCounter().getStatus();
                results[classIndex][methodIndex].m_instructionCounter = methodCoverage.getInstructionCounter().getCoveredCount();
                results[classIndex][methodIndex].m_instructionMissed = methodCoverage.getInstructionCounter().getMissedCount();
                results[classIndex][methodIndex].m_branchCounter_status = methodCoverage.getBranchCounter().getStatus();
                results[classIndex][methodIndex].m_branchCounter = methodCoverage.getBranchCounter().getCoveredCount();
                results[classIndex][methodIndex].m_branchMissed = methodCoverage.getBranchCounter().getMissedCount();

                methodIndex++;
            }

            classIndex++;
        }

        return results;
    } catch (Exception ex) {
        System.out.println("Exception raised getting counters.");
        ex.printStackTrace();
    }
    
    return null;
 }


   //
   // main method - use to start the GUI application
   //

   public static void main(String[] args) {

      // check args

      if ((args.length==0) || (args[0]==null)) {
         System.out.println("Enter GUI application name as first argument");
         System.exit(1);
      }

      // add shutdown hook to print coverage info

      Runtime.getRuntime().addShutdownHook(new Thread() {
         public void run() {
            done = true;
            System.out.println("Shutdown hook running...");
            CoverageReporter.printCoverageData();
         }
      });

      // run the app

      try {
         Class<?> c = Class.forName(args[0]);
         Method m=c.getDeclaredMethod("main",String[].class);
         String[] a=null;
         System.out.println("Application starting...");
         m.invoke(null, (Object)a);
		 
		 Class<?> c1 = Class.forName(args[1]);
         Method m1=c1.getDeclaredMethod("main",String[].class);
         String[] a1=null;
         m1.invoke(null, (Object)a1);
      } catch (Exception ex) {
         System.out.println("Error");
         System.out.println(ex.toString());
         System.exit(1);
      }

      // print coverage stats periodically

      while (!done) {
         try {
            Thread.sleep(1000);
         } catch (Exception ex) {
            System.out.println(ex.toString());
         }
         CoverageReporter.printCoverageData();
      }

   }

}

class Counters {
   public String theClass;
   public String theMethod;
   public int m_lineCounter_status=-1;
   public int m_lineCounter=0;
   public int m_lineMissed=0;
   public int m_instructionCounter_status=-1;
   public int m_instructionCounter=0;
   public int m_instructionMissed=0;
   public int m_branchCounter_status=-1;
   public int m_branchCounter=0;
   public int m_branchMissed=0;
};