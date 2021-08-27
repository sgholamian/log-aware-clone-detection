//,temp,sample_7397.java,2,13,temp,sample_7392.java,2,13
//,2
public class xxx {
protected void doCreateBranch(Exchange exchange, String operation) throws Exception {
if (ObjectHelper.isEmpty(endpoint.getBranchName())) {
throw new IllegalArgumentException("Branch Name must be specified to execute " + operation);
}
try {
git.branchCreate().setName(endpoint.getBranchName()).call();
} catch (Exception e) {


log.info("there was an error in git operation");
}
}

};