//,temp,sample_6658.java,2,18,temp,sample_7066.java,2,18
//,2
public class xxx {
public void dummy_method(){
headers.put(Exchange.HTTP_RESPONSE_CODE, response.getStatus().getCode());
headers.put(Exchange.HTTP_RESPONSE_TEXT, response.getStatus().getReasonPhrase());
for (String name : response.headers().names()) {
if (name.toLowerCase().equals("content-type")) {
name = Exchange.CONTENT_TYPE;
}
List<String> values = response.headers().getAll(name);
Iterator<?> it = ObjectHelper.createIterator(values);
while (it.hasNext()) {
Object extracted = it.next();


log.info("http header");
}
}
}

};