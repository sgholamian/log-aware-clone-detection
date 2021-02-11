//,temp,TestDelegationTokenForProxyUser.java,73,97,temp,TestDoAsEffectiveUser.java,83,107
//,2
public class xxx {
  private void configureSuperUserIPAddresses(Configuration conf,
      String superUserShortName) throws IOException {
    ArrayList<String> ipList = new ArrayList<String>();
    Enumeration<NetworkInterface> netInterfaceList = NetworkInterface
        .getNetworkInterfaces();
    while (netInterfaceList.hasMoreElements()) {
      NetworkInterface inf = netInterfaceList.nextElement();
      Enumeration<InetAddress> addrList = inf.getInetAddresses();
      while (addrList.hasMoreElements()) {
        InetAddress addr = addrList.nextElement();
        ipList.add(addr.getHostAddress());
      }
    }
    StringBuilder builder = new StringBuilder();
    for (String ip : ipList) {
      builder.append(ip);
      builder.append(',');
    }
    builder.append("127.0.1.1,");
    builder.append(InetAddress.getLocalHost().getCanonicalHostName());
    LOG.info("Local Ip addresses: "+builder.toString());
    conf.setStrings(DefaultImpersonationProvider.getTestProvider().
            getProxySuperuserIpConfKey(superUserShortName),
        builder.toString());
  }

};