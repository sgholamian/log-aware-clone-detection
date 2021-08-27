//,temp,AMQ5486Test.java,128,132,temp,VirtualTopicWildcardTest.java,126,130
//,3
public class xxx {
                    @Override
                    public boolean isSatisified() throws Exception {
                        LOG.info("Exceptions size: " + exceptions.size() + ", connections size: " + connector.getConnections().size());
                        return connector.getConnections().size()  + exceptions.size() == maxConnections;
                    }

};