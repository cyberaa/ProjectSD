--------------------------------------------------------
--  File created - Friday-December-06-2013
--------------------------------------------------------
--------------------------------------------------------
--  DDL for Sequence SEQ_IDEA
--------------------------------------------------------

   CREATE SEQUENCE  "SD2"."SEQ_IDEA"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 41 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_IDEA_SHARE
--------------------------------------------------------

   CREATE SEQUENCE  "SD2"."SEQ_IDEA_SHARE"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 61 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_IDEA_TRANSACTION
--------------------------------------------------------

   CREATE SEQUENCE  "SD2"."SEQ_IDEA_TRANSACTION"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 41 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_NOTIFICATION
--------------------------------------------------------

   CREATE SEQUENCE  "SD2"."SEQ_NOTIFICATION"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 41 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_SDUSER
--------------------------------------------------------

   CREATE SEQUENCE  "SD2"."SEQ_SDUSER"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 61 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_TOPIC
--------------------------------------------------------

   CREATE SEQUENCE  "SD2"."SEQ_TOPIC"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 81 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Sequence SEQ_TRANSACTION_QUEUE
--------------------------------------------------------

   CREATE SEQUENCE  "SD2"."SEQ_TRANSACTION_QUEUE"  MINVALUE 1 MAXVALUE 9999999999999999999999999999 INCREMENT BY 1 START WITH 1 CACHE 20 NOORDER  NOCYCLE ;
--------------------------------------------------------
--  DDL for Table IDEA
--------------------------------------------------------

  CREATE TABLE "SD2"."IDEA"
   (  "ID" NUMBER(10,0),
  "USER_ID" NUMBER(10,0),
  "NUMBER_PARTS" NUMBER(10,0),
  "ACTIVE" NUMBER(1,0),
  "TEXT" VARCHAR2(255 BYTE),
  "ATTACH" VARCHAR2(100 BYTE),
  "IN_HALL" NUMBER(1,0),
  "FACE_ID" VARCHAR2(255 BYTE)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table IDEA_HAS_TOPIC
--------------------------------------------------------

  CREATE TABLE "SD2"."IDEA_HAS_TOPIC"
   (  "IDEA_ID" NUMBER(10,0),
  "TOPIC_ID" NUMBER(10,0)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table IDEA_SHARE
--------------------------------------------------------

  CREATE TABLE "SD2"."IDEA_SHARE"
   (  "ID" NUMBER(10,0),
  "IDEA_ID" NUMBER(10,0),
  "USER_ID" NUMBER(10,0),
  "PARTS" NUMBER(10,0),
  "VALUE" NUMBER(38,4)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table IDEA_TRANSACTION
--------------------------------------------------------

  CREATE TABLE "SD2"."IDEA_TRANSACTION"
   (  "ID" NUMBER(10,0),
  "IDEA_ID" NUMBER(10,0),
  "BUYER_ID" NUMBER(10,0),
  "SELLER_ID" NUMBER(10,0),
  "NUMBER_PARTS" NUMBER(10,0),
  "VALUE" NUMBER(38,4),
  "TIMESTAMP" TIMESTAMP (6)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table NOTIFICATION
--------------------------------------------------------

  CREATE TABLE "SD2"."NOTIFICATION"
   (  "ID" NUMBER(10,0),
  "USER_ID" NUMBER(10,0),
  "TEXT" VARCHAR2(255 BYTE)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table SDUSER
--------------------------------------------------------

  CREATE TABLE "SD2"."SDUSER"
   (  "ID" NUMBER(10,0),
  "USERNAME" VARCHAR2(100 BYTE),
  "PASSWORD" VARCHAR2(25 BYTE),
  "CASH" NUMBER(10,0),
  "IS_ROOT" NUMBER(1,0),
  "IS_ONLINE" NUMBER(1,0),
  "FACE_ID" VARCHAR2(100 BYTE)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table TOPIC
--------------------------------------------------------

  CREATE TABLE "SD2"."TOPIC"
   (  "ID" NUMBER(10,0),
  "TEXT" VARCHAR2(100 BYTE)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table TRANSACTION_QUEUE
--------------------------------------------------------

  CREATE TABLE "SD2"."TRANSACTION_QUEUE"
   (  "ID" NUMBER(10,0),
  "USER_ID" NUMBER(10,0),
  "IDEA_ID" NUMBER(10,0),
  "SHARE_NUM" NUMBER(10,0),
  "PRICE_PER_SHARE" NUMBER(38,4),
  "NEW_PRICE_SHARE" NUMBER(38,4),
  "TIMESTAMP" TIMESTAMP (6)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Table WATCHLIST
--------------------------------------------------------

  CREATE TABLE "SD2"."WATCHLIST"
   (  "IDEA_ID" NUMBER(10,0),
  "SDUSER_ID" NUMBER(10,0)
   ) SEGMENT CREATION IMMEDIATE
  PCTFREE 10 PCTUSED 40 INITRANS 1 MAXTRANS 255 NOCOMPRESS LOGGING
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
REM INSERTING into SD2.IDEA
SET DEFINE OFF;
REM INSERTING into SD2.IDEA_HAS_TOPIC
SET DEFINE OFF;
REM INSERTING into SD2.IDEA_SHARE
SET DEFINE OFF;
REM INSERTING into SD2.IDEA_TRANSACTION
SET DEFINE OFF;
REM INSERTING into SD2.NOTIFICATION
SET DEFINE OFF;
REM INSERTING into SD2.SDUSER
SET DEFINE OFF;
REM INSERTING into SD2.TOPIC
SET DEFINE OFF;
REM INSERTING into SD2.TRANSACTION_QUEUE
SET DEFINE OFF;
REM INSERTING into SD2.WATCHLIST
SET DEFINE OFF;
--------------------------------------------------------
--  DDL for Index TRANSACTION_QUEUE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SD2"."TRANSACTION_QUEUE_PK" ON "SD2"."TRANSACTION_QUEUE" ("ID")
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index IDEA_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SD2"."IDEA_PK" ON "SD2"."IDEA" ("ID")
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index NOTIFICATION_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SD2"."NOTIFICATION_PK" ON "SD2"."NOTIFICATION" ("ID")
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index IDEA_HAS_TOPIC_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SD2"."IDEA_HAS_TOPIC_PK" ON "SD2"."IDEA_HAS_TOPIC" ("IDEA_ID", "TOPIC_ID")
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index IDEA_TRANSACTION_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SD2"."IDEA_TRANSACTION_PK" ON "SD2"."IDEA_TRANSACTION" ("ID")
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index SDUSER_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SD2"."SDUSER_PK" ON "SD2"."SDUSER" ("ID")
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index TOPIC_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SD2"."TOPIC_PK" ON "SD2"."TOPIC" ("ID")
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index WATCHLIST_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SD2"."WATCHLIST_PK" ON "SD2"."WATCHLIST" ("IDEA_ID", "SDUSER_ID")
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  DDL for Index SHARE_PK
--------------------------------------------------------

  CREATE UNIQUE INDEX "SD2"."SHARE_PK" ON "SD2"."IDEA_SHARE" ("ID")
  PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM" ;
--------------------------------------------------------
--  Constraints for Table IDEA_TRANSACTION
--------------------------------------------------------

  ALTER TABLE "SD2"."IDEA_TRANSACTION" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "SD2"."IDEA_TRANSACTION" MODIFY ("IDEA_ID" NOT NULL ENABLE);
  ALTER TABLE "SD2"."IDEA_TRANSACTION" MODIFY ("BUYER_ID" NOT NULL ENABLE);
  ALTER TABLE "SD2"."IDEA_TRANSACTION" MODIFY ("SELLER_ID" NOT NULL ENABLE);
  ALTER TABLE "SD2"."IDEA_TRANSACTION" MODIFY ("NUMBER_PARTS" NOT NULL ENABLE);
  ALTER TABLE "SD2"."IDEA_TRANSACTION" MODIFY ("VALUE" NOT NULL ENABLE);
  ALTER TABLE "SD2"."IDEA_TRANSACTION" MODIFY ("TIMESTAMP" NOT NULL ENABLE);
  ALTER TABLE "SD2"."IDEA_TRANSACTION" ADD CONSTRAINT "IDEA_TRANSACTION_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table TRANSACTION_QUEUE
--------------------------------------------------------

  ALTER TABLE "SD2"."TRANSACTION_QUEUE" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "SD2"."TRANSACTION_QUEUE" MODIFY ("USER_ID" NOT NULL ENABLE);
  ALTER TABLE "SD2"."TRANSACTION_QUEUE" MODIFY ("IDEA_ID" NOT NULL ENABLE);
  ALTER TABLE "SD2"."TRANSACTION_QUEUE" MODIFY ("SHARE_NUM" NOT NULL ENABLE);
  ALTER TABLE "SD2"."TRANSACTION_QUEUE" MODIFY ("PRICE_PER_SHARE" NOT NULL ENABLE);
  ALTER TABLE "SD2"."TRANSACTION_QUEUE" MODIFY ("NEW_PRICE_SHARE" NOT NULL ENABLE);
  ALTER TABLE "SD2"."TRANSACTION_QUEUE" ADD CONSTRAINT "TRANSACTION_QUEUE_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "SD2"."TRANSACTION_QUEUE" MODIFY ("TIMESTAMP" NOT NULL ENABLE);
--------------------------------------------------------
--  Constraints for Table IDEA_SHARE
--------------------------------------------------------

  ALTER TABLE "SD2"."IDEA_SHARE" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "SD2"."IDEA_SHARE" MODIFY ("IDEA_ID" NOT NULL ENABLE);
  ALTER TABLE "SD2"."IDEA_SHARE" MODIFY ("USER_ID" NOT NULL ENABLE);
  ALTER TABLE "SD2"."IDEA_SHARE" MODIFY ("PARTS" NOT NULL ENABLE);
  ALTER TABLE "SD2"."IDEA_SHARE" MODIFY ("VALUE" NOT NULL ENABLE);
  ALTER TABLE "SD2"."IDEA_SHARE" ADD CONSTRAINT "SHARE_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table IDEA_HAS_TOPIC
--------------------------------------------------------

  ALTER TABLE "SD2"."IDEA_HAS_TOPIC" MODIFY ("IDEA_ID" NOT NULL ENABLE);
  ALTER TABLE "SD2"."IDEA_HAS_TOPIC" MODIFY ("TOPIC_ID" NOT NULL ENABLE);
  ALTER TABLE "SD2"."IDEA_HAS_TOPIC" ADD CONSTRAINT "IDEA_HAS_TOPIC_PK" PRIMARY KEY ("IDEA_ID", "TOPIC_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table WATCHLIST
--------------------------------------------------------

  ALTER TABLE "SD2"."WATCHLIST" MODIFY ("IDEA_ID" NOT NULL ENABLE);
  ALTER TABLE "SD2"."WATCHLIST" MODIFY ("SDUSER_ID" NOT NULL ENABLE);
  ALTER TABLE "SD2"."WATCHLIST" ADD CONSTRAINT "WATCHLIST_PK" PRIMARY KEY ("IDEA_ID", "SDUSER_ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table IDEA
--------------------------------------------------------

  ALTER TABLE "SD2"."IDEA" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "SD2"."IDEA" MODIFY ("USER_ID" NOT NULL ENABLE);
  ALTER TABLE "SD2"."IDEA" MODIFY ("NUMBER_PARTS" NOT NULL ENABLE);
  ALTER TABLE "SD2"."IDEA" MODIFY ("ACTIVE" NOT NULL ENABLE);
  ALTER TABLE "SD2"."IDEA" MODIFY ("TEXT" NOT NULL ENABLE);
  ALTER TABLE "SD2"."IDEA" MODIFY ("ATTACH" NOT NULL ENABLE);
  ALTER TABLE "SD2"."IDEA" MODIFY ("IN_HALL" NOT NULL ENABLE);
  ALTER TABLE "SD2"."IDEA" ADD CONSTRAINT "IDEA_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "SD2"."IDEA" ADD UNIQUE ("TEXT")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table TOPIC
--------------------------------------------------------

  ALTER TABLE "SD2"."TOPIC" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "SD2"."TOPIC" MODIFY ("TEXT" NOT NULL ENABLE);
  ALTER TABLE "SD2"."TOPIC" ADD CONSTRAINT "TOPIC_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table NOTIFICATION
--------------------------------------------------------

  ALTER TABLE "SD2"."NOTIFICATION" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "SD2"."NOTIFICATION" MODIFY ("USER_ID" NOT NULL ENABLE);
  ALTER TABLE "SD2"."NOTIFICATION" MODIFY ("TEXT" NOT NULL ENABLE);
  ALTER TABLE "SD2"."NOTIFICATION" ADD CONSTRAINT "NOTIFICATION_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Constraints for Table SDUSER
--------------------------------------------------------

  ALTER TABLE "SD2"."SDUSER" MODIFY ("ID" NOT NULL ENABLE);
  ALTER TABLE "SD2"."SDUSER" MODIFY ("USERNAME" NOT NULL ENABLE);
  ALTER TABLE "SD2"."SDUSER" MODIFY ("PASSWORD" NOT NULL ENABLE);
  ALTER TABLE "SD2"."SDUSER" MODIFY ("CASH" NOT NULL ENABLE);
  ALTER TABLE "SD2"."SDUSER" MODIFY ("IS_ROOT" NOT NULL ENABLE);
  ALTER TABLE "SD2"."SDUSER" ADD CONSTRAINT "SDUSER_PK" PRIMARY KEY ("ID")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
  ALTER TABLE "SD2"."SDUSER" ADD UNIQUE ("USERNAME")
  USING INDEX PCTFREE 10 INITRANS 2 MAXTRANS 255 COMPUTE STATISTICS
  STORAGE(INITIAL 65536 NEXT 1048576 MINEXTENTS 1 MAXEXTENTS 2147483645
  PCTINCREASE 0 FREELISTS 1 FREELIST GROUPS 1 BUFFER_POOL DEFAULT FLASH_CACHE DEFAULT CELL_FLASH_CACHE DEFAULT)
  TABLESPACE "SYSTEM"  ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table IDEA
--------------------------------------------------------

  ALTER TABLE "SD2"."IDEA" ADD CONSTRAINT "FKIDEA566749" FOREIGN KEY ("USER_ID")
    REFERENCES "SD2"."SDUSER" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table IDEA_HAS_TOPIC
--------------------------------------------------------

  ALTER TABLE "SD2"."IDEA_HAS_TOPIC" ADD CONSTRAINT "FKIDEA_HAS_T173695" FOREIGN KEY ("TOPIC_ID")
    REFERENCES "SD2"."TOPIC" ("ID") ENABLE;
  ALTER TABLE "SD2"."IDEA_HAS_TOPIC" ADD CONSTRAINT "FKIDEA_HAS_T583052" FOREIGN KEY ("IDEA_ID")
    REFERENCES "SD2"."IDEA" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table IDEA_SHARE
--------------------------------------------------------

  ALTER TABLE "SD2"."IDEA_SHARE" ADD CONSTRAINT "FKIDEA_SHARE798495" FOREIGN KEY ("USER_ID")
    REFERENCES "SD2"."SDUSER" ("ID") ENABLE;
  ALTER TABLE "SD2"."IDEA_SHARE" ADD CONSTRAINT "FKIDEA_SHARE9130" FOREIGN KEY ("IDEA_ID")
    REFERENCES "SD2"."IDEA" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table IDEA_TRANSACTION
--------------------------------------------------------

  ALTER TABLE "SD2"."IDEA_TRANSACTION" ADD CONSTRAINT "FKIDEA_TRANS43216" FOREIGN KEY ("IDEA_ID")
    REFERENCES "SD2"."IDEA" ("ID") ENABLE;
  ALTER TABLE "SD2"."IDEA_TRANSACTION" ADD CONSTRAINT "FKIDEA_TRANS531549" FOREIGN KEY ("SELLER_ID")
    REFERENCES "SD2"."SDUSER" ("ID") ENABLE;
  ALTER TABLE "SD2"."IDEA_TRANSACTION" ADD CONSTRAINT "FKIDEA_TRANS615813" FOREIGN KEY ("BUYER_ID")
    REFERENCES "SD2"."SDUSER" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table NOTIFICATION
--------------------------------------------------------

  ALTER TABLE "SD2"."NOTIFICATION" ADD CONSTRAINT "FKNOTIFICATI560537" FOREIGN KEY ("USER_ID")
    REFERENCES "SD2"."SDUSER" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table TRANSACTION_QUEUE
--------------------------------------------------------

  ALTER TABLE "SD2"."TRANSACTION_QUEUE" ADD CONSTRAINT "FKTRANSACTIO111269" FOREIGN KEY ("USER_ID")
    REFERENCES "SD2"."SDUSER" ("ID") ENABLE;
  ALTER TABLE "SD2"."TRANSACTION_QUEUE" ADD CONSTRAINT "FKTRANSACTIO947303" FOREIGN KEY ("IDEA_ID")
    REFERENCES "SD2"."IDEA" ("ID") ENABLE;
--------------------------------------------------------
--  Ref Constraints for Table WATCHLIST
--------------------------------------------------------

  ALTER TABLE "SD2"."WATCHLIST" ADD CONSTRAINT "FKWATCHLIST224612" FOREIGN KEY ("SDUSER_ID")
    REFERENCES "SD2"."SDUSER" ("ID") ENABLE;
  ALTER TABLE "SD2"."WATCHLIST" ADD CONSTRAINT "FKWATCHLIST73607" FOREIGN KEY ("IDEA_ID")
    REFERENCES "SD2"."IDEA" ("ID") ENABLE;


--buyShares
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

--deleteIdea
CREATE OR REPLACE PROCEDURE deleteIdea(ideaId IN NUMBER) IS
  BEGIN
    UPDATE idea SET active=0, in_hall=0 WHERE id = ideaId;
    DELETE FROM idea_has_topic WHERE idea_id = ideaId;
    DELETE FROM idea_share WHERE idea_id = ideaId;
    DELETE FROM watchlist WHERE idea_id = ideaId;
    DELETE FROM transaction_queue WHERE idea_id = ideaId;
  END;

--faceAuth
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


--insertTopic
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

--queueTrigger
CREATE OR REPLACE TRIGGER tQueue
AFTER
UPDATE OF share_num ON transaction_queue
  BEGIN
    DELETE FROM transaction_queue WHERE share_num = 0;
  END;

--sharesTrigger
CREATE OR REPLACE TRIGGER tShares
AFTER
UPDATE OF parts ON idea_share
  BEGIN
    DELETE FROM idea_share WHERE parts = 0;
  END;

--submitIdea
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
