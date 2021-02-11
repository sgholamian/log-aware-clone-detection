//,temp,sample_2399.java,2,18,temp,sample_2398.java,2,18
//,3
public class xxx {
public void dummy_method(){
FileSystem backingFs = ((HFileSystem)fs).getBackingFs();
if (backingFs instanceof DistributedFileSystem) {
short replication = Short.parseShort(conf.get(HColumnDescriptor.DFS_REPLICATION, String.valueOf(HColumnDescriptor.DEFAULT_DFS_REPLICATION)));
try {
return (FSDataOutputStream) (DistributedFileSystem.class.getDeclaredMethod("create", Path.class, FsPermission.class, boolean.class, int.class, short.class, long.class, Progressable.class, InetSocketAddress[].class).invoke(backingFs, path, perm, true, getDefaultBufferSize(backingFs), replication > 0 ? replication : getDefaultReplication(backingFs, path), getDefaultBlockSize(backingFs, path), null, favoredNodes));
} catch (InvocationTargetException ite) {
throw new IOException(ite.getCause());
} catch (NoSuchMethodException e) {
} catch (IllegalArgumentException e) {
} catch (SecurityException e) {


log.info("ignoring most likely reflection related exception");
}
}
}

};