//,temp,sample_9313.java,2,18,temp,sample_9312.java,2,18
//,3
public class xxx {
public void dummy_method(){
String temp = "bash xen/" + this.commandName;
Set<?> c = this.urlParam.entrySet();
Iterator<?> it = c.iterator();
while (it.hasNext()) {
Map.Entry<?, ?> me = (Map.Entry<?, ?>)it.next();
String key = (String)me.getKey();
String value = (String)me.getValue();
try {
temp = temp + " -" + key + " " + value;
} catch (Exception ex) {


log.info("unable to set parameter for the command");
}
}
}

};