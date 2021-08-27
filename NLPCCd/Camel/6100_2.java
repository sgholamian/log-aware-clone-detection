//,temp,sample_6223.java,2,15,temp,sample_6224.java,2,15
//,3
public class xxx {
public void testExecutor() throws Exception {
ScheduledExecutorService e = Executors.newScheduledThreadPool(2);
DefaultTimeoutMap<String, Integer> map = new DefaultTimeoutMap<String, Integer>(e, 50);
map.start();
assertEquals(50, map.getPurgePollTime());
map.put("A", 123, 100);
assertEquals(1, map.size());
Thread.sleep(250);
if (map.size() > 0) {


log.info("waiting extra due slow ci box");
}
}

};