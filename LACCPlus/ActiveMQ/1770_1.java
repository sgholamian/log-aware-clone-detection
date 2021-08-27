//,temp,TransactionNotStartedErrorTest.java,102,212,temp,MissingDataFileTest.java,119,241
//,3
public class xxx {
    public void testTransactionNotStartedError() throws Exception {
        startBroker();
        hectorConnection = createConnection();
        Thread hectorThread = buildProducer(hectorConnection, hectorToHalo);
        Receiver hHectorReceiver = new Receiver() {
            public void receive(String s) throws Exception {
                haloToHectorCtr++;
                if (haloToHectorCtr >= counter) {
                    synchronized (lock) {
                        lock.notifyAll();
                    }
                }
            }
        };
        buildReceiver(hectorConnection, haloToHector, false, hHectorReceiver);

        troyConnection = createConnection();
        Thread troyThread = buildProducer(troyConnection, troyToHalo);
        Receiver hTroyReceiver = new Receiver() {
            public void receive(String s) throws Exception {
                haloToTroyCtr++;
                if (haloToTroyCtr >= counter) {
                    synchronized (lock) {
                        lock.notifyAll();
                    }
                }
            }
        };
        buildReceiver(hectorConnection, haloToTroy, false, hTroyReceiver);

        xenaConnection = createConnection();
        Thread xenaThread = buildProducer(xenaConnection, xenaToHalo);
        Receiver hXenaReceiver = new Receiver() {
            public void receive(String s) throws Exception {
                haloToXenaCtr++;
                if (haloToXenaCtr >= counter) {
                    synchronized (lock) {
                        lock.notifyAll();
                    }
                }
            }
        };
        buildReceiver(xenaConnection, haloToXena, false, hXenaReceiver);

        haloConnection = createConnection();
        final MessageSender hectorSender = buildTransactionalProducer(haloToHector, haloConnection);
        final MessageSender troySender = buildTransactionalProducer(haloToTroy, haloConnection);
        final MessageSender xenaSender = buildTransactionalProducer(haloToXena, haloConnection);
        Receiver hectorReceiver = new Receiver() {
            public void receive(String s) throws Exception {
                hectorToHaloCtr++;
                troySender.send("halo to troy because of hector");
                if (hectorToHaloCtr >= counter) {
                    synchronized (lock) {
                        lock.notifyAll();
                    }
                }
            }
        };
        Receiver xenaReceiver = new Receiver() {
            public void receive(String s) throws Exception {
                xenaToHaloCtr++;
                hectorSender.send("halo to hector because of xena");
                if (xenaToHaloCtr >= counter) {
                    synchronized (lock) {
                        lock.notifyAll();
                    }
                }
            }
        };
        Receiver troyReceiver = new Receiver() {
            public void receive(String s) throws Exception {
                troyToHaloCtr++;
                xenaSender.send("halo to xena because of troy");
                if (troyToHaloCtr >= counter) {
                    synchronized (lock) {
                        lock.notifyAll();
                    }
                }
            }
        };
        buildReceiver(haloConnection, hectorToHalo, true, hectorReceiver);
        buildReceiver(haloConnection, xenaToHalo, true, xenaReceiver);
        buildReceiver(haloConnection, troyToHalo, true, troyReceiver);

        haloConnection.start();

        troyConnection.start();
        troyThread.start();

        xenaConnection.start();
        xenaThread.start();

        hectorConnection.start();
        hectorThread.start();
        waitForMessagesToBeDelivered();
        // number of messages received should match messages sent
        assertEquals(hectorToHaloCtr, counter);
        LOG.info("hectorToHalo received " + hectorToHaloCtr + " messages");
        assertEquals(xenaToHaloCtr, counter);
        LOG.info("xenaToHalo received " + xenaToHaloCtr + " messages");
        assertEquals(troyToHaloCtr, counter);
        LOG.info("troyToHalo received " + troyToHaloCtr + " messages");
        assertEquals(haloToHectorCtr, counter);
        LOG.info("haloToHector received " + haloToHectorCtr + " messages");
        assertEquals(haloToXenaCtr, counter);
        LOG.info("haloToXena received " + haloToXenaCtr + " messages");
        assertEquals(haloToTroyCtr, counter);
        LOG.info("haloToTroy received " + haloToTroyCtr + " messages");

    }

};