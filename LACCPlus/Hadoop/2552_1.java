//,temp,TestWebHDFS.java,161,166,temp,TestWebHDFS.java,151,159
//,3
public class xxx {
    void end(final long nBytes) {
      final long now = System.nanoTime();
      final double seconds = (now - startTime)/1000000000.0;
      LOG.info(String.format("\n\n%s END: duration=%.2fs %s\n",
          name, seconds, toMpsString(nBytes, now)));
    }

};