#!/bin/bash

ARGS=("$@")
NARGS=${#ARGS[@]} 
IBIOMES_JAR=$IBIOMES_HOME/lib/ibiomes-local-0.0.1-SNAPSHOT-jar-with-dependencies.jar

USAGE="Usage: lookup-index -i <index-path> -t <term>"

if [ $NARGS -lt 4 ]; then
	echo $USAGE
	exit
fi

java -classpath $IBIOMES_JAR edu.utah.bmi.ibiomes.catalog.cli.DictionaryLookup ${ARGS[@]}
