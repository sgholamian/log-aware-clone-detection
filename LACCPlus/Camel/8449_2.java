//,temp,DefaultTimeoutMapTest.java,112,135,temp,DefaultTimeoutMapTest.java,50,70
//,3
public class xxx {
    @Test
    public void testDefaultTimeoutMapPurge() throws Exception {
        DefaultTimeoutMap<String, Integer> map = new DefaultTimeoutMap<>(executor, 100);
        map.start();
        assertTrue(map.currentTime() > 0);

        assertEquals(0, map.size());

        map.put("A", 123, 50);
        assertEquals(1, map.size());

        Thread.sleep(250);
        if (map.size() > 0) {
            LOG.warn("Waiting extra due slow CI box");
            Thread.sleep(1000);
        }

        assertEquals(0, map.size());

        map.stop();
    }

};