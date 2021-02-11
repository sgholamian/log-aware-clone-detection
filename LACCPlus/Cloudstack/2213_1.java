//,temp,ClusterMO.java,640,650,temp,HostDatastoreBrowserMO.java,41,49
//,3
public class xxx {
    @Override
    public ComputeResourceSummary getHyperHostHardwareSummary() throws Exception {
        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - getHyperHostHardwareSummary(). target MOR: " + _mor.getValue());

        ClusterComputeResourceSummary hardwareSummary = (ClusterComputeResourceSummary)_context.getVimClient().getDynamicProperty(_mor, "summary");

        if (s_logger.isTraceEnabled())
            s_logger.trace("vCenter API trace - getHyperHostHardwareSummary() done");
        return hardwareSummary;
    }

};