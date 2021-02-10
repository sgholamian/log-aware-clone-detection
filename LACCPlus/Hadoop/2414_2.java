//,temp,FileSystem.java,3310,3337,temp,SaslDataTransferClient.java,238,277
//,3
public class xxx {
  private IOStreamPair send(InetAddress addr, OutputStream underlyingOut,
      InputStream underlyingIn, DataEncryptionKey encryptionKey,
      Token<BlockTokenIdentifier> accessToken, DatanodeID datanodeId)
      throws IOException {
    if (encryptionKey != null) {
      LOG.debug("SASL client doing encrypted handshake for addr = {}, "
          + "datanodeId = {}", addr, datanodeId);
      return getEncryptedStreams(addr, underlyingOut, underlyingIn,
          encryptionKey);
    } else if (!UserGroupInformation.isSecurityEnabled()) {
      LOG.debug("SASL client skipping handshake in unsecured configuration for "
          + "addr = {}, datanodeId = {}", addr, datanodeId);
      return null;
    } else if (SecurityUtil.isPrivilegedPort(datanodeId.getXferPort())) {
      LOG.debug(
          "SASL client skipping handshake in secured configuration with "
              + "privileged port for addr = {}, datanodeId = {}",
          addr, datanodeId);
      return null;
    } else if (fallbackToSimpleAuth != null && fallbackToSimpleAuth.get()) {
      LOG.debug(
          "SASL client skipping handshake in secured configuration with "
              + "unsecured cluster for addr = {}, datanodeId = {}",
          addr, datanodeId);
      return null;
    } else if (saslPropsResolver != null) {
      LOG.debug(
          "SASL client doing general handshake for addr = {}, datanodeId = {}",
          addr, datanodeId);
      return getSaslStreams(addr, underlyingOut, underlyingIn, accessToken);
    } else {
      // It's a secured cluster using non-privileged ports, but no SASL.  The
      // only way this can happen is if the DataNode has
      // ignore.secure.ports.for.testing configured so this is a rare edge case.
      LOG.debug("SASL client skipping handshake in secured configuration with "
              + "no SASL protection configured for addr = {}, datanodeId = {}",
          addr, datanodeId);
      return null;
    }
  }

};