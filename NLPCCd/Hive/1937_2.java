//,temp,sample_1268.java,2,16,temp,sample_1267.java,2,13
//,3
public class xxx {
public static HushableRandomAccessFileAppender createAppender( @PluginAttribute("fileName") final String fileName, @PluginAttribute("fileName") final String fileName, @PluginAttribute("append") final String append, @PluginAttribute("append") final String append, @PluginAttribute("name") final String name, @PluginAttribute("name") final String name, @PluginAttribute("immediateFlush") final String immediateFlush, @PluginAttribute("immediateFlush") final String immediateFlush, @PluginAttribute("bufferSize") final String bufferSizeStr, @PluginAttribute("bufferSize") final String bufferSizeStr, @PluginAttribute("ignoreExceptions") final String ignore, @PluginAttribute("ignoreExceptions") final String ignore, @PluginElement("Layout") Layout<? extends Serializable> layout, @PluginElement("Layout") Layout<? extends Serializable> layout, @PluginElement("Filter") final Filter filter, @PluginElement("Filter") final Filter filter, @PluginAttribute("advertise") final String advertise, @PluginAttribute("advertise") final String advertise, @PluginAttribute("advertiseURI") final String advertiseURI, @PluginAttribute("advertiseURI") final String advertiseURI, @PluginConfiguration final Configuration config) {
final boolean isAppend = Booleans.parseBoolean(append, true);
final boolean isFlush = Booleans.parseBoolean(immediateFlush, true);
final boolean ignoreExceptions = Booleans.parseBoolean(ignore, true);
final boolean isAdvertise = Boolean.parseBoolean(advertise);
final int bufferSize = Integers.parseInt(bufferSizeStr, 256 * 1024 /* RandomAccessFileManager.DEFAULT_BUFFER_SIZE */);
if (name == null) {


log.info("no name provided for fileappender");
}
}

};