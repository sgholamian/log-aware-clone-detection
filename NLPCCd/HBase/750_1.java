//,temp,sample_3144.java,2,15,temp,sample_4707.java,2,14
//,3
public class xxx {
public static boolean validate(HashMap<TableName, BackupManifest> backupManifestMap, Configuration conf) throws IOException {
boolean isValid = true;
for (Entry<TableName, BackupManifest> manifestEntry : backupManifestMap.entrySet()) {
TableName table = manifestEntry.getKey();
TreeSet<BackupImage> imageSet = new TreeSet<BackupImage>();
ArrayList<BackupImage> depList = manifestEntry.getValue().getDependentListByTable(table);
if (depList != null && !depList.isEmpty()) {
imageSet.addAll(depList);
}


log.info("dependent image s from old to new");
}
}

};