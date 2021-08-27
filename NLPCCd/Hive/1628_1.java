//,temp,sample_3260.java,2,12,temp,sample_3261.java,2,13
//,3
public class xxx {
public void cleanupNotifs() throws Exception {
Database db = new Database("cleanup1", "no description", "file:/tmp", emptyParameters);
msClient.createDatabase(db);
msClient.dropDatabase("cleanup1");
NotificationEventResponse rsp = msClient.getNextNotification(firstEventId, 0, null);
assertEquals(2, rsp.getEventsSize());
Thread.sleep(EVENTS_TTL * 2 * 1000);


log.info("pulling events again after cleanup");
}

};