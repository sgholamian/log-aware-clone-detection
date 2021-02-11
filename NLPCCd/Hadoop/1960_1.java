//,temp,sample_6921.java,2,13,temp,sample_436.java,2,10
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