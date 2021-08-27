//,temp,sample_8159.java,2,17,temp,sample_141.java,2,17
//,2
public class xxx {
public void dummy_method(){
StringBuilder sb = new StringBuilder();
for (char ch : hostName.toCharArray()) {
if (ch < 127) {
sb.append(ch);
} else {
changed = true;
}
}
if (changed) {
String newHost = sb.toString();


log.info("sanitized hostname from to");
}
}

};