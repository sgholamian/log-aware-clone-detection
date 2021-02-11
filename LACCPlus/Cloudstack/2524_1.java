//,temp,VmwareResource.java,3937,3977,temp,VmwareResource.java,3682,3713
//,3
public class xxx {
    protected Answer execute(MigrateCommand cmd) {
        if (s_logger.isInfoEnabled()) {
            s_logger.info("Executing resource MigrateCommand: " + _gson.toJson(cmd));
        }

        final String vmName = cmd.getVmName();
        try {
            VmwareHypervisorHost hyperHost = getHyperHost(getServiceContext());
            ManagedObjectReference morDc = hyperHost.getHyperHostDatacenter();

            // find VM through datacenter (VM is not at the target host yet)
            VirtualMachineMO vmMo = hyperHost.findVmOnPeerHyperHost(vmName);
            if (vmMo == null) {
                String msg = "VM " + vmName + " does not exist in VMware datacenter";
                s_logger.error(msg);
                throw new Exception(msg);
            }

            VmwareHypervisorHost destHyperHost = getTargetHyperHost(new DatacenterMO(hyperHost.getContext(), morDc), cmd.getDestinationIp());

            ManagedObjectReference morTargetPhysicalHost = destHyperHost.findMigrationTarget(vmMo);
            if (morTargetPhysicalHost == null) {
                throw new Exception("Unable to find a target capable physical host");
            }

            if (!vmMo.migrate(destHyperHost.getHyperHostOwnerResourcePool(), morTargetPhysicalHost)) {
                throw new Exception("Migration failed");
            }

            return new MigrateAnswer(cmd, true, "migration succeeded", null);
        } catch (Throwable e) {
            if (e instanceof RemoteException) {
                s_logger.warn("Encounter remote exception to vCenter, invalidate VMware session context");
                invalidateServiceContext();
            }

            String msg = "MigrationCommand failed due to " + VmwareHelper.getExceptionMessage(e);
            s_logger.warn(msg, e);
            return new MigrateAnswer(cmd, false, msg, null);
        }
    }

};