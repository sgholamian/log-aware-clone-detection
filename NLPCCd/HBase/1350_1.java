//,temp,sample_4270.java,2,12,temp,sample_2259.java,2,14
//,3
public class xxx {
public void run() {
try {
LOG.info("Hook closing fs=" + this.fs);
this.fs.close();
} catch (NullPointerException npe) {
} catch (IOException e) {


log.info("running hook");
}
}

};