//,temp,sample_2508.java,2,11,temp,sample_765.java,2,11
//,2
public class xxx {
private void closeStream() {
if (verbose) System.out.println("[" + this + "] INFO: Closing stream.");
try {
is.close();
} catch (IOException e) {


log.info("ignored io error on input stream");
}
}

};