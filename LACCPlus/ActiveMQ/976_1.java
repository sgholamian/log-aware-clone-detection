//,temp,NetworkOfTwentyBrokersTest.java,182,197,temp,AMQ4485NetworkOfXBrokersWithNDestsFanoutTransactionTest.java,310,333
//,3
public class xxx {
    private void verifyPeerBrokerInfo(BrokerItem brokerItem, final int max) throws Exception {
        final BrokerService broker = brokerItem.broker;
        final RegionBroker regionBroker = (RegionBroker) broker.getRegionBroker();
        Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.info("verify infos " + broker.getBrokerName() + ", len: " + regionBroker.getPeerBrokerInfos().length);
                return max == regionBroker.getPeerBrokerInfos().length;
            }
         }, 120 * 1000);
        LOG.info("verify infos " + broker.getBrokerName() + ", len: " + regionBroker.getPeerBrokerInfos().length);
        for (BrokerInfo info : regionBroker.getPeerBrokerInfos()) {
            LOG.info(info.getBrokerName());
        }
        assertEquals(broker.getBrokerName(), max, regionBroker.getPeerBrokerInfos().length);
    }

};