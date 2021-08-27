//,temp,DurableSubProcessWithRestartTest.java,665,687,temp,DurableSubProcessTest.java,556,574
//,3
public class xxx {
        private void sweep() throws Exception {
            LOG.info("Housekeeper sweeping.");

            int closed = 0;
            ArrayList<String> sweeped = new ArrayList<String>();
            try {
                for (String clientId : abandonedSubscriptions) {
                    LOG.info("Sweeping out subscription of "
                            + clientId + ".");
                    broker.getAdminView().destroyDurableSubscriber(clientId,
                            Client.SUBSCRIPTION_NAME);
                    sweeped.add(clientId);
                    closed++;
                }
            } catch (Exception ignored) {
                LOG.info("Ex on destroy sub " + ignored);
            } finally {
                abandonedSubscriptions.removeAll(sweeped);
            }

            LOG.info("Housekeeper sweeped out " + closed
                    + " subscriptions.");
        }

};