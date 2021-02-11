//,temp,sample_4270.java,2,12,temp,sample_2259.java,2,14
//,3
public class xxx {
protected void tearDown() throws Exception {
try {
if (localfs) {
if (this.fs.exists(testDir)) {
this.fs.delete(testDir, true);
}
}
} catch (Exception e) {


log.info("error during tear down");
}
}

};