CREATE OR REPLACE PROCEDURE buyshares(userId IN NUMBER, share_price IN NUMBER, number_shares IN NUMBER, ideaId IN NUMBER, isSeller IN NUMBER) IS
  share_id NUMBER := -1;
  counter NUMBER;
BEGIN
  SELECT count(id) INTO counter FROM idea_share WHERE user_id = userId;
  IF counter > 0 THEN
    SELECT id INTO share_id FROM idea_share WHERE user_id = userId;
    IF isSeller = 0 THEN
      UPDATE idea_share SET parts = idea_share.parts + number_shares, value = share_price WHERE id = share_id;
    ELSE
      UPDATE idea_share SET parts = idea_share.parts - number_shares, value = share_price WHERE id = share_id;
    END IF;
  ELSE
    INSERT INTO idea_share VALUES (seq_idea_share.nextval, ideaId, userId, number_shares, share_price);
  END IF;
END;