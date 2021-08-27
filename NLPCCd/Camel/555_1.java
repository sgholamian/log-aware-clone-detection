//,temp,sample_7413.java,2,10,temp,sample_7417.java,2,10
//,3
public class xxx {
public static InputStream resolveResourceAsInputStream(ClassResolver classResolver, String uri) throws IOException {
if (uri.startsWith("file:")) {
uri = ObjectHelper.after(uri, "file:");
uri = tryDecodeUri(uri);


log.info("loading resource from file system");
}
}

};