//,temp,TransferFsImage.java,122,139,temp,TransferFsImage.java,104,120
//,3
public class xxx {
  public static MD5Hash downloadImageToStorage(URL fsName, long imageTxId,
      Storage dstStorage, boolean needDigest) throws IOException {
    String fileid = ImageServlet.getParamStringForImage(null,
        imageTxId, dstStorage);
    String fileName = NNStorage.getCheckpointImageFileName(imageTxId);
    
    List<File> dstFiles = dstStorage.getFiles(
        NameNodeDirType.IMAGE, fileName);
    if (dstFiles.isEmpty()) {
      throw new IOException("No targets in destination storage!");
    }
    
    MD5Hash hash = getFileClient(fsName, fileid, dstFiles, dstStorage, needDigest);
    LOG.info("Downloaded file " + dstFiles.get(0).getName() + " size " +
        dstFiles.get(0).length() + " bytes.");
    return hash;
  }

};