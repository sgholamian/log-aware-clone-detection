//,temp,DurableSubProcessConcurrentCommitActivateNoDuplicateTest.java,760,782,temp,DurableSubDelayedUnsubscribeTest.java,642,661
//,2
public class xxx {
        private void sweep() throws Exception {
            LOG.info("Housekeeper sweeping.");

            int closed = 0;
            ArrayList<String> sweeped = new ArrayList<String>();
            try {
                for (String clientId : abandonedSubscriptions) {
                    LOG.info("Sweeping out subscription of " + clientId + ".");
                    broker.getAdminView().destroyDurableSubscriber(clientId, Client.SUBSCRIPTION_NAME);
                    sweeped.add(clientId);
                    closed++;
                }
            } catch (Exception ignored) {
                LOG.info("Ex on destroy sub " + ignored);
            } finally {
                abandonedSubscriptions.removeAll(sweeped);
            }

            LOG.info("Housekeeper sweeped out " + closed + " subscriptions.");
        }

};