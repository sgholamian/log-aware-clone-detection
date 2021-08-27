//,temp,DurableSubProcessWithRestartTest.java,665,687,temp,DurableSubProcessTest.java,556,574
//,3
public class xxx {
        private void sweep() throws Exception {
            LOG.info("Housekeeper sweeping.");

            int closed = 0;
            ArrayList<String> sweeped = new ArrayList<String>();
            try {
                for (String clientId: abandonedSubscriptions) {
                    sweeped.add(clientId);
                    LOG.info("Sweeping out subscription of " + clientId + ".");
                    broker.getAdminView().destroyDurableSubscriber(clientId, Client.SUBSCRIPTION_NAME);
                    closed++;
                }
            }
            finally {
                abandonedSubscriptions.removeAll(sweeped);
            }

            LOG.info("Housekeeper sweeped out " + closed + " subscriptions.");
        }

};