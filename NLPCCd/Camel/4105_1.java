//,temp,sample_6985.java,2,18,temp,sample_4924.java,2,13
//,3
public class xxx {
public void dummy_method(){
try {
urls = componentClass.getClassLoader().getResources("camel-connector.json");
} catch (IOException e) {
throw new IllegalArgumentException("Cannot open camel-connector.json in classpath for connector " + componentName);
}
while (urls.hasMoreElements()) {
try (InputStream is = urls.nextElement().openStream()) {
String json = IOHelper.loadText(is);
JsonObject output = (JsonObject) Jsoner.deserialize(json);
String javaType = output.getString("javaType");


log.info("found camel connector json in classpath with javatype");
}
}
}

};