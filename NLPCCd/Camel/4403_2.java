//,temp,sample_1717.java,2,20,temp,sample_1718.java,2,19
//,3
public class xxx {
public void dummy_method(){
for (int i = 0; i < list.getLength(); i++) {
Node node = list.item(i);
String lineNumber = (String) node.getUserData(XmlLineNumberParser.LINE_NUMBER);
String lineNumberEnd = (String) node.getUserData(XmlLineNumberParser.LINE_NUMBER_END);
if (lineNumber != null && lineNumberEnd != null && !changed.isEmpty()) {
int start = Integer.valueOf(lineNumber);
int end = Integer.valueOf(lineNumberEnd);
boolean within = withinChanged(start, end, changed);
if (within) {
} else {


log.info("no changes to route in lines");
}
}
}
}

};