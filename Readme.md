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
