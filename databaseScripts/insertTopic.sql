CREATE OR REPLACE FUNCTION insertTopic(name IN VARCHAR2) RETURN
  NUMBER IS
PRAGMA AUTONOMOUS_TRANSACTION;
  topicId NUMBER := 0;
  counter NUMBER;
  BEGIN
    SELECT COUNT(*) INTO counter FROM topic WHERE text LIKE name;
    IF counter = 0 THEN
      INSERT INTO topic (id, text) VALUES (seq_topic.nextval,name);
      SELECT seq_topic.currval INTO topicId FROM dual;
      COMMIT;
    END IF;
    return topicId;
  END;
