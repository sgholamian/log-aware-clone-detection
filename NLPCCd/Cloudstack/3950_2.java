//,temp,sample_1793.java,2,17,temp,sample_8206.java,2,17
//,2
public class xxx {
public void dummy_method(){
if (responseCode == 200) {
InputStream is = method.getResponseBodyAsStream();
Map<String, List<String>> eventDescriptions = getMultipleValuesFromXML(is, new String[] {"description"});
List<String> descriptionText = eventDescriptions.get("description");
if (descriptionText == null) {
} else {
for (String text : descriptionText) {
}
}
} else {


log.info("list events failed with error code following url was sent");
}
}

};