//,temp,TestDbNotificationListener.java,1679,1705,temp,TestDbNotificationListener.java,1659,1677
//,3
public class xxx {
  @Test
  public void cleanupNotificationWithError() throws Exception {
    Database db = new Database("cleanup1", "no description", testTempDir, emptyParameters);
    msClient.createDatabase(db);
    msClient.dropDatabase("cleanup1");

    LOG.info("Pulling events immediately after createDatabase/dropDatabase");
    NotificationEventResponse rsp = msClient.getNextNotification(firstEventId, 0, null);
    assertEquals(2, rsp.getEventsSize());
    //this simulates that cleaning thread will error out while cleaning the notifications
    DummyRawStoreFailEvent.setEventSucceed(false);
    // sleep for expiry time, and then fetch again
    // sleep twice the TTL interval - things should have been cleaned by then.
    Thread.sleep(EVENTS_TTL * 2 * 1000);

    LOG.info("Pulling events again after failing to cleanup");
    NotificationEventResponse rsp2 = msClient.getNextNotification(firstEventId, 0, null);
    LOG.info("second trigger done");
    assertEquals(2, rsp2.getEventsSize());
    DummyRawStoreFailEvent.setEventSucceed(true);
    Thread.sleep(EVENTS_TTL * 2 * 1000);

    LOG.info("Pulling events again after cleanup");
    rsp2 = msClient.getNextNotification(firstEventId, 0, null);
    LOG.info("third trigger done");
    assertEquals(0, rsp2.getEventsSize());
  }

};