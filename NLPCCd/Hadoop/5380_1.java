//,temp,sample_5618.java,2,14,temp,sample_5616.java,2,14
//,2
public class xxx {
public void testOverwriteParam() {
final OverwriteParam p = new OverwriteParam(OverwriteParam.DEFAULT);
Assert.assertEquals(false, p.getValue());
new OverwriteParam("trUe");
try {
new OverwriteParam("abc");
Assert.fail();
} catch(IllegalArgumentException e) {


log.info("expected");
}
}

};