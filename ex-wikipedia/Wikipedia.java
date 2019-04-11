import java.io.IOException;
import javax.json.stream.JsonParsingException;

/**
* Interacts with Wikipedia Api and formats output.
*/
public class Wikipedia {

  /**
  * Executes the program.
  *
  * @param args the search parameter
  */
  public static void main(String[] args) {

    Wikipedia wikipedia = new Wikipedia();
    wikipedia.run(args);
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
      System.out.println("Not a valid JSON string!");
    }
  }

  /**
  * Converts the search parameter into valid api query.
  *
  * @param searchParameter the search parameter being queried
  * @return query url
  */
  public String createURL(String searchParameter) {
    final String head = "https://en.wikipedia.org/w/api.php?action=opensearch&format=json&search=";
    //replaces all the spaces with the designated space operand for api
    final String url = head + searchParameter.replaceAll("\\s", "%20");
    return url;
  }
}
