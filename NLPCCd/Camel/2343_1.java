//,temp,sample_5242.java,2,12,temp,sample_5241.java,2,12
//,2
public class xxx {
public void testException() throws Exception {
sendMessage();
assertTrue("Exchange did not complete.", doneLatch.await(5, TimeUnit.SECONDS));
assertNull("Should not have completed", completed);
assertNotNull("Should have received failed notification", failed);
assertEquals("Should have propagated the header inside the Synchronization.onFailure() callback", "bat", baz);
assertNull("The Synchronization.onComplete() callback should have not been invoked", foo);


log.info("received fail");
}

};