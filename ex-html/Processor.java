import java.io.IOException;
import java.io.FileReader;
import java.io.StringReader;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.Json;
import javax.json.stream.JsonParsingException;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
/**
* Process Json data into a format in which it can be formated for ouput to HTML Document.
*/
public class Processor {

  private JsonReader reader;
  //for writng html data to a file
  private PrintWriter writer;

  /**
  * Takes in the file that will be formatted.
  *
  * @param json the json data being processed.
  * @param isFile if true the json is in a file; if false it is in a string.
  * @throws IOException in event of errors finding file
  */
  public Processor(String json, boolean isFile) throws IOException {
    if (isFile) {
      reader = Json.createReader(new FileReader(json));
    }
    else {
      reader = Json.createReader(new StringReader(json));

    }
  }

  /**
  * formats and outputs the json data in to html format.
  *
  * @throws JsonParsingException in event the json file is invalid.
  */
  public void format() throws JsonParsingException, FileNotFoundException {
    JsonObject mainObject = reader.readObject();
    String heading = mainObject.get("Heading").toString();
    String head = heading + " can refer to:";
    writer = new PrintWriter(heading + ".html");
    writer.write("<h1>" + head + "</h1>");
    //prints all the related topics
    JsonArray topics = mainObject.getJsonArray("RelatedTopics");
    formatTopic(topics, 1);
    writer.close();
  }

  /**
  * Formats a topic group.
  *
  * @param relatedTopics the topic group being formatted
  * @param indent the number of indents the text should have
  * @throws JsonParsingException in event the json is invalid
  */
  private void formatTopic(JsonArray relatedTopics, int indent) {
    for (int i = 0; i < relatedTopics.size(); i++) {
      JsonObject topic = relatedTopics.getJsonObject(i);
      //checks for sub-categories
      if (topic.containsKey("Topics")) {
        formatCategory(topic);
      }
      else {
        printText(topic, indent);
      }
    }
  }

  /**
  * formats a category object to be output to html.
  *
  * @param category the category object that will be formatted
  * @throws JsonParsingException in event the json file is invalid
  */
  private void formatCategory(JsonObject category) {
    JsonArray categoryArray = category.getJsonArray("Topics");
    //checks the category is not empty
    if (categoryArray.size() > 0) {
      String heading = "Category: "  + category.get("Name").toString();
      writer.write("<h2>" + heading + "</h2>");
      //recursive process
      formatTopic(categoryArray, 2);
    }
  }

  /**
  * Prints the text key of topic object.
  *
  * @param topic the object being formatted for printing to html
  * @param indent the number of indents text should have
  * @throws JsonParsingException in event the json is invalid
  */
  private void printText(JsonObject topic, int indent) {

    //checks if the object contains text
    String url = topic.get("FirstURL").toString();
    if (topic.containsKey("Text")) {
      writer.write("<h3>Result:</h3>");
      String result = topic.get("Text").toString();
      writer.write("<a href='" + url + "'><h4>" + result + "</h4></a>");
      if (topic.containsKey("Icon")) {
        JsonObject icon = (JsonObject) topic.get("Icon");
        writer.write("<img src='" + icon.get("URL") + "'>");
      }
    }
  }
}
