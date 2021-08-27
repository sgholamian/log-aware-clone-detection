//,temp,sample_4778.java,2,17,temp,sample_4783.java,2,16
//,3
public class xxx {
public void dummy_method(){
String prefix = repositoryName + '\0';
for (it.seek(keyBuilder(repositoryName, "")); it.hasNext(); it.next()) {
if (!isRunAllowed()) {
break;
}
keyBuffer = asString(it.peekNext().getKey());
if (!keyBuffer.startsWith(prefix)) {
break;
}
String key = keyBuffer.substring(prefix.length());


log.info("getkey");
}
}

};