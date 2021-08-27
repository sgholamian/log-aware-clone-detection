//,temp,sample_6223.java,2,15,temp,sample_6224.java,2,15
//,3
public class xxx {
public void testDefaultTimeoutMapPurge() throws Exception {
DefaultTimeoutMap<String, Integer> map = new DefaultTimeoutMap<String, Integer>(executor, 100);
map.start();
assertTrue(map.currentTime() > 0);
assertEquals(0, map.size());
map.put("A", 123, 50);
assertEquals(1, map.size());
Thread.sleep(250);
if (map.size() > 0) {


log.info("waiting extra due slow ci box");
}
}

};