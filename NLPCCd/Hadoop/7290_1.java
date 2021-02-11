//,temp,sample_987.java,2,11,temp,sample_5629.java,2,17
//,3
public class xxx {
private void shouldThrow(PrivilegedExceptionAction<Object> action, Class<? extends Throwable> except) {
try {
action.run();
Assert.fail("action did not throw " + except);
} catch (Throwable th) {


log.info("caught an exception");
}
}

};