//,temp,AMQ5266StarvedConsumerTest.java,536,592,temp,AMQ5266Test.java,500,552
//,3
public class xxx {
            @Override
            public void run() {

                try {

                    int count = 0;

                    // Keep reading as long as it hasn't been told to shutdown
                    while (!shutdown) {

                        if (idList.size() >= totalToExpect) {
                            LOG.info("Got {} for q: {}", +idList.size(), qName);
                            session.commit();
                            break;
                        }
                        Message m = mc.receive(4000);

                        if (m != null) {

                            // We received a non-null message, add the ID to our list

                            idList.add(m.getStringProperty("KEY"));

                            count++;

                            // If we've reached our batch size, commit the batch and reset the count

                            if (count == batchSize) {
                                session.commit();
                                count = 0;
                            }
                        } else {

                            // We didn't receive anything this time, commit any current batch and reset the count

                            session.commit();
                            count = 0;

                            try {
                                if (idList.size() < totalToExpect) {
                                    LOG.info("did not receive on {}, current count: {}", qName, idList.size());
                                }
                            } catch (Exception e) {
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    // Once we exit, close everything
                    close();
                }
            }

};