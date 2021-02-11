//,temp,ElastistorUtil.java,2563,2576,temp,ElastistorUtil.java,2487,2507
//,3
public class xxx {
     public static UpdateTsmStorageCmdResponse updateElastistorTsmStorage(String capacityBytes,String uuid) throws Throwable{

         Long size = (Long.parseLong(capacityBytes)/(1024 * 1024 * 1024));

         String quotasize = null;

         if(size > 1024){
            quotasize = (String.valueOf(Long.parseLong(capacityBytes)/(1024)) + "T");
         }else{
            quotasize = String.valueOf(quotasize) + "G";
         }
         s_logger.info("elastistor tsm storage is updating to " + quotasize);
         UpdateTsmStorageCmd updateTsmStorageCmd = new UpdateTsmStorageCmd();

         updateTsmStorageCmd.putCommandParameter("id", uuid);
         updateTsmStorageCmd.putCommandParameter("quotasize", quotasize);

         UpdateTsmStorageCmdResponse updateTsmStorageCmdResponse = (UpdateTsmStorageCmdResponse) getElastistorRestClient().executeCommand(updateTsmStorageCmd);

       return updateTsmStorageCmdResponse;
     }

};