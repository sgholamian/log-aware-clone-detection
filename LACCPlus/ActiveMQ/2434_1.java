//,temp,MessageGroupLateArrivalsTest.java,100,163,temp,MessageGroupDelayedTest.java,111,162
//,3
public class xxx {
    @Test(timeout = 30 * 1000)
    public void testConsumersLateToThePartyGetSomeNewGroups() throws Exception {

        final int perBatch = 3;
        int[] counters = {perBatch, perBatch, perBatch};

        CountDownLatch startSignal = new CountDownLatch(0);
        CountDownLatch doneSignal = new CountDownLatch(3);
        CountDownLatch worker1Started = new CountDownLatch(1);
        CountDownLatch worker2Started = new CountDownLatch(1);
        CountDownLatch worker3Started = new CountDownLatch(1);

        messageCount.put("worker1", 0);
        messageGroups.put("worker1", new HashSet<String>());
        Worker worker1 = new Worker(connection, destination, "worker1", startSignal, doneSignal, counters, messageCount, messageGroups, worker1Started);
        messageCount.put("worker2", 0);
        messageGroups.put("worker2", new HashSet<String>());
        Worker worker2 = new Worker(connection, destination, "worker2", startSignal, doneSignal, counters, messageCount, messageGroups, worker2Started);
        messageCount.put("worker3", 0);
        messageGroups.put("worker3", new HashSet<String>());
        Worker worker3 = new Worker(connection, destination, "worker3", startSignal, doneSignal, counters, messageCount, messageGroups, worker3Started);

        new Thread(worker1).start();
        new Thread(worker2).start();
        worker1Started.await();
        worker2Started.await();

        for (int i = 0; i < perBatch; i++) {
            Message msga = session.createTextMessage("hello a");
            msga.setStringProperty("JMSXGroupID", "A");
            producer.send(msga);

            Message msgb = session.createTextMessage("hello b");
            msgb.setStringProperty("JMSXGroupID", "B");
            producer.send(msgb);
        }

        // ensure this chap, late to the party gets a new group
        new Thread(worker3).start();

        // wait for presence before new group
        worker3Started.await();

        for (int i = 0; i < perBatch; i++) {
            Message msgc = session.createTextMessage("hello c");
            msgc.setStringProperty("JMSXGroupID", "C");
            producer.send(msgc);
        }

        doneSignal.await();

        List<String> workers = new ArrayList<String>(messageCount.keySet());
        Collections.sort(workers);
        for (String worker : workers) {
            log.info("worker " + worker + " received " + messageCount.get(worker) + " messages from groups " + messageGroups.get(worker));
        }

        for (String worker : workers) {
            assertEquals("worker " + worker + " received " + messageCount.get(worker) + " messages from groups " + messageGroups.get(worker)
                    , perBatch, messageCount.get(worker).intValue());
            assertEquals("worker " + worker + " received " + messageCount.get(worker) + " messages from groups " + messageGroups.get(worker)
                    , 1, messageGroups.get(worker).size());
        }
    }

};