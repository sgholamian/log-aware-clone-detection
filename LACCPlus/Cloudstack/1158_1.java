//,temp,VirtualMachineMO.java,1208,1234,temp,VirtualMachineMO.java,1191,1206
//,3
public class xxx {
    public void updateAdapterTypeIfRequired(String vmdkFileName) throws Exception {
        // Validate existing adapter type of VMDK file. Update it with a supported adapter type if validation fails.
        Pair<VmdkFileDescriptor, byte[]> vmdkInfo = getVmdkFileInfo(vmdkFileName);
        VmdkFileDescriptor vmdkFileDescriptor = vmdkInfo.first();

        boolean isVmfsSparseFile = vmdkFileDescriptor.isVmfsSparseFile();
        if (!isVmfsSparseFile) {
            String currentAdapterTypeStr = vmdkFileDescriptor.getAdapterType();
            if (s_logger.isTraceEnabled()) {
                s_logger.trace("Detected adapter type  " + currentAdapterTypeStr + " for VMDK file " + vmdkFileName);
            }
            VmdkAdapterType currentAdapterType = VmdkAdapterType.getType(currentAdapterTypeStr);
            if (currentAdapterType == VmdkAdapterType.none) {
                // Value of currentAdapterType can be VmdkAdapterType.none only if adapter type of vmdk is set to either
                // lsisas1068 (SAS controller) or pvscsi (Vmware Paravirtual) only. Valid adapter type for those controllers is lsilogic.
                // Hence use adapter type lsilogic. Other adapter types ide, lsilogic, buslogic are valid and does not need to be modified.
                VmdkAdapterType newAdapterType = VmdkAdapterType.lsilogic;
                s_logger.debug("Updating adapter type to " + newAdapterType + " from " + currentAdapterTypeStr + " for VMDK file " + vmdkFileName);
                Pair<DatacenterMO, String> dcInfo = getOwnerDatacenter();
                byte[] newVmdkContent = vmdkFileDescriptor.changeVmdkAdapterType(vmdkInfo.second(), newAdapterType.toString());
                String vmdkUploadUrl = getContext().composeDatastoreBrowseUrl(dcInfo.first().getName(), vmdkFileName);

                getContext().uploadResourceContent(vmdkUploadUrl, newVmdkContent);
                s_logger.debug("Updated VMDK file " + vmdkFileName);
            }
        }
    }

};