@echo off

REM ####################################################
REM
REM JAVA & ANT Environment Script
REM
REM Hack #1
REM The -lib arg is really unecessary.  We can always
REM use the <classpath> sub-element of <taskdef> to 
REM specify task level classes.
REM 
REM
REM ####################################################

REM ####################################################
REM    Java Root.
REM ####################################################
set ITG_JROOT=C:\code\ppm\SourceCode\java\target\jboss\server\kintana\deploy\itg.war\WEB-INF
set ITG_ROOT=%ITG_JROOT%\..\..

REM ####################################################
REM    ANT Related Environment.
REM ####################################################
set ANT_OPTS=-Xmx1024m -Dfile.encoding=UTF-8
set ANT_ARGS=-lib %ITG_JROOT%\lib

REM ####################################################
REM    TODO:  Get everyone to use the JDK + ANT
REM           distributed by the Build-Tools project.
REM ####################################################
REM
REM set BUILD_TOOLS=%ITG_JROOT%\..\..\..\Build-Tools
REM set JAVA_HOME=%BUILD_TOOLS%\JDKs\Windows\1.4.2_06-b03
REM set ANT_HOME=%BUILD_TOOLS%\ANTs\apache-ant-1.6.2
REM set PATH=%JAVA_HOME%\bin;%ANT_HOME%\bin;%PATH%
REM
set CLASSPATH=%CLASSPATH%;
set JDK_LIB_DIR=C:\Java\jdk1.7.0_79
set JDK_LIB=%JDK_LIB_DIR%\rt.jar;%JDK_LIB_DIR%\tools.jar
set SOURCE=1.7
set TARGET=1.7

echo   JAVA_HOME %JAVA_HOME%
echo    ANT_HOME %ANT_HOME%
echo    ANT_ARGS %ANT_ARGS%
echo    ITG_ROOT %ITG_ROOT%
echo   ITG_JROOT %ITG_JROOT%
echo   JDK_LIB   %JDK_LIB%
echo   SOURCE    %SOURCE%
echo   TARGET    %TARGET%