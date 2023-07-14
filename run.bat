@ECHO OFF

SET PROMPT=Cov$G$S

SET java="C:\Program Files\Java\jdk-11.0.8\bin\java.exe"
SET javac="C:\Program Files\Java\jdk-11.0.8\bin\javac.exe"

del jacoco.exec >nul 2>&1

java -cp bin;jars\*;jacoco-0.8.8\lib\* -ea -javaagent:jacoco-0.8.8\lib\jacocoagent.jar=append=false,jmx=true testAssessor.CoverageReporter sb.CoverageWindow sb.FlightBooker


