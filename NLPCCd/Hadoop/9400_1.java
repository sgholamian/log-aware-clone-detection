//,temp,sample_9381.java,2,15,temp,sample_9382.java,2,18
//,3
public class xxx {
protected void addCGroupParentIfRequired(String resourcesOptions, String containerIdStr, DockerRunCommand runCommand) {
if (cGroupsHandler == null) {
if (LOG.isDebugEnabled()) {
}
return;
}
if (resourcesOptions.equals(PrivilegedOperation.CGROUP_ARG_PREFIX + PrivilegedOperation.CGROUP_ARG_NO_TASKS)) {
if (LOG.isDebugEnabled()) {


log.info("no resource restrictions specified not using docker s cgroup options");
}
}
}

};