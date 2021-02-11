//,temp,ZooKeeperSaslClient.java,372,386,temp,ZooKeeperSaslClient.java,353,370
//,3
public class xxx {
    private void sendSaslPacket(byte[] saslToken, ClientCnxn cnxn)
      throws SaslException{
        if (LOG.isDebugEnabled()) {
            LOG.debug("ClientCnxn:sendSaslPacket:length="+saslToken.length);
        }

        GetSASLRequest request = new GetSASLRequest();
        request.setToken(saslToken);
        SetSASLResponse response = new SetSASLResponse();
        ServerSaslResponseCallback cb = new ServerSaslResponseCallback();

        try {
            cnxn.sendPacket(request,response,cb, ZooDefs.OpCode.sasl);
        } catch (IOException e) {
            throw new SaslException("Failed to send SASL packet to server.",
                e);
        }
    }

};