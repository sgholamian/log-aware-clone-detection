//,temp,HMaster.java,2067,2084,temp,HMaster.java,1915,1926
//,3
public class xxx {
      @Override
      protected void run() throws IOException {
        getMaster().getMasterCoprocessorHost().preMergeRegions(regionsToMerge);

        LOG.info(getClientIdAuditPrefix() + " Merge regions " +
          regionsToMerge[0].getEncodedName() + " and " + regionsToMerge[1].getEncodedName());

        submitProcedure(new MergeTableRegionsProcedure(procedureExecutor.getEnvironment(),
          regionsToMerge, forcible));

        getMaster().getMasterCoprocessorHost().postMergeRegions(regionsToMerge);
      }

};