//,temp,TestCodec.java,712,747,temp,TestCodec.java,680,710
//,3
public class xxx {
  void GzipConcatTest(Configuration conf,
      Class<? extends Decompressor> decomClass) throws IOException {
    Random r = new Random();
    long seed = r.nextLong();
    r.setSeed(seed);
    LOG.info(decomClass + " seed: " + seed);

    final int CONCAT = r.nextInt(4) + 3;
    final int BUFLEN = 128 * 1024;
    DataOutputBuffer dflbuf = new DataOutputBuffer();
    DataOutputBuffer chkbuf = new DataOutputBuffer();
    byte[] b = new byte[BUFLEN];
    for (int i = 0; i < CONCAT; ++i) {
      GZIPOutputStream gzout = new GZIPOutputStream(dflbuf);
      r.nextBytes(b);
      int len = r.nextInt(BUFLEN);
      int off = r.nextInt(BUFLEN - len);
      chkbuf.write(b, off, len);
      gzout.write(b, off, len);
      gzout.close();
    }
    final byte[] chk = Arrays.copyOf(chkbuf.getData(), chkbuf.getLength());

    CompressionCodec codec = ReflectionUtils.newInstance(GzipCodec.class, conf);
    Decompressor decom = codec.createDecompressor();
    assertNotNull(decom);
    assertEquals(decomClass, decom.getClass());
    DataInputBuffer gzbuf = new DataInputBuffer();
    gzbuf.reset(dflbuf.getData(), dflbuf.getLength());
    InputStream gzin = codec.createInputStream(gzbuf, decom);

    dflbuf.reset();
    IOUtils.copyBytes(gzin, dflbuf, 4096);
    final byte[] dflchk = Arrays.copyOf(dflbuf.getData(), dflbuf.getLength());
    assertArrayEquals(chk, dflchk);
  }

};