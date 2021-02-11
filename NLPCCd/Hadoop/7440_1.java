//,temp,sample_6921.java,2,13,temp,sample_5053.java,2,12
//,3
public class xxx {
public void tearDown() throws Exception {
if (fc != null) {
final Path testRoot = fileContextTestHelper.getAbsoluteTestRootPath(fc);
try {
fc.delete(testRoot, true);
} catch (Exception e) {


log.info("error when deleting test root path");
}
}
}

};