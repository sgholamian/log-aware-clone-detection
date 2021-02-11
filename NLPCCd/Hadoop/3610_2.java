//,temp,sample_7217.java,2,16,temp,sample_1297.java,2,15
//,3
public class xxx {
public void run() {
System.out.println("Writer " + id + " starting... ");
int i = 0;
try {
for (i = 0; i < paths.length; i++) {
makeRandomTestFile(paths[i], BLOCK_SIZE, true, seed);
}
} catch (IOException e) {
bFail.set(true);


log.info("writer exception writer id testfile");
}
}

};