//,temp,sample_1777.java,2,16,temp,sample_524.java,2,16
//,3
public class xxx {
public static Writer createWriter(final Configuration conf, final FileSystem fs, final Path path, final boolean overwritable) throws IOException {
Class<? extends Writer> logWriterClass = conf.getClass("hbase.regionserver.hlog.writer.impl", ProtobufLogWriter.class, Writer.class);
Writer writer = null;
try {
writer = logWriterClass.newInstance();
writer.init(fs, path, conf, overwritable);
return writer;
} catch (Exception e) {
if (e instanceof CommonFSUtils.StreamLacksCapabilityException) {


log.info("the regionserver write ahead log provider for filesystem implementations relies on the ability to call for proper operation during component failures but the current filesystem does not support doing so please check the config value of and ensure it points to a filesystem mount that has suitable capabilities for output streams");
}
}
}

};