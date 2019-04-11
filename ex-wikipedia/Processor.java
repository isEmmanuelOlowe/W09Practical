import java.io.IOException;
import java.io.FileReader;
import java.io.StringReader;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.Json;
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

    //the indexs which contain the wikipedia data
    final int searchIndex = 0;
    final int headingIndex = 1;
    final int descriptionIndex = 2;
    final int urlIndex = 3;

    JsonArray mainArray = reader.readArray();
    String title = mainArray.get(searchIndex).toString();
    JsonArray headings = mainArray.getJsonArray(headingIndex);
    JsonArray descriptions = mainArray.getJsonArray(descriptionIndex);
    JsonArray urls = mainArray.getJsonArray(urlIndex);
    String head = title + " can refer to:";
    System.out.println(head);
    // prints all the related pages
    formatPages(headings, descriptions, urls);
  }

  /**
  * Formats the pages from wikipedia api
  *
  * @param headings the headings of all the wikipedia pages found
  * @param descriptions the descriptions of all the wikipedia pages found
  * @param urls the url of all the wikipedia pages found
  */
  private void formatPages(JsonArray headings, JsonArray descriptions, JsonArray urls) {
    for (int i = 0; i < headings.size(); i++) {
      printText(headings.get(i).toString(), descriptions.get(i).toString(), urls.get(i).toString());
    }
  }

  /**
  * Prints the data for a found related page
  *
  * @param heading the title of the page
  * @param descrption the description of the page
  * @param url the url of the page
  */
  private void printText(String heading, String descrption, String url) {
    System.out.println("  Page: " + heading);
    System.out.println("    Description: " + descrption);
    System.out.println("    Url: " + url);
    System.out.println();
  }
}
