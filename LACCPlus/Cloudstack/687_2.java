//,temp,RequestTest.java,266,275,temp,RequestTest.java,255,264
//,2
public class xxx {
    public void testGoodCommand() {
        s_logger.info("Testing good Command");
        String content = "[{\"com.cloud.agent.api.GetVolumeStatsCommand\":{\"volumeUuids\":[\"dcc860ac-4a20-498f-9cb3-bab4d57aa676\"],"
                + "\"poolType\":\"NetworkFilesystem\",\"poolUuid\":\"e007c270-2b1b-3ce9-ae92-a98b94eef7eb\",\"contextMap\":{},\"wait\":5}}]";
        Request sreq = new Request(Version.v2, 1L, 2L, 3L, 1L, (short)1, content);
        sreq.setSequence(1);
        Command cmds[] = sreq.getCommands();
        s_logger.debug("Command class = " + cmds[0].getClass().getSimpleName());
        assert cmds[0].getClass().equals(GetVolumeStatsCommand.class);
    }

};