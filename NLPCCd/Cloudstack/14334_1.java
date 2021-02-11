//,temp,sample_9315.java,2,18,temp,sample_9316.java,2,18
//,3
public class xxx {
public void dummy_method(){
String temp = this.host + ":8096/?command=" + this.commandName;
Set<?> c = this.urlParam.entrySet();
Iterator<?> it = c.iterator();
while (it.hasNext()) {
Map.Entry<?, ?> me = (Map.Entry<?, ?>)it.next();
String key = (String)me.getKey();
String value = (String)me.getValue();
try {
temp = temp + "&" + key + "=" + URLEncoder.encode(value, "UTF-8");
} catch (Exception ex) {


log.info("unable to set parameter for the command");
}
}
}

};