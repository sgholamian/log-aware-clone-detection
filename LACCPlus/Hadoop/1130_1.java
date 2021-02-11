//,temp,UnmanagedAMLauncher.java,259,270,temp,UnmanagedAMLauncher.java,245,256
//,2
public class xxx {
      @Override
      public void run() {
        try {
          String line = inReader.readLine();
          while((line != null) && !isInterrupted()) {
            System.out.println(line);
            line = inReader.readLine();
          }
        } catch(IOException ioe) {
          LOG.warn("Error reading the out stream", ioe);
        }
      }

};