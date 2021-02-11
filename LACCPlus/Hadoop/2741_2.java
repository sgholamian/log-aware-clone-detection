//,temp,MiniDFSClusterManager.java,230,245,temp,TestDefaultStringifier.java,100,117
//,3
public class xxx {
  @Test
  public void testStoreLoadArray() throws IOException {
    LOG.info("Testing DefaultStringifier#storeArray() and #loadArray()");
    conf.set("io.serializations", "org.apache.hadoop.io.serializer.JavaSerialization");

    String keyName = "test.defaultstringifier.key2";

    Integer[] array = new Integer[] {1,2,3,4,5};


    DefaultStringifier.storeArray(conf, array, keyName);

    Integer[] claimedArray = DefaultStringifier.<Integer>loadArray(conf, keyName, Integer.class);
    for (int i = 0; i < array.length; i++) {
      assertEquals("two arrays are not equal", array[i], claimedArray[i]);
    }

  }

};