//,temp,sample_7414.java,2,13,temp,sample_7418.java,2,16
//,3
public class xxx {
public static URL resolveResourceAsUrl(ClassResolver classResolver, String uri) throws MalformedURLException {
if (uri.startsWith("file:")) {
String name = ObjectHelper.after(uri, "file:");
uri = tryDecodeUri(uri);
File file = new File(name);
if (!file.exists()) {
return null;
}
return new URL(uri);
} else if (uri.startsWith("http:")) {


log.info("loading resource from http");
}
}

};