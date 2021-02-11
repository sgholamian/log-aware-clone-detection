//,temp,sample_909.java,2,16,temp,sample_1159.java,2,18
//,3
public class xxx {
public void doScan(int n, boolean caching) throws IOException {
Scan scan = new Scan();
if (caching) {
scan.setCaching(n);
} else {
scan.setCaching(1);
}
ResultScanner scanner = table.getScanner(scan);
for (int i = 0; i < n; i++) {
Result res = scanner.next();


log.info("result row value");
}
}

};