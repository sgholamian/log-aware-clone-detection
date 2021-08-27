//,temp,sample_7390.java,2,17,temp,sample_7391.java,2,17
//,3
public class xxx {
public void dummy_method(){
try {
if (ObjectHelper.isNotEmpty(endpoint.getBranchName())) {
git.checkout().setCreateBranch(false).setName(endpoint.getBranchName()).call();
}
if (ObjectHelper.isNotEmpty(username) && ObjectHelper.isNotEmpty(email)) {
git.commit().setAllowEmpty(allowEmpty).setCommitter(username, email).setMessage(commitMessage).call();
} else {
git.commit().setAllowEmpty(allowEmpty).setMessage(commitMessage).call();
}
} catch (Exception e) {


log.info("there was an error in git operation");
}
}

};