//,temp,sample_5618.java,2,14,temp,sample_5613.java,2,14
//,3
public class xxx {
public void testAccessTimeParam() {
final AccessTimeParam p = new AccessTimeParam(AccessTimeParam.DEFAULT);
Assert.assertEquals(-1L, p.getValue().longValue());
new AccessTimeParam(-1L);
try {
new AccessTimeParam(-2L);
Assert.fail();
} catch(IllegalArgumentException e) {


log.info("expected");
}
}

};