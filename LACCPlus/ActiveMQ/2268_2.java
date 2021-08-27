//,temp,FailoverTransportBackupsTest.java,147,181,temp,FailoverTransportBackupsTest.java,101,145
//,3
public class xxx {
    @Test
    public void testFailoverToBackups() throws Exception {
        this.transport = createTransport(2);
        assertNotNull(failoverTransport);
        assertTrue(failoverTransport.isBackup());
        assertEquals(2, failoverTransport.getBackupPoolSize());

        assertTrue("Timed out waiting for Backups to connect.", Wait.waitFor(new Wait.Condition(){
            @Override
            public boolean isSatisified() throws Exception {
                LOG.debug("Current Backup Count = " + failoverTransport.getCurrentBackups());
                return failoverTransport.getCurrentBackups() == 2;
            }
        }));

        assertEquals("conected to..", "1", currentBrokerInfo.getBrokerName());
        broker1.stop();

        assertTrue("Timed out waiting for Backups to connect.", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.debug("Current Backup Count = " + failoverTransport.getCurrentBackups());
                return failoverTransport.getCurrentBackups() == 1;
            }
        }));

        assertTrue("Incorrect number of Transport interruptions", transportInterruptions >= 1);
        assertTrue("Incorrect number of Transport resumptions", transportResumptions >= 1);

        assertEquals("conected to..", "2", currentBrokerInfo.getBrokerName());
        broker2.stop();

        assertTrue("Timed out waiting for Backups to connect.", Wait.waitFor(new Wait.Condition() {
            @Override
            public boolean isSatisified() throws Exception {
                LOG.debug("Current Backup Count = " + failoverTransport.getCurrentBackups());
                return failoverTransport.getCurrentBackups() == 0;
            }
        }));

        assertTrue("Incorrect number of Transport interruptions", transportInterruptions >= 2);
        assertTrue("Incorrect number of Transport resumptions", transportResumptions >= 2);

        assertEquals("conected to..", "3", currentBrokerInfo.getBrokerName());
    }

};