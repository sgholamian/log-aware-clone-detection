//,temp,sample_68.java,2,17,temp,sample_6473.java,2,17
//,3
public class xxx {
public void dummy_method(){
Set<Artifact> relevantDependencies;
if (this.includePluginDependencies) {
if (this.executableDependency == null) {
relevantDependencies = new HashSet<Artifact>(this.pluginDependencies);
} else {
Artifact executableArtifact = this.findExecutableArtifact();
Artifact executablePomArtifact = this.getExecutablePomArtifact(executableArtifact);
relevantDependencies = this.resolveExecutableDependencies(executablePomArtifact, false);
}
} else {


log.info("only direct plugin dependencies will be included");
}
}

};