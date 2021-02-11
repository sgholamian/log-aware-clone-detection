//,temp,sample_1255.java,2,14,temp,sample_1258.java,2,14
//,3
public class xxx {
public void testUpdateMultiDirTargetPresent() {
try {
addEntries(listFile, "Umultifile", "Usingledir");
createFiles("Umultifile/Ufile3", "Umultifile/Ufile4", "Umultifile/Ufile5");
mkdirs(target.toString(), root + "/Usingledir/Udir1");
runTest(listFile, target, true, true);
checkResult(target, 4, "Ufile3", "Ufile4", "Ufile5", "Udir1");
} catch (IOException e) {


log.info("exception encountered while testing distcp");
}
}

};