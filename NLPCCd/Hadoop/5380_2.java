//,temp,sample_5618.java,2,14,temp,sample_5616.java,2,14
//,2
public class xxx {
public void testDestinationParam() {
final DestinationParam p = new DestinationParam(DestinationParam.DEFAULT);
Assert.assertEquals(null, p.getValue());
new DestinationParam("/abc");
try {
new DestinationParam("abc");
Assert.fail();
} catch(IllegalArgumentException e) {


log.info("expected");
}
}

};