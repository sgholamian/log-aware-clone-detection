//,temp,sample_4059.java,2,15,temp,sample_166.java,2,15
//,3
public class xxx {
private static URL urlFromPathString(String onestr) {
URL oneurl = null;
try {
if (onestr.startsWith("file:/")) {
oneurl = new URL(onestr);
} else {
oneurl = new File(onestr).toURL();
}
} catch (Exception err) {


log.info("bad url ignoring path");
}
}

};