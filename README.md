# W09Practical
The Week 9 Practical for CS1003

### Source: This is the basic specification

```bash
javac -cp "javax.json-1.0.jar:." *.java
java -cp "javax.json-1.0.jar:." Describe [input_file]
java -cp "javax.json-1.0.jar:." DuckDuckGo [search_parameter]
```

output is to terminal

### ex-xml: XML Extension

```bash
javac -cp "javax.xml-1.3.4.jar:." *.java
java -cp "javax.xml-1.3.4.jar:." DuckDuckGo [search_parameter]
```

output is to terminal

### ex-html: HTML Extension

```bash
javac -cp "javax.json-1.0.jar:." *.java
java -cp "javax.json-1.0.jar:." DuckDuckGo [search_parameter]
```

The output shall then be in the `ex-html` directory called `search_parameter.html`

### ex-wikipedia

```bash
java -cp "javax.json-1.0.jar:." *.java
java -cp "javax.json-1.0.jar:." Wikipedia [search_parameter]
```

output is to terminal