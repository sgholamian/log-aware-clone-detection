//,temp,sample_6655.java,2,19,temp,sample_7063.java,2,19
//,3
public class xxx {
public void dummy_method(){
if (request.getUri().contains("?")) {
String query = ObjectHelper.after(request.getUri(), "?");
Map<String, Object> uriParameters = URISupport.parseQuery(query, false, true);
for (Map.Entry<String, Object> entry : uriParameters.entrySet()) {
String name = entry.getKey();
Object values = entry.getValue();
Iterator<?> it = ObjectHelper.createIterator(values);
while (it.hasNext()) {
Object extracted = it.next();
Object decoded = shouldUrlDecodeHeader(configuration, name, extracted, "UTF-8");


log.info("uri parameter");
}
}
}
}

};