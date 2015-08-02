#!/bin/bash
DescPath=$1
if [ "$DescPath" = "" ] ; then
  echo "Specify a descriptor!"
  exit 1
fi
if [ ! -f "$DescPath" ] ; then
  echo "'$DescPath' is not a file!"
  exit 1
fi
mvn compile exec:java -Dexec.mainClass=org.apache.uima.examples.cpe.SimpleRunCPE  -Dexec.args="$DescPath"
