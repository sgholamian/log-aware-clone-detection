//,temp,ElastistorUtil.java,2563,2576,temp,ElastistorUtil.java,2487,2507
//,3
public class xxx {
     public static UpdateTsmCmdResponse updateElastistorTsmIOPS(String capacityIOPs,String uuid) throws Throwable{

         s_logger.info("elastistor tsm IOPS is updating to " + capacityIOPs);
         UpdateTsmCmd updateTsmCmd = new UpdateTsmCmd();
         String throughput = String.valueOf(Long.parseLong(capacityIOPs)*4);

         updateTsmCmd.putCommandParameter("id", uuid);
         updateTsmCmd.putCommandParameter("iops", capacityIOPs);
         updateTsmCmd.putCommandParameter("throughput", throughput);

         UpdateTsmCmdResponse updateTsmStorageCmdResponse = (UpdateTsmCmdResponse) getElastistorRestClient().executeCommand(updateTsmCmd);

       return updateTsmStorageCmdResponse;
     }

};