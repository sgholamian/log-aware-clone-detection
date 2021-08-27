//,temp,sample_7397.java,2,13,temp,sample_7392.java,2,13
//,2
public class xxx {
protected void doCreateTag(Exchange exchange, String operation) throws Exception {
if (ObjectHelper.isEmpty(endpoint.getTagName())) {
throw new IllegalArgumentException("Tag Name must be specified to execute " + operation);
}
try {
git.tag().setName(endpoint.getTagName()).call();
} catch (Exception e) {


log.info("there was an error in git operation");
}
}

};