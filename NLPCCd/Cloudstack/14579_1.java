//,temp,sample_8205.java,2,19,temp,sample_1792.java,2,19
//,2
public class xxx {
public void dummy_method(){
HttpClient client = new HttpClient();
HttpMethod method = new GetMethod(url);
int responseCode = client.executeMethod(method);
if (responseCode == 200) {
InputStream is = method.getResponseBodyAsStream();
Map<String, List<String>> eventDescriptions = getMultipleValuesFromXML(is, new String[] {"description"});
List<String> descriptionText = eventDescriptions.get("description");
if (descriptionText == null) {
} else {
for (String text : descriptionText) {


log.info("event");
}
}
}
}

};