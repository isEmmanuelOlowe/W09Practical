import java.io.IOException;
import javax.json.stream.JsonParsingException;
/**
* Takes in Json in file and formats it for output.
*/
public class Describe {

  /**
  * Executes the program formatting the json file for ouptut.
  *
  * @param args the name of the file.
  */
  public static void main(String[] args) {
    Describe describe = new Describe();
    describe.run(args);
  }

  /**
  * Executes the program formatting the json file for ouptut.
  *
  * @param args the name of the file.
  */
  public void run(String[] args) {
    try {
      String fileName = args[0];
      Processor processor = new Processor(fileName, true);
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
}
