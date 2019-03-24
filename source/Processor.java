import java.io.IOException;
import java.io.FileReader;
import java.io.StringReader;
import javax.json.*;
import javax.json.stream.JsonParsingException;

/**
* Process Json data into a format in which it can be formated for ouput to user.
*/
public class Processor {

  private JsonReader reader;

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
  * formats and outputs the json data in to text format.
  *
  * @throws JsonParsingException in event the json file is invalid.
  */
  public void format() throws JsonParsingException {
    JsonObject mainObject = reader.readObject();
    String heading = mainObject.get("Heading").toString();
    String head = heading + " can refer to:";
    System.out.println(head);
    JsonArray topics = mainObject.getJsonArray("RelatedTopics");
    formatTopic(topics, 1);
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
  * formats a category object to be output to screen.
  *
  * @param category the category object that will be formatted
  * @throws JsonParsingException in event the json file is invalid
  */
  private void formatCategory(JsonObject category) {
    String heading = "  * Category: "  + category.get("Name").toString();
    System.out.println(heading);
    //recursive process
    formatTopic(category.getJsonArray("Topics"), 2);
  }

  /**
  * Prints the text key of topic object.
  *
  * @param topic the object being formatted for printing
  * @param indent the number of indents text should have
  * @throws JsonParsingException in event the json is invalid
  */
  private void printText(JsonObject topic, int indent) {
    String tab = "";
    if (indent == 1) {
      tab = "  - ";
    }
    else if (indent == 2) {
      tab = "    - ";
    }
    if (topic.containsKey("Text")) {
      String result = topic.get("Text").toString();
      String output = tab + result;
      System.out.println(output);
    }
  }
}
