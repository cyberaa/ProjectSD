CREATE OR REPLACE FUNCTION newidea(userId IN NUMBER, numberShares IN NUMBER, priceShare IN NUMBER, ideaText IN VARCHAR2, attachFile IN VARCHAR2, faceId IN VARCHAR2) RETURN NUMBER IS  
  PRAGMA AUTONOMOUS_TRANSACTION;
  ideaId NUMBER;
BEGIN
  INSERT INTO idea (id,user_id,number_parts,active,text,attach,in_hall,face_id) VALUES (seq_idea.nextval, userId, numberShares, 1, ideaText, attachFile, 0, faceId);
  SELECT seq_idea.currval INTO ideaId FROM dual;
  INSERT INTO idea_share (id,idea_id,user_id,parts,VALUE) VALUES (seq_idea_share.nextval, ideaId, userId, numberShares, priceShare);
  COMMIT;
  return ideaId;
EXCEPTION
  WHEN OTHERS THEN
    ROLLBACK;
    return -1;
END;