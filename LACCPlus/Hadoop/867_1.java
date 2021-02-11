//,temp,TransferFsImage.java,122,139,temp,TransferFsImage.java,104,120
//,3
public class xxx {
  static MD5Hash handleUploadImageRequest(HttpServletRequest request,
      long imageTxId, Storage dstStorage, InputStream stream,
      long advertisedSize, DataTransferThrottler throttler) throws IOException {

    String fileName = NNStorage.getCheckpointImageFileName(imageTxId);

    List<File> dstFiles = dstStorage.getFiles(NameNodeDirType.IMAGE, fileName);
    if (dstFiles.isEmpty()) {
      throw new IOException("No targets in destination storage!");
    }

    MD5Hash advertisedDigest = parseMD5Header(request);
    MD5Hash hash = receiveFile(fileName, dstFiles, dstStorage, true,
        advertisedSize, advertisedDigest, fileName, stream, throttler);
    LOG.info("Downloaded file " + dstFiles.get(0).getName() + " size "
        + dstFiles.get(0).length() + " bytes.");
    return hash;
  }

};