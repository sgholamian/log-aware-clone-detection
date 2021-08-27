//,temp,MessageGroupLateArrivalsTest.java,100,163,temp,MessageGroupDelayedTest.java,111,162
//,3
public class xxx {
    public void testDelayedDirectConnectionListener() throws Exception {

        for (int i = 0; i < 10; i++) {
            Message msga = session.createTextMessage("hello a");
            msga.setStringProperty("JMSXGroupID", "A");
            producer.send(msga);
            Message msgb = session.createTextMessage("hello b");
            msgb.setStringProperty("JMSXGroupID", "B");
            producer.send(msgb);
            Message msgc = session.createTextMessage("hello c");
            msgc.setStringProperty("JMSXGroupID", "C");
            producer.send(msgc);
        }
        log.info("30 messages sent to group A/B/C");

        int[] counters = { 10, 10, 10 };

        CountDownLatch startSignal = new CountDownLatch(1);
        CountDownLatch doneSignal = new CountDownLatch(1);

        messageCount.put("worker1", 0);
        messageGroups.put("worker1", new HashSet<String>());
        Worker worker1 = new Worker(connection, destination, "worker1", startSignal, doneSignal, counters, messageCount, messageGroups);
        messageCount.put("worker2", 0);
        messageGroups.put("worker2", new HashSet<String>());
        Worker worker2 = new Worker(connection, destination, "worker2", startSignal, doneSignal, counters, messageCount, messageGroups);
        messageCount.put("worker3", 0);
        messageGroups.put("worker3", new HashSet<String>());
        Worker worker3 = new Worker(connection, destination, "worker3", startSignal, doneSignal, counters, messageCount, messageGroups);

        new Thread(worker1).start();
        new Thread(worker2).start();
        new Thread(worker3).start();

        startSignal.countDown();
        doneSignal.await();

        // check results
        if (consumersBeforeDispatchStarts == 0 && timeBeforeDispatchStarts == 0) {
            log.info("Ignoring results because both parameters are 0");
            return;
        }

        for (String worker : messageCount.keySet()) {
            log.info("worker " + worker + " received " + messageCount.get(worker) + " messages from groups " + messageGroups.get(worker));
            assertEquals("worker " + worker + " received " + messageCount.get(worker) + " messages from groups " + messageGroups.get(worker), 10, messageCount
                .get(worker).intValue());
            assertEquals("worker " + worker + " received " + messageCount.get(worker) + " messages from groups " + messageGroups.get(worker), 1, messageGroups
                .get(worker).size());
        }

    }

};