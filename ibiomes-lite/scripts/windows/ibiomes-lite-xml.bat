
@echo off
IF "%IBIOMES_HOME%" == "" (

	GOTO ErrorEnvVar
)
java -version 2> tmp_java_version.txt
set /p JAVA_VERSION= < tmp_java_version.txt
del tmp_java_version.txt
set JAVA_VERSION=%JAVA_VERSION:~14,1%%JAVA_VERSION:~16,1%
IF %JAVA_VERSION% LSS 17 (
	GOTO ErrorJava
)
set IBIOMES_CLASSES=%IBIOMES_HOME%\ibiomes-lite\target\ibiomes-lite-0.0.1-SNAPSHOT-jar-with-dependencies.jar
java -classpath %IBIOMES_CLASSES% edu.utah.bmi.ibiomes.cli.CommandXML %*

GOTO end

:ErrorJava
echo Java 1.7 is required. Please update and rerun.
GOTO end

:ErrorEnvVar
echo The IBIOMES_HOME environment variable was not set properly.
GOTO end

:end