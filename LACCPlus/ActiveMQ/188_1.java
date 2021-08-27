//,temp,LoggingBrokerPlugin.java,409,425,temp,LoggingBrokerPlugin.java,306,323
//,3
public class xxx {
    @Override
    public BrokerInfo[] getPeerBrokerInfos() {
        BrokerInfo[] result = super.getPeerBrokerInfos();
        if (isLogAll() || isLogInternalEvents()) {
            if (result == null) {
                LOG.info("Get Peer Broker Infos returned empty list.");
            } else {
                StringBuffer peers = new StringBuffer();
                for (BrokerInfo bi : result) {
                    peers.append(peers.length() > 0 ? ", " : "");
                    peers.append(bi.getBrokerName());
                }
                LOG.info("Get Peer Broker Infos: {}", peers);
            }
        }
        return result;
    }

};