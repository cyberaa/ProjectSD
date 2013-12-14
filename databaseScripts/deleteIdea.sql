CREATE OR REPLACE PROCEDURE deleteIdea(ideaId IN NUMBER) IS
  BEGIN
    UPDATE idea SET active=0, in_hall=0 WHERE id = ideaId;
    DELETE FROM idea_has_topic WHERE idea_id = ideaId;
    DELETE FROM idea_share WHERE idea_id = ideaId;
    DELETE FROM watchlist WHERE idea_id = ideaId;
    DELETE FROM transaction_queue WHERE idea_id = ideaId;
  END;
