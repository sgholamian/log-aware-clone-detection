//,temp,sample_5553.java,2,15,temp,sample_5552.java,2,15
//,2
public class xxx {
public void testStartAfterTrigger() throws InterruptedException {
final long time = 10;
ForeignExceptionListener listener = Mockito.mock(ForeignExceptionListener.class);
TimeoutExceptionInjector timer = new TimeoutExceptionInjector(listener, time);
timer.trigger();
try {
timer.start();
fail("Timer should fail to start after complete.");
} catch (IllegalStateException e) {


log.info("correctly failed timer");
}
}

};