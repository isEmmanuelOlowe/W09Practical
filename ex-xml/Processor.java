import java.io.IOException;
import java.io.FileReader;
import java.io.StringReader;
import javax.xml.parsers.*;
import org.xml.sax.*;
import org.w3c.dom.*;

/**
* Process Json data into a format in which it can be formated for ouput to user.
*/
public class Processor {

  private Document reader;

  /**
  * Takes in the file that will be formatted.
  *
  * @param json the json data being processed.
  * @param isFile if true the json is in a file; if false it is in a string.
  * @throws IOException in event of errors finding file
  */
  public Processor(String xmlData, boolean isFile) throws IOException, SAXException, ParserConfigurationException {

    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
    DocumentBuilder builder = factory.newDocumentBuilder();
    reader = builder.parse(new InputSource(new StringReader(xmlData)));
  }

  /**
  * formats and outputs the json data in to text format.
  */
  public void format() {
    //There is only one tag with the name Heading so must be the 1st index
    String heading = reader.getElementsByTagName("Heading").item(0).getTextContent();
    if (heading.isEmpty()) {
      System.out.println("No Search Results Found.");
    }
    else {
      String head = heading + " can refer to:";
      System.out.println(head);
      //only one index with the name RelatedTopics so must be the 1st index
      //prints all the related topics
      Node relatedTopics = reader.getElementsByTagName("RelatedTopics").item(0);
      formatTopic(relatedTopics, 1);
    }
  }

  /**
  * Formats a topic group.
  *
  * @param relatedTopics the topic group being formatted
  * @param indent the number of indents the text should have
  */
  private void formatTopic(Node relatedTopics, int indent) {
    NodeList children  = relatedTopics.getChildNodes();
    for (int i = 0; i < children.getLength(); i++) {
      Node childNode = children.item(i);
      if (childNode.getNodeName().equals("RelatedTopicsSection")) {
        formatCategory(childNode);
      }
      else if (childNode.getNodeType() == Node.ELEMENT_NODE) {
        Element nextChild = (Element) childNode;
        if (nextChild.getTagName().equals("RelatedTopic")){
          printText(nextChild, indent);
        }
      }
    }
  }

  /**
  * formats a category object to be output to screen.
  *
  * @param category the category object that will be formatted
  */
  private void formatCategory(Node category) {
    NodeList categoryArray = category.getChildNodes();
    Element categoryTag = (Element) category;
    //checks the category is not empty
    if (categoryArray.getLength() > 0) {
      String heading = "  * Category: " + categoryTag.getAttribute("name");
      System.out.println(heading);
      //recursive process
      formatTopic(category, 2);
    }
  }

  /**
  * Prints the text key of topic object.
  *
  * @param topic the object being formatted for printing
  * @param indent the number of indents text should have
  */
  private void printText(Element topic, int indent) {
    //for adding the correct tabulature
    String tab = "";
    if (indent == 1) {
      tab = "  - ";
    }
    else if (indent == 2) {
      tab = "    - ";
    }
    //checks if the object contains text
    NodeList children = topic.getChildNodes();
    for (int i = 0; i < children.getLength(); i++) {
      Node childNode = children.item(i);
      if (childNode.getNodeType() == Node.ELEMENT_NODE) {
        Element child = (Element) childNode;
        if (child.getTagName().equals("Text")) {
          String result = child.getTextContent();
          String output = tab + result;
          System.out.println(output);
        }
      }
    }
  }
}
