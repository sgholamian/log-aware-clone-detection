//,temp,sample_68.java,2,17,temp,sample_6473.java,2,17
//,3
public class xxx {
public void dummy_method(){
if (this.includePluginDependencies) {
if (this.executableDependency == null) {
relevantDependencies = new HashSet<Artifact>(this.pluginDependencies);
} else {
Artifact executableArtifact = this.findExecutableArtifact();
Artifact executablePomArtifact = this.getExecutablePomArtifact(executableArtifact);
relevantDependencies = this.resolveExecutableDependencies(executablePomArtifact);
}
} else {
relevantDependencies = Collections.emptySet();


log.info("plugin dependencies will be excluded");
}
}

};