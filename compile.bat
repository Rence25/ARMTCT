@ECHO OFF

SET PROMPT=Cov$G$S

SET java="C:\Program Files\Java\jdk-11.0.8\bin\java.exe"
SET javac="C:\Program Files\Java\jdk-11.0.8\bin\javac.exe"

REM SET javac="C:\Program Files\Java\jdk-17.0.3\bin\javac.exe"
REM SET java="C:\Program Files\Java\jdk-17.0.3\bin\java.exe"

del jacoco.exec >nul 2>&1

javac -d bin -cp bin;jacoco-0.8.8\lib\* CoverageReporter.java 
javac -d bin -cp bin;jars\* CoverageWindow.java CharterFlight.java FlightBooker.java

 