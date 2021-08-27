//,temp,SplunkDataReader.java,247,259,temp,SplunkDataReader.java,234,245
//,3
public class xxx {
    private List<SplunkEvent> realtimeSearch(SplunkResultProcessor callback) throws Exception {
        LOG.debug("realtime search start");

        JobArgs queryArgs = new JobArgs();
        queryArgs.setExecutionMode(ExecutionMode.NORMAL);
        queryArgs.setSearchMode(SearchMode.REALTIME);
        Calendar startTime = Calendar.getInstance();
        populateArgs(queryArgs, startTime, true);

        List<SplunkEvent> data = runQuery(queryArgs, true, callback);
        lastSuccessfulReadTime = startTime;
        return data;
    }

};