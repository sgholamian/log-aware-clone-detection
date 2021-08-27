//,temp,GitProducer.java,530,540,temp,GitProducer.java,518,528
//,2
public class xxx {
    protected void doDeleteTag(Exchange exchange, String operation) throws Exception {
        if (ObjectHelper.isEmpty(endpoint.getTagName())) {
            throw new IllegalArgumentException("Tag Name must be specified to execute " + operation);
        }
        try {
            git.tagDelete().setTags(endpoint.getTagName()).call();
        } catch (Exception e) {
            LOG.error("There was an error in Git {} operation", operation);
            throw e;
        }
    }

};