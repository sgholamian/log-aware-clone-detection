//,temp,MessageGroupLateArrivalsTest.java,254,301,temp,MessageGroupDelayedTest.java,194,241
//,3
public class xxx {
        @Override
        public void run() {

            try {
                log.info(workerName);
                startSignal.await();
                Session sess = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
                MessageConsumer consumer = sess.createConsumer(queueName);

                while (true) {
                    if (counters[0] == 0 && counters[1] == 0 && counters[2] == 0) {
                        doneSignal.countDown();
                        log.info(workerName + " done...");
                        break;
                    }

                    Message msg = consumer.receive(500);
                    if (msg == null)
                        continue;

                    String group = msg.getStringProperty("JMSXGroupID");
                    msg.getBooleanProperty("JMSXGroupFirstForConsumer");

                    if ("A".equals(group)) {
                        --counters[0];
                        update(group);
                        Thread.sleep(500);
                    } else if ("B".equals(group)) {
                        --counters[1];
                        update(group);
                        Thread.sleep(100);
                    } else if ("C".equals(group)) {
                        --counters[2];
                        update(group);
                        Thread.sleep(10);
                    } else {
                        log.warn("unknown group");
                    }
                    if (counters[0] != 0 || counters[1] != 0 || counters[2] != 0) {
                        msg.acknowledge();
                    }
                }
                consumer.close();
                sess.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

};