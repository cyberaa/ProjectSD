DROP TRIGGER TSHARES;

create or replace 
trigger TSHARES
AFTER
UPDATE ON idea_share 
FOR EACH ROW 
WHEN (NEW.parts < 0)
BEGIN
  DELETE FROM idea_share WHERE id = :NEW.id;
END;
