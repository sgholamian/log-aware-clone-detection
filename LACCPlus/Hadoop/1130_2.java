//,temp,UnmanagedAMLauncher.java,259,270,temp,UnmanagedAMLauncher.java,245,256
//,2
public class xxx {
      @Override
      public void run() {
        try {
          String line = errReader.readLine();
          while((line != null) && !isInterrupted()) {
            System.err.println(line);
            line = errReader.readLine();
          }
        } catch(IOException ioe) {
          LOG.warn("Error reading the error stream", ioe);
        }
      }

};