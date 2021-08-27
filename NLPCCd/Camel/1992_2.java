//,temp,sample_7403.java,2,12,temp,sample_6350.java,2,12
//,2
public class xxx {
private Repository getLocalRepository() throws IOException {
FileRepositoryBuilder builder = new FileRepositoryBuilder();
Repository repo = null;
try {
repo = builder.setGitDir(new File(endpoint.getLocalPath(), ".git")).readEnvironment() .findGitDir() .build();
} catch (IOException e) {


log.info("there was an error cannot open repository");
}
}

};