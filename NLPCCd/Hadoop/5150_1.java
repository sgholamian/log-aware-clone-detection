//,temp,sample_7100.java,2,18,temp,sample_7098.java,2,12
//,3
public class xxx {
private void verifyChunks(ByteBuffer dataBuf, ByteBuffer checksumBuf) throws IOException {
try {
clientChecksum.verifyChunkedSums(dataBuf, checksumBuf, clientname, 0);
} catch (ChecksumException ce) {
PacketHeader header = packetReceiver.getHeader();
String specificOffset = "specific offsets are:" + " offsetInBlock = " + header.getOffsetInBlock() + " offsetInPacket = " + ce.getPos();
if (srcDataNode != null && isDatanode) {
try {
datanode.reportRemoteBadBlock(srcDataNode, block);
} catch (IOException e) {


log.info("failed to report bad from datanode to namenode");
}
}
}
}

};