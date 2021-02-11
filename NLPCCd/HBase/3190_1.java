//,temp,sample_2331.java,2,13,temp,sample_2332.java,2,17
//,3
public class xxx {
public void testEmptyResultIsReadonly() {
Result emptyResult = Result.EMPTY_RESULT;
Result otherResult = new Result();
try {
emptyResult.copyFrom(otherResult);
fail("UnsupportedOperationException should have been thrown!");
} catch (UnsupportedOperationException ex) {


log.info("as expected");
}
}

};