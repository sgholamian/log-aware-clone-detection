//,temp,VirtualMachineMO.java,1208,1234,temp,VirtualMachineMO.java,1191,1206
//,3
public class xxx {
    public void updateVmdkAdapter(String vmdkFileName, String newAdapterType) throws Exception {
        Pair<VmdkFileDescriptor, byte[]> vmdkInfo = getVmdkFileInfo(vmdkFileName);
        VmdkFileDescriptor vmdkFileDescriptor = vmdkInfo.first();
        boolean isVmfsSparseFile = vmdkFileDescriptor.isVmfsSparseFile();
        if (!isVmfsSparseFile) {
            String currentAdapterType = vmdkFileDescriptor.getAdapterType();
            if (!currentAdapterType.equalsIgnoreCase(newAdapterType)) {
                s_logger.info("Updating adapter type to " + newAdapterType + " for VMDK file " + vmdkFileName);
                Pair<DatacenterMO, String> dcInfo = getOwnerDatacenter();
                byte[] newVmdkContent = vmdkFileDescriptor.changeVmdkAdapterType(vmdkInfo.second(), newAdapterType);
                String vmdkUploadUrl = getContext().composeDatastoreBrowseUrl(dcInfo.first().getName(), vmdkFileName);
                getContext().uploadResourceContent(vmdkUploadUrl, newVmdkContent);
                s_logger.info("Updated VMDK file " + vmdkFileName);
            }
        }
    }

};