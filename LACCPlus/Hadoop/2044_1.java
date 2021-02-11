//,temp,JHLogAnalyzer.java,498,529,temp,JHLogAnalyzer.java,401,427
//,3
public class xxx {
    String parse(StringTokenizer tokens) throws IOException {
      String taskID = null;
      while(tokens.hasMoreTokens()) {
        String t = tokens.nextToken();
        String[] keyVal = getKeyValue(t);
        if(keyVal.length < 2) continue;

        if(keyVal[0].equals("TASKID")) {
          if(taskID == null)
            taskID = new String(keyVal[1]);
          else if(!taskID.equals(keyVal[1])) {
            LOG.error("Incorrect TASKID: " + keyVal[1] + " expect " + taskID);
            continue;
          }
        }
        else if(keyVal[0].equals("TASK_ATTEMPT_ID")) {
          if(TASK_ATTEMPT_ID == null)
            TASK_ATTEMPT_ID = new String(keyVal[1]);
          else if(!TASK_ATTEMPT_ID.equals(keyVal[1])) {
            LOG.error("Incorrect TASKID: " + keyVal[1] + " expect " + taskID);
            continue;
          }
        }
        else if(keyVal[0].equals("TASK_STATUS"))
          TASK_STATUS = new String(keyVal[1]);
        else if(keyVal[0].equals("START_TIME"))
          START_TIME = Long.parseLong(keyVal[1]);
        else if(keyVal[0].equals("FINISH_TIME"))
          FINISH_TIME = Long.parseLong(keyVal[1]);
      }
      return taskID;
    }

};