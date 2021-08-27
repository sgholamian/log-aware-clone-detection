//,temp,sample_7480.java,2,14,temp,sample_8133.java,2,14
//,2
public class xxx {
public void onNewArtifacts(Set<NexusArtifactDto> newArtifacts) {
for (NexusArtifactDto dto : newArtifacts) {
try {
String url = createArtifactURL(dto);
URL jarUrl = new URL(url);
addCustomCamelComponentsFromArtifact(dto, jarUrl);
} catch (Throwable e) {


log.info("error downloading component jar this exception is ignored");
}
}
}

};