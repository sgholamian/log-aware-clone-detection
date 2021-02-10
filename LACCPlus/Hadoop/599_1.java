//,temp,SwiftTestUtils.java,193,235,temp,ContractTestUtils.java,227,269
//,2
public class xxx {
  public static void compareByteArrays(byte[] src,
                                       byte[] dest,
                                       int len) {
    assertEquals("Number of bytes read != number written",
                        len, dest.length);
    int errors = 0;
    int first_error_byte = -1;
    for (int i = 0; i < len; i++) {
      if (src[i] != dest[i]) {
        if (errors == 0) {
          first_error_byte = i;
        }
        errors++;
      }
    }

    if (errors > 0) {
      String message = String.format(" %d errors in file of length %d",
                                     errors, len);
      LOG.warn(message);
      // the range either side of the first error to print
      // this is a purely arbitrary number, to aid user debugging
      final int overlap = 10;
      for (int i = Math.max(0, first_error_byte - overlap);
           i < Math.min(first_error_byte + overlap, len);
           i++) {
        byte actual = dest[i];
        byte expected = src[i];
        String letter = toChar(actual);
        String line = String.format("[%04d] %2x %s%n", i, actual, letter);
        if (expected != actual) {
          line = String.format("[%04d] %2x %s -expected %2x %s%n",
                               i,
                               actual,
                               letter,
                               expected,
                               toChar(expected));
        }
        LOG.warn(line);
      }
      fail(message);
    }
  }

};