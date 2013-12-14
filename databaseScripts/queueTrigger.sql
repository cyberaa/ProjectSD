CREATE OR REPLACE TRIGGER tQueue
AFTER
UPDATE OF share_num ON transaction_queue
  BEGIN
    DELETE FROM transaction_queue WHERE share_num = 0;
  END;
