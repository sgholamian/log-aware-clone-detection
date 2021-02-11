//,temp,sample_4190.java,2,12,temp,sample_1608.java,2,12
//,3
public class xxx {
public void run() {
for (RegionScanner scanner : scanners) {
try {
scanner.close();
} catch (IOException e) {


log.info("exception while closing the scanner");
}
}
}

};