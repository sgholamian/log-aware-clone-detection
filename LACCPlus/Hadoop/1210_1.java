//,temp,TestLargeBlock.java,57,65,temp,TestDecommission.java,150,162
//,3
public class xxx {
  static FSDataOutputStream createFile(FileSystem fileSys, Path name, int repl,
                                       final long blockSize)
    throws IOException {
    FSDataOutputStream stm = fileSys.create(name, true, fileSys.getConf()
        .getInt(CommonConfigurationKeys.IO_FILE_BUFFER_SIZE_KEY, 4096),
        (short) repl, blockSize);
    LOG.info("createFile: Created " + name + " with " + repl + " replica.");
    return stm;
  }

};