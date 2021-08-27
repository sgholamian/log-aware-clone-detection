//,temp,sample_7414.java,2,13,temp,sample_7418.java,2,16
//,3
public class xxx {
public static InputStream resolveResourceAsInputStream(ClassResolver classResolver, String uri) throws IOException {
if (uri.startsWith("file:")) {
uri = ObjectHelper.after(uri, "file:");
uri = tryDecodeUri(uri);
return new FileInputStream(uri);
} else if (uri.startsWith("http:")) {
URL url = new URL(uri);


log.info("loading resource from http");
}
}

};