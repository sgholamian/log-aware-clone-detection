//,temp,AMQ2584Test.java,121,126,temp,AMQ2870Test.java,92,100
//,2
public class xxx {
                    @Override
                    public boolean isSatisified() throws Exception {
                        broker.getSystemUsage().getStoreUsage().isFull();
                        LOG.info("store precent usage: "+brokerView.getStorePercentUsage());
                        return broker.getAdminView().getStorePercentUsage() < minPercentUsageForStore;
                    }

};