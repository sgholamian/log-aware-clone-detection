//,temp,sample_3002.java,2,10,temp,sample_5302.java,2,9
//,3
public class xxx {
protected final Set<InstanceType> getByHostInternal(String host) {
Set<InstanceType> byHost = nodeToInstanceCache.get(host);
byHost = (byHost == null) ? Sets.<InstanceType>newHashSet() : byHost;
if (LOG.isDebugEnabled()) {


log.info("returning hosts for locality allocation on");
}
}

};