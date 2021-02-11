//,temp,sample_525.java,2,17,temp,sample_1777.java,2,16
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
} else {


log.info("error instantiating log writer");
}
}
}

};