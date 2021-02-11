//,temp,sample_987.java,2,11,temp,sample_8854.java,2,11
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