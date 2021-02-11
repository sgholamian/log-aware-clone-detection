//,temp,sample_884.java,2,13,temp,sample_885.java,2,17
//,3
public class xxx {
public void dummy_method(){
String controllerPath = getControllerPath(controller);
if (controllerPath == null) {
throw new ResourceHandlerException( String.format("Controller %s not mounted." + " You either need to mount it with %s" + " or mount cgroups before launching Yarn", controller.getName(), YarnConfiguration. NM_LINUX_CONTAINER_CGROUPS_MOUNT));
}
File rootHierarchy = new File(controllerPath);
File yarnHierarchy = new File(rootHierarchy, cGroupPrefix);
String subsystemName = controller.getName();
if (!rootHierarchy.exists()) {
throw new ResourceHandlerException(getErrorWithDetails( "Cgroups mount point does not exist or not accessible", subsystemName, rootHierarchy.getAbsolutePath() ));
} else if (!yarnHierarchy.exists()) {


log.info("yarn control group does not exist creating");
}
}

};