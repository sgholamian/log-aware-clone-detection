//,temp,BigSwitchBcfUtils.java,456,469,temp,BigSwitchBcfUtils.java,441,454
//,3
public class xxx {
    public String syncTopologyToBcfHost(HostVO bigswitchBcfHost){
        SyncBcfTopologyCommand syncCmd;
        if(isNatEnabled()){
            syncCmd = new SyncBcfTopologyCommand(true, true);
        } else {
            syncCmd = new SyncBcfTopologyCommand(true, false);
        }
        BcfAnswer syncAnswer = (BcfAnswer) _agentMgr.easySend(bigswitchBcfHost.getId(), syncCmd);
        if (syncAnswer == null || !syncAnswer.getResult()) {
            s_logger.error("SyncBcfTopologyCommand failed");
            return null;
        }
        return syncAnswer.getHash();
    }

};