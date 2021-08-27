//,temp,sample_2717.java,2,13,temp,sample_516.java,2,12
//,3
public class xxx {
public static void close(String handleId) throws IOException {
List<Connection> handleConnections;
synchronized (lock) {
handleConnections = connectionMap.remove(handleId);
}
if (handleConnections != null) {


log.info("closing connections for handle id");
}
}

};