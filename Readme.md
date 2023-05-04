java -jar xxx.jar --spring.config.name=custom --spring.config.location=file:./

<dependency>
    <groupId>org.json</groupId>
    <artifactId>json</artifactId>
    <version>20200518</version>
</dependency>

Properties props = new Properties();
try (InputStream in = getClass().getResourceAsStream("./config.properties");
     OutputStream out = new FileOutputStream("./config.properties")) {

    if (in != null) {
        props.load(in);
        props.setProperty("key1", "value1");
        props.setProperty("key2", "value2");
        props.store(out, "Updated config properties");
        System.out.println(props);
    } else {
        System.err.println("config.properties not found");
    }
} catch (IOException e) {
    e.printStackTrace();
}

String temp = "aaa,bbbb,ccccc";
List<String> list = Arrays.asList(temp.split(","));
System.out.println(list);
    
    
public class EnumExample {
  
  // Define an enum
  enum Color {
    RED, GREEN, BLUE;
  }

  public static void main(String[] args) {
    // Use the enum
    Color color = Color.RED;
    System.out.println("The selected color is: " + color);

    // Loop through all the enum values
    for (Color c : Color.values()) {
      System.out.println("Enum value: " + c);
    }

    // Use the enum in a switch statement
    switch (color) {
      case RED:
        System.out.println("The color is red");
        break;
      case GREEN:
        System.out.println("The color is green");
        break;
      case BLUE:
        System.out.println("The color is blue");
        break;
    }
  }
}
