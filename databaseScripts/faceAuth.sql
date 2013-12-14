CREATE OR REPLACE FUNCTION authenticate(username IN varchar2, faceId IN varchar2) RETURN NUMBER IS
  PRAGMA AUTONOMOUS_TRANSACTION;
  user_reg NUMBER;
  counter NUMBER;
BEGIN
  SELECT count(*) INTO counter FROM sduser WHERE face_id = faceId;
  IF counter > 0 THEN
    SELECT id INTO user_reg FROM sduser WHERE face_id = faceId;
    return user_reg;
  ELSE
    INSERT INTO sduser VALUES (seq_sduser.nextval, username, 'face', 1000000, 0, 0, faceId);
    SELECT id INTO user_reg FROM sduser WHERE face_id = faceId;
    COMMIT;
    return user_reg;
  END IF;
EXCEPTION
  WHEN OTHERS THEN
    return -1;
    ROLLBACK;
END;
