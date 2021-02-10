//,temp,TestDFSIO.java,470,479,temp,TestDFSIO.java,404,413
//,3
public class xxx {
    @Override // IOMapperBase
    public Closeable getIOStream(String name) throws IOException {
      // create file
      OutputStream out =
          fs.create(new Path(getDataDir(getConf()), name), true, bufferSize);
      if(compressionCodec != null)
        out = compressionCodec.createOutputStream(out);
      LOG.info("out = " + out.getClass().getName());
      return out;
    }

};