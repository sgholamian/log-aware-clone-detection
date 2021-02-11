//,temp,sample_3108.java,2,14,temp,sample_3109.java,2,14
//,2
public class xxx {
public Pair<VmdkFileDescriptor, byte[]> getVmdkFileInfo(String vmdkDatastorePath) throws Exception {
Pair<DatacenterMO, String> dcPair = getOwnerDatacenter();
String url = getContext().composeDatastoreBrowseUrl(dcPair.second(), vmdkDatastorePath);
byte[] content = getContext().getResourceContent(url);
VmdkFileDescriptor descriptor = new VmdkFileDescriptor();
descriptor.parse(content);
Pair<VmdkFileDescriptor, byte[]> result = new Pair<VmdkFileDescriptor, byte[]>(descriptor, content);
if (s_logger.isTraceEnabled()) {


log.info("vcenter api trace getvmdkfileinfo done");
}
}

};