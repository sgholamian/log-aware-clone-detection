//,temp,NettyServerCnxnFactory.java,478,492,temp,NettyServerCnxnFactory.java,465,476
//,3
public class xxx {
    public void reconfigure(InetSocketAddress addr) {
       Channel oldChannel = parentChannel;
       try {
           LOG.info("binding to port {}", addr);
           parentChannel = bootstrap.bind(addr).syncUninterruptibly().channel();
           // Port changes after bind() if the original port was 0, update
           // localAddress to get the real port.
           localAddress = (InetSocketAddress) parentChannel.localAddress();
           LOG.info("bound to port " + getLocalPort());
       } catch (Exception e) {
           LOG.error("Error while reconfiguring", e);
       } finally {
           oldChannel.close();
       }
    }

};