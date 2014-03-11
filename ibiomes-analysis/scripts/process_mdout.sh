#!/bin/bash

java -classpath \
$IBIOMES_LIB/log4j-1.2.15.jar:\
$IBIOMES_LIB/ibiomes-0.6.jar:\
$IBIOMES_LIB/jargon-2.5.jar:\
$IBIOMES_LIB/lib/log4j-1.2.15.jar:\
$IBIOMES_LIB/slf4j-log4j12-1.5.10.jar:\
$IBIOMES_LIB/slf4j-api-1.5.10.jar \
edu.utah.bmi.amber.ProcessMdOut $*
