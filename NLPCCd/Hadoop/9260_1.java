//,temp,sample_1207.java,2,13,temp,sample_9192.java,2,9
//,3
public class xxx {
public <T extends FileSystem & Renewable> void removeRenewAction( final T fs) throws IOException {
RenewAction<T> action = new RenewAction<T>(fs);
if (queue.remove(action)) {
try {
action.cancel();
} catch (InterruptedException ie) {


log.info("interrupted while canceling token for filesystem");
}
}
}

};