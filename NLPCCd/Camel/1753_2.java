//,temp,sample_67.java,2,13,temp,sample_6472.java,2,13
//,2
public class xxx {
private Set<Artifact> determineRelevantPluginDependencies() throws MojoExecutionException {
Set<Artifact> relevantDependencies;
if (this.includePluginDependencies) {
if (this.executableDependency == null) {
relevantDependencies = new HashSet<Artifact>(this.pluginDependencies);
} else {


log.info("selected plugin dependencies will be included");
}
}
}

};