//,temp,sample_525.java,2,17,temp,sample_1777.java,2,16
//,3
public class xxx {
public static AsyncWriter createAsyncWriter(Configuration conf, FileSystem fs, Path path, boolean overwritable, EventLoopGroup eventLoopGroup, Class<? extends Channel> channelClass) throws IOException {
Class<? extends AsyncWriter> logWriterClass = conf.getClass( "hbase.regionserver.hlog.async.writer.impl", AsyncProtobufLogWriter.class, AsyncWriter.class);
try {
AsyncWriter writer = logWriterClass.getConstructor(EventLoopGroup.class, Class.class) .newInstance(eventLoopGroup, channelClass);
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