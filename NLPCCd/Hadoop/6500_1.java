//,temp,sample_8691.java,2,11,temp,sample_8854.java,2,11
//,3
public class xxx {
private static void customShuffleTransferCornerCases( FadvisedFileRegion fileRegion, WritableByteChannel target, int count) {
try {
fileRegion.customShuffleTransfer(target, -1);
Assert.fail("Expected a IllegalArgumentException");
} catch (IllegalArgumentException ie) {


log.info("expected illegal argument is passed");
}
}

};