//,temp,sample_1090.java,2,12,temp,sample_1093.java,2,13
//,3
public class xxx {
public static long getDefaultBlockSize(final FileSystem fs, final Path path) throws IOException {
Method m = null;
Class<? extends FileSystem> cls = fs.getClass();
try {
m = cls.getMethod("getDefaultBlockSize", new Class<?>[] { Path.class });
} catch (NoSuchMethodException e) {


log.info("filesystem doesn t support getdefaultblocksize");
}
}

};