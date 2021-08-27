//,temp,SplunkDataReader.java,247,259,temp,SplunkDataReader.java,234,245
//,3
public class xxx {
    private List<SplunkEvent> nonBlockingSearch(SplunkResultProcessor callback) throws Exception {
        LOG.debug("non block search start");

        JobArgs queryArgs = new JobArgs();
        queryArgs.setExecutionMode(ExecutionMode.NORMAL);
        Calendar startTime = Calendar.getInstance();
        populateArgs(queryArgs, startTime, false);

        List<SplunkEvent> data = runQuery(queryArgs, false, callback);
        lastSuccessfulReadTime = startTime;
        return data;
    }

};