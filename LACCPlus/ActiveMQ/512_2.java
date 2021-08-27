//,temp,AMQ2584Test.java,121,126,temp,AMQ2870Test.java,92,100
//,2
public class xxx {
                    @Override
                    public boolean isSatisified() throws Exception {
                        // usage percent updated only on send check for isFull so once
                        // sends complete it is no longer updated till next send via a call to isFull
                        // this is optimal as it is only used to block producers
                        broker.getSystemUsage().getStoreUsage().isFull();
                        LOG.info("store percent usage: "+brokerView.getStorePercentUsage());
                        return broker.getAdminView().getStorePercentUsage() < minPercentUsageForStore;
                    }

};