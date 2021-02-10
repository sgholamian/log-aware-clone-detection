//,temp,TestGridmixRecord.java,97,120,temp,TestGridmixRecord.java,66,95
//,3
public class xxx {
  static void eqSeedTest(GridmixRecord x, GridmixRecord y, int max)
      throws Exception {
    final Random r = new Random();
    final long s = r.nextLong();
    r.setSeed(s);
    LOG.info("eqSeed: " + s);
    assertEquals(x.fixedBytes(), y.fixedBytes());
    final int min = x.fixedBytes() + 1;
    final DataOutputBuffer out1 = new DataOutputBuffer();
    final DataOutputBuffer out2 = new DataOutputBuffer();
    for (int i = min; i < max; ++i) {
      final long seed = r.nextLong();
      setSerialize(x, seed, i, out1);
      setSerialize(y, seed, i, out2);
      assertEquals(x, y);
      assertEquals(x.hashCode(), y.hashCode());

      // verify written contents match
      assertEquals(out1.getLength(), out2.getLength());
      // assumes that writes will grow buffer deterministically
      assertEquals("Bad test", out1.getData().length, out2.getData().length);
      assertArrayEquals(out1.getData(), out2.getData());
    }
  }

};