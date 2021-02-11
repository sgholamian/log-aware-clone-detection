//,temp,RpcProgram.java,108,120,temp,RpcProgram.java,91,103
//,2
public class xxx {
  public void unregister(int transport, int boundPort) {
    if (boundPort != port) {
      LOG.info("The bound port is " + boundPort
          + ", different with configured port " + port);
      port = boundPort;
    }
    // Unregister all the program versions with portmapper for a given transport
    for (int vers = lowProgVersion; vers <= highProgVersion; vers++) {
      PortmapMapping mapEntry = new PortmapMapping(progNumber, vers, transport,
          port);
      register(mapEntry, false);
    }
  }

};