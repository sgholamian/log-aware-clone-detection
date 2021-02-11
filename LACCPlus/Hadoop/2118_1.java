//,temp,AllocationFileLoaderService.java,416,541,temp,AllocationFileLoaderService.java,204,411
//,3
public class xxx {
  private void loadQueue(String parentName, Element element,
      Map<String, Resource> minQueueResources,
      Map<String, Resource> maxQueueResources, Map<String, Integer> queueMaxApps,
      Map<String, Integer> userMaxApps, Map<String, Float> queueMaxAMShares,
      Map<String, ResourceWeights> queueWeights,
      Map<String, SchedulingPolicy> queuePolicies,
      Map<String, Long> minSharePreemptionTimeouts,
      Map<String, Long> fairSharePreemptionTimeouts,
      Map<String, Float> fairSharePreemptionThresholds,
      Map<String, Map<QueueACL, AccessControlList>> queueAcls,
      Map<FSQueueType, Set<String>> configuredQueues,
      Set<String> reservableQueues)
      throws AllocationConfigurationException {
    String queueName = element.getAttribute("name").trim();

    if (queueName.contains(".")) {
      throw new AllocationConfigurationException("Bad fair scheduler config "
          + "file: queue name (" + queueName + ") shouldn't contain period.");
    }

    if (queueName.isEmpty()) {
      throw new AllocationConfigurationException("Bad fair scheduler config "
          + "file: queue name shouldn't be empty or "
          + "consist only of whitespace.");
    }

    if (parentName != null) {
      queueName = parentName + "." + queueName;
    }
    Map<QueueACL, AccessControlList> acls =
        new HashMap<QueueACL, AccessControlList>();
    NodeList fields = element.getChildNodes();
    boolean isLeaf = true;

    for (int j = 0; j < fields.getLength(); j++) {
      Node fieldNode = fields.item(j);
      if (!(fieldNode instanceof Element))
        continue;
      Element field = (Element) fieldNode;
      if ("minResources".equals(field.getTagName())) {
        String text = ((Text)field.getFirstChild()).getData().trim();
        Resource val = FairSchedulerConfiguration.parseResourceConfigValue(text);
        minQueueResources.put(queueName, val);
      } else if ("maxResources".equals(field.getTagName())) {
        String text = ((Text)field.getFirstChild()).getData().trim();
        Resource val = FairSchedulerConfiguration.parseResourceConfigValue(text);
        maxQueueResources.put(queueName, val);
      } else if ("maxRunningApps".equals(field.getTagName())) {
        String text = ((Text)field.getFirstChild()).getData().trim();
        int val = Integer.parseInt(text);
        queueMaxApps.put(queueName, val);
      } else if ("maxAMShare".equals(field.getTagName())) {
        String text = ((Text)field.getFirstChild()).getData().trim();
        float val = Float.parseFloat(text);
        val = Math.min(val, 1.0f);
        queueMaxAMShares.put(queueName, val);
      } else if ("weight".equals(field.getTagName())) {
        String text = ((Text)field.getFirstChild()).getData().trim();
        double val = Double.parseDouble(text);
        queueWeights.put(queueName, new ResourceWeights((float)val));
      } else if ("minSharePreemptionTimeout".equals(field.getTagName())) {
        String text = ((Text)field.getFirstChild()).getData().trim();
        long val = Long.parseLong(text) * 1000L;
        minSharePreemptionTimeouts.put(queueName, val);
      } else if ("fairSharePreemptionTimeout".equals(field.getTagName())) {
        String text = ((Text)field.getFirstChild()).getData().trim();
        long val = Long.parseLong(text) * 1000L;
        fairSharePreemptionTimeouts.put(queueName, val);
      } else if ("fairSharePreemptionThreshold".equals(field.getTagName())) {
        String text = ((Text)field.getFirstChild()).getData().trim();
        float val = Float.parseFloat(text);
        val = Math.max(Math.min(val, 1.0f), 0.0f);
        fairSharePreemptionThresholds.put(queueName, val);
      } else if ("schedulingPolicy".equals(field.getTagName())
          || "schedulingMode".equals(field.getTagName())) {
        String text = ((Text)field.getFirstChild()).getData().trim();
        SchedulingPolicy policy = SchedulingPolicy.parse(text);
        queuePolicies.put(queueName, policy);
      } else if ("aclSubmitApps".equals(field.getTagName())) {
        String text = ((Text)field.getFirstChild()).getData();
        acls.put(QueueACL.SUBMIT_APPLICATIONS, new AccessControlList(text));
      } else if ("aclAdministerApps".equals(field.getTagName())) {
        String text = ((Text)field.getFirstChild()).getData();
        acls.put(QueueACL.ADMINISTER_QUEUE, new AccessControlList(text));
      } else if ("reservation".equals(field.getTagName())) {
        isLeaf = false;
        reservableQueues.add(queueName);
        configuredQueues.get(FSQueueType.PARENT).add(queueName);
      } else if ("queue".endsWith(field.getTagName()) || 
          "pool".equals(field.getTagName())) {
        loadQueue(queueName, field, minQueueResources, maxQueueResources,
            queueMaxApps, userMaxApps, queueMaxAMShares, queueWeights,
            queuePolicies, minSharePreemptionTimeouts,
            fairSharePreemptionTimeouts, fairSharePreemptionThresholds,
            queueAcls, configuredQueues, reservableQueues);
        isLeaf = false;
      }
    }
    if (isLeaf) {
      // if a leaf in the alloc file is marked as type='parent'
      // then store it under 'parent'
      if ("parent".equals(element.getAttribute("type"))) {
        configuredQueues.get(FSQueueType.PARENT).add(queueName);
      } else {
        configuredQueues.get(FSQueueType.LEAF).add(queueName);
      }
    } else {
      if ("parent".equals(element.getAttribute("type"))) {
        throw new AllocationConfigurationException("Both <reservation> and " +
            "type=\"parent\" found for queue " + queueName + " which is " +
            "unsupported");
      }
      configuredQueues.get(FSQueueType.PARENT).add(queueName);
    }
    queueAcls.put(queueName, acls);
    if (maxQueueResources.containsKey(queueName) &&
        minQueueResources.containsKey(queueName)
        && !Resources.fitsIn(minQueueResources.get(queueName),
            maxQueueResources.get(queueName))) {
      LOG.warn(
          String.format(
              "Queue %s has max resources %s less than min resources %s",
          queueName, maxQueueResources.get(queueName),
              minQueueResources.get(queueName)));
    }
  }

};