#!/bin/bash

cd source
javac -cp "javax.json-1.0.jar:." *.java
j1="$(java -cp javax.json-1.0.jar:. DuckDuckGo 'Swim' 2>&1 )"
j2="$(java -cp javax.json-1.0.jar:. DuckDuckGo 'Spider' 2>&1 )"
j3="$(java -cp javax.json-1.0.jar:. DuckDuckGo 'Dave' 2>&1 )"

cd ../ex-xml
javac -cp "javax.xml-1.3.4.jar:." *.java
w1="$(java -cp javax.xml-1.3.4.jar:. DuckDuckGo 'Swim' 2>&1 )"
w2="$(java -cp javax.xml-1.3.4.jar:. DuckDuckGo 'Spider' 2>&1 )"
w3="$(java -cp javax.xml-1.3.4.jar:. DuckDuckGo 'Dave' 2>&1 )"

if [ "$j1" == "$w1" ]; then
  echo "Swim test successful"
else
  echo "Swim test failed"
fi

if [ "$j2" == "$w2" ]; then
  echo "Spider test successful"
else
  echo "Spider test failed"
fi

if [ "$j3" == "$w3" ]; then

  echo "Dave test successful"
else
  echo "Dave test failed"
fi
