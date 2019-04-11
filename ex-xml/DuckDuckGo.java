import java.io.IOException;
/**
* Interacts with DuckDuckGo Api and formats output.
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
      String xmlData = api.makeRESTCall(url);
      Processor processor = new Processor(xmlData, false);
      processor.format();
    }
    catch (ArrayIndexOutOfBoundsException e) {
      System.out.println("Insufficient Arguments");
    }
    catch (Exception e) {
      System.out.println("Here");
      e.printStackTrace();
    }
    // catch (JsonParsingException e) {
    //   System.out.println("Not a valid JSON string!");
    // }
  }

  /**
  * Converts the search parameter into valid api query.
  *
  * @param searchParameter the search parameter being queried
  * @return query url
  */
  public String createURL(String searchParameter) {
    final String head = "https://api.duckduckgo.com/?q=";
    final String footer = "&format=xml";
    final String url = head + "'" + searchParameter + "'" + footer;
    return url;
  }
}
