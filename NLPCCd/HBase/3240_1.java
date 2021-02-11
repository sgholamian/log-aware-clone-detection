//,temp,sample_3528.java,2,14,temp,sample_5521.java,2,9
//,3
public class xxx {
private void runVerify(String outputDir, int numReducers, long expectedNodes, int tableIndex) throws Exception {
long temp = expectedNodes;
for (int i = 0; i < DEFAULT_TABLES_COUNT; i++) {
if (i <= tableIndex) {
expectedNodes = 0;
} else {
expectedNodes = temp;
}


log.info("verifying data in the table with index and expected nodes is");
}
}

};