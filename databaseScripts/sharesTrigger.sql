CREATE OR REPLACE TRIGGER tShares
AFTER
UPDATE OF parts ON idea_share
  BEGIN
    DELETE FROM idea_share WHERE parts = 0;
  END;
