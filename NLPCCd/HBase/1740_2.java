//,temp,sample_2695.java,2,16,temp,sample_2694.java,2,16
//,3
public class xxx {
private Path getReplSyncUpPath(Path path) throws IOException {
FileStatus[] rss = fs.listStatus(manager.getLogDir());
for (FileStatus rs : rss) {
Path p = rs.getPath();
FileStatus[] logs = fs.listStatus(p);
for (FileStatus log : logs) {
p = new Path(p, log.getPath().getName());
if (p.getName().equals(path.getName())) {


log.info("log found at");
}
}
}
}

};