//,temp,sample_4095.java,2,9,temp,sample_4906.java,2,12
//,3
public class xxx {
private static CGroupsBlkioResourceHandlerImpl getCgroupsBlkioResourceHandler( Configuration conf) throws ResourceHandlerException {
if (cGroupsBlkioResourceHandler == null) {
synchronized (DiskResourceHandler.class) {
if (cGroupsBlkioResourceHandler == null) {


log.info("creating new cgroups blkio handler");
}
}
}
}

};