//,temp,LevelDBAggregationRepository.java,420,452,temp,JdbcAggregationRepository.java,629,651
//,3
public class xxx {
    @Override
    protected void doStart() throws Exception {
        // either we have a LevelDB configured or we use a provided fileName
        if (levelDBFile == null && persistentFileName != null) {
            levelDBFile = new LevelDBFile();
            levelDBFile.setSync(isSync());
            levelDBFile.setFileName(persistentFileName);
        }

        ObjectHelper.notNull(levelDBFile, "Either set a persistentFileName or a levelDBFile");
        ObjectHelper.notNull(repositoryName, "repositoryName");

        ServiceHelper.startService(levelDBFile);

        // log number of existing exchanges
        int current = size(getRepositoryName());
        int completed = size(getRepositoryNameCompleted());

        if (current > 0) {
            LOG.info("On startup there are {} aggregate exchanges (not completed) in repository: {}",
                    current, getRepositoryName());
        } else {
            LOG.info("On startup there are no existing aggregate exchanges (not completed) in repository: {}",
                    getRepositoryName());
        }
        if (completed > 0) {
            LOG.warn("On startup there are {} completed exchanges to be recovered in repository: {}",
                    completed, getRepositoryNameCompleted());
        } else {
            LOG.info("On startup there are no completed exchanges to be recovered in repository: {}",
                    getRepositoryNameCompleted());
        }
    }

};