//,temp,TestLargeBlock.java,57,65,temp,TestDecommission.java,150,162
//,3
public class xxx {
  private void writeFile(FileSystem fileSys, Path name, int repl)
    throws IOException {
    // create and write a file that contains three blocks of data
    FSDataOutputStream stm = fileSys.create(name, true, fileSys.getConf()
        .getInt(CommonConfigurationKeys.IO_FILE_BUFFER_SIZE_KEY, 4096),
        (short) repl, blockSize);
    byte[] buffer = new byte[fileSize];
    Random rand = new Random(seed);
    rand.nextBytes(buffer);
    stm.write(buffer);
    stm.close();
    LOG.info("Created file " + name + " with " + repl + " replicas.");
  }

};