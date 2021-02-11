//,temp,DockerClient.java,46,60,temp,RpcProgramNfs3.java,228,241
//,3
public class xxx {
  private void clearDirectory(String writeDumpDir) throws IOException {
    File dumpDir = new File(writeDumpDir);
    if (dumpDir.exists()) {
      LOG.info("Delete current dump directory " + writeDumpDir);
      if (!(FileUtil.fullyDelete(dumpDir))) {
        throw new IOException("Cannot remove current dump directory: "
            + dumpDir);
      }
    }
    LOG.info("Create new dump directory " + writeDumpDir);
    if (!dumpDir.mkdirs()) {
      throw new IOException("Cannot create dump directory " + dumpDir);
    }
  }

};