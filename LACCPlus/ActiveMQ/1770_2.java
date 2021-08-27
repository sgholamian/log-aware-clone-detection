//,temp,TransactionNotStartedErrorTest.java,102,212,temp,MissingDataFileTest.java,119,241
//,3
public class xxx {
    public void testForNoDataFoundError() throws Exception {

        startBroker();
        hectorConnection = createConnection();
        Thread hectorThread = buildProducer(hectorConnection, hectorToHalo, false, useTopic);
        Receiver hHectorReceiver = new Receiver() {
            @Override
            public void receive(String s) throws Exception {
                haloToHectorCtr++;
                if (haloToHectorCtr >= counter) {
                    synchronized (lock) {
                        lock.notifyAll();
                    }
                }
                possiblySleep(haloToHectorCtr);
            }
        };
        buildReceiver(hectorConnection, haloToHector, false, hHectorReceiver, useTopic);

        troyConnection = createConnection();
        Thread troyThread = buildProducer(troyConnection, troyToHalo);
        Receiver hTroyReceiver = new Receiver() {
            @Override
            public void receive(String s) throws Exception {
                haloToTroyCtr++;
                if (haloToTroyCtr >= counter) {
                    synchronized (lock) {
                        lock.notifyAll();
                    }
                }
                possiblySleep(haloToTroyCtr);
            }
        };
        buildReceiver(hectorConnection, haloToTroy, false, hTroyReceiver, false);

        xenaConnection = createConnection();
        Thread xenaThread = buildProducer(xenaConnection, xenaToHalo);
        Receiver hXenaReceiver = new Receiver() {
            @Override
            public void receive(String s) throws Exception {
                haloToXenaCtr++;
                if (haloToXenaCtr >= counter) {
                    synchronized (lock) {
                        lock.notifyAll();
                    }
                }
                possiblySleep(haloToXenaCtr);
            }
        };
        buildReceiver(xenaConnection, haloToXena, false, hXenaReceiver, false);

        haloConnection = createConnection();
        final MessageSender hectorSender = buildTransactionalProducer(haloToHector, haloConnection, false);
        final MessageSender troySender = buildTransactionalProducer(haloToTroy, haloConnection, false);
        final MessageSender xenaSender = buildTransactionalProducer(haloToXena, haloConnection, false);
        Receiver hectorReceiver = new Receiver() {
            @Override
            public void receive(String s) throws Exception {
                hectorToHaloCtr++;
                troySender.send(payload);
                if (hectorToHaloCtr >= counter) {
                    synchronized (lock) {
                        lock.notifyAll();
                    }
                    possiblySleep(hectorToHaloCtr);
                }
            }
        };
        Receiver xenaReceiver = new Receiver() {
            @Override
            public void receive(String s) throws Exception {
                xenaToHaloCtr++;
                hectorSender.send(payload);
                if (xenaToHaloCtr >= counter) {
                    synchronized (lock) {
                        lock.notifyAll();
                    }
                }
                possiblySleep(xenaToHaloCtr);
            }
        };
        Receiver troyReceiver = new Receiver() {
            @Override
            public void receive(String s) throws Exception {
                troyToHaloCtr++;
                xenaSender.send(payload);
                if (troyToHaloCtr >= counter) {
                    synchronized (lock) {
                        lock.notifyAll();
                    }
                }
            }
        };
        buildReceiver(haloConnection, hectorToHalo, true, hectorReceiver, false);
        buildReceiver(haloConnection, xenaToHalo, true, xenaReceiver, false);
        buildReceiver(haloConnection, troyToHalo, true, troyReceiver, false);

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