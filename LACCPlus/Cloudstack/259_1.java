//,temp,ClusterMO.java,374,390,temp,HostMO.java,878,896
//,3
public class xxx {
    @Override
    public void importVmFromOVF(String ovfFilePath, String vmName, DatastoreMO dsMo, String diskOption) throws Exception {
        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - importVmFromOVF(). target MOR: " + _mor.getValue() + ", ovfFilePath: " + ovfFilePath + ", vmName: " + vmName +
                    ", datastore: " + dsMo.getMor().getValue() + ", diskOption: " + diskOption);

        ManagedObjectReference morRp = getHyperHostOwnerResourcePool();
        assert (morRp != null);

        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - importVmFromOVF(). resource pool: " + morRp.getValue());

        HypervisorHostHelper.importVmFromOVF(this, ovfFilePath, vmName, dsMo, diskOption, morRp, null);

        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - importVmFromOVF() done");
    }

};