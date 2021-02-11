//,temp,VersionInfo.java,170,179,temp,YarnVersionInfo.java,103,109
//,3
public class xxx {
  public static void main(String[] args) {
    LOG.debug("version: "+ getVersion());
    System.out.println("Hadoop " + getVersion());
    System.out.println("Subversion " + getUrl() + " -r " + getRevision());
    System.out.println("Compiled by " + getUser() + " on " + getDate());
    System.out.println("Compiled with protoc " + getProtocVersion());
    System.out.println("From source with checksum " + getSrcChecksum());
    System.out.println("This command was run using " + 
        ClassUtil.findContainingJar(VersionInfo.class));
  }

};