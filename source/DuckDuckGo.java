import java.io.IOException;
import javax.json.stream.JsonParsingException;

/**
* Interacts with DuckDUckGo Api and formats output
*/
public class DuckDuckGo {

  /**
  * Executes the program.
  *
  * @param args the search parameter
  */
  public static void main(String[] args) {

    DuckDuckGo duckDuckGo = new DuckDuckGo();
    duckDuckGo.run(args);
  }

  /**
  * Executes the program quering with API.
  *
  * @param args the search Parameter
  */
  public void run(String[] args) {
    try {
      RESTClient api = new RESTClient();
      String searchParameter = args[0];
      String url = createURL(searchParameter);
      String jsonData = api.makeRESTCall(url);
      Processor processor = new Processor(jsonData, false);
      processor.format();
    }
    catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("Insufficient Arguments");
    }
    catch (IOException e) {
      System.out.println("FileNotFound");
    }
    catch (JsonParsingException e) {
      System.out.println("Invalid JSON String");
    }
  }

  /**
  * Converts the search parameter into valid api query.
  *
  * @param searchParameter the search parameter being queried
  */
  public String createURL(String searchParameter) {
    final String head = "https://api.duckduckgo.com/?q=";
    final String footer = "&format=json";
    final String url = head + "'" + searchParameter + "'" + footer;
    return url;
  }
}
