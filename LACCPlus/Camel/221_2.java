//,temp,AbstractGitConsumer.java,60,72,temp,GitProducer.java,644,659
//,2
public class xxx {
    private Repository getLocalRepository() throws IOException {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
        Repository repo = null;
        try {
            repo = builder.setGitDir(new File(endpoint.getLocalPath(), ".git")).readEnvironment() // scan
                    // environment
                    // GIT_*
                    // variables
                    .findGitDir() // scan up the file system tree
                    .build();
        } catch (IOException e) {
            LOG.error("There was an error, cannot open {} repository", endpoint.getLocalPath());
            throw e;
        }
        return repo;
    }

};