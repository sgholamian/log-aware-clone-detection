//,temp,TestCodec.java,712,747,temp,TestCodec.java,680,710
//,3
public class xxx {
  @Test
  public void testGzipCompatibility() throws IOException {
    Random r = new Random();
    long seed = r.nextLong();
    r.setSeed(seed);
    LOG.info("seed: " + seed);

    DataOutputBuffer dflbuf = new DataOutputBuffer();
    GZIPOutputStream gzout = new GZIPOutputStream(dflbuf);
    byte[] b = new byte[r.nextInt(128 * 1024 + 1)];
    r.nextBytes(b);
    gzout.write(b);
    gzout.close();

    DataInputBuffer gzbuf = new DataInputBuffer();
    gzbuf.reset(dflbuf.getData(), dflbuf.getLength());

    Configuration conf = new Configuration();
    // don't use native libs
    ZlibFactory.setNativeZlibLoaded(false);
    CompressionCodec codec = ReflectionUtils.newInstance(GzipCodec.class, conf);
    Decompressor decom = codec.createDecompressor();
    assertNotNull(decom);
    assertEquals(BuiltInGzipDecompressor.class, decom.getClass());
    InputStream gzin = codec.createInputStream(gzbuf, decom);

    dflbuf.reset();
    IOUtils.copyBytes(gzin, dflbuf, 4096);
    final byte[] dflchk = Arrays.copyOf(dflbuf.getData(), dflbuf.getLength());
    assertArrayEquals(b, dflchk);
  }

};