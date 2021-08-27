//,temp,sample_1299.java,2,11,temp,sample_1300.java,2,14
//,3
public class xxx {
private void writeExitValue(Configuration conf, int exitValue, String statusdir) throws IOException {
if (TempletonUtils.isset(statusdir)) {
Path p = new Path(statusdir, EXIT_FNAME);
FileSystem fs = p.getFileSystem(conf);
OutputStream out = fs.create(p);
PrintWriter writer = new PrintWriter(out);
writer.println(exitValue);
writer.close();


log.info("templeton exit value successfully written");
}
}

};