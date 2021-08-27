//,temp,sample_2902.java,2,20,temp,sample_2283.java,2,20
//,2
public class xxx {
public void dummy_method(){
for (int i = 0; i < map.getLength(); i++) {
Node att = map.item(i);
if (att.getNodeName().equals("uri") || att.getNodeName().endsWith("Uri")) {
final String value = att.getNodeValue();
String before = ObjectHelper.before(value, "?");
String after = ObjectHelper.after(value, "?");
if (before != null && after != null) {
String changed = after.replaceAll("\\s{2,}", "");
if (!after.equals(changed)) {
String newAtr = before.trim() + "?" + changed.trim();


log.info("removed whitespace noise from attribute");
}
}
}
}
}

};