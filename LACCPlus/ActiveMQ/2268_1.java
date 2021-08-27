//,temp,FailoverTransportBackupsTest.java,147,181,temp,FailoverTransportBackupsTest.java,101,145
//,3
public class xxx {
    @Test
    public void testBackupsRefilled() throws Exception {
        this.transport = createTransport(1);
        assertNotNull(failoverTransport);
        assertTrue(failoverTransport.isBackup());
        assertEquals(1, failoverTransport.getBackupPoolSize());

        assertTrue("Timed out waiting for Backups to connect.", Wait.waitFor(new Wait.Condition(){
            @Override
            public boolean isSatisified() throws Exception {
                LOG.debug("Current Backup Count = " + failoverTransport.getCurrentBackups());
                return failoverTransport.getCurrentBackups() == 1;
            }
        }));

        broker1.stop();

        assertTrue("Timed out waiting for Backups to connect.", Wait.waitFor(new Wait.Condition(){
            @Override
            public boolean isSatisified() throws Exception {
                LOG.debug("Current Backup Count = " + failoverTransport.getCurrentBackups());
                return failoverTransport.getCurrentBackups() == 1;
            }
        }));

        broker2.stop();

        assertTrue("Timed out waiting for Backups to connect.", Wait.waitFor(new Wait.Condition(){
            @Override
            public boolean isSatisified() throws Exception {
                LOG.debug("Current Backup Count = " + failoverTransport.getCurrentBackups());
                return failoverTransport.getCurrentBackups() == 0;
            }
        }));
    }

};