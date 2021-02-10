//,temp,XceiverServer.java,63,87,temp,XceiverServerGrpc.java,55,83
//,3
public class xxx {
  public XceiverServer(DatanodeDetails datanodeDetails, Configuration conf,
                       ContainerDispatcher dispatcher) {
    Preconditions.checkNotNull(conf);

    this.port = conf.getInt(OzoneConfigKeys.DFS_CONTAINER_IPC_PORT,
        OzoneConfigKeys.DFS_CONTAINER_IPC_PORT_DEFAULT);
    // Get an available port on current node and
    // use that as the container port
    if (conf.getBoolean(OzoneConfigKeys.DFS_CONTAINER_IPC_RANDOM_PORT,
        OzoneConfigKeys.DFS_CONTAINER_IPC_RANDOM_PORT_DEFAULT)) {
      try (ServerSocket socket = new ServerSocket()) {
        socket.setReuseAddress(true);
        SocketAddress address = new InetSocketAddress(0);
        socket.bind(address);
        this.port = socket.getLocalPort();
        LOG.info("Found a free port for the server : {}", this.port);
      } catch (IOException e) {
        LOG.error("Unable find a random free port for the server, "
            + "fallback to use default port {}", this.port, e);
      }
    }
    datanodeDetails.setPort(
        DatanodeDetails.newPort(DatanodeDetails.Port.Name.STANDALONE, port));
    this.storageContainer = dispatcher;
  }

};