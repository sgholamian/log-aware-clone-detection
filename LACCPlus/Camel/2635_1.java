//,temp,UnitOfWorkTest.java,54,65,temp,UnitOfWorkTest.java,41,52
//,2
public class xxx {
    @Test
    public void testException() throws Exception {
        sendMessage();

        assertTrue(doneLatch.await(5, TimeUnit.SECONDS), "Exchange did not complete.");
        assertNull(completed, "Should not have completed");
        assertNotNull(failed, "Should have received failed notification");
        assertEquals("bat", baz, "Should have propagated the header inside the Synchronization.onFailure() callback");
        assertNull(foo, "The Synchronization.onComplete() callback should have not been invoked");

        log.info("Received fail: " + failed);
    }

};