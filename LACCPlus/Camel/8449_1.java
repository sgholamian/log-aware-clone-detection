//,temp,DefaultTimeoutMapTest.java,112,135,temp,DefaultTimeoutMapTest.java,50,70
//,3
public class xxx {
    @Test
    public void testExecutor() throws Exception {
        ScheduledExecutorService e = Executors.newScheduledThreadPool(2);

        DefaultTimeoutMap<String, Integer> map = new DefaultTimeoutMap<>(e, 50);
        map.start();
        assertEquals(50, map.getPurgePollTime());

        map.put("A", 123, 100);
        assertEquals(1, map.size());

        Thread.sleep(250);

        if (map.size() > 0) {
            LOG.warn("Waiting extra due slow CI box");
            Thread.sleep(1000);
        }
        // should have been timed out now
        assertEquals(0, map.size());

        assertSame(e, map.getExecutor());

        map.stop();
    }

};