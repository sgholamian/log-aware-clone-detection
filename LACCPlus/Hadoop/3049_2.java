//,temp,AllocationTagsManager.java,147,160,temp,InputStriper.java,55,65
//,3
public class xxx {
  InputStriper(FilePool inputDir, long mapBytes)
      throws IOException {
    final long inputBytes = inputDir.getInputFiles(mapBytes, files);
    if (mapBytes > inputBytes) {
      LOG.warn("Using " + inputBytes + "/" + mapBytes + " bytes");
    }
    if (files.isEmpty() && mapBytes > 0) {
      throw new IOException("Failed to satisfy request for " + mapBytes);
    }
    current = files.isEmpty() ? null : files.get(0);
  }

};