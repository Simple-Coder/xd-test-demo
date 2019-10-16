-- 导出、清理表
CREATE TABLE
    IFS_BAT_CLEAR
    (
        TABLE_SEQ INTEGER NOT NULL,
        TABLE_ENG VARCHAR(30) NOT NULL,
        TABLE_CHN VARCHAR(60) NOT NULL,
        CLEAN_INTERVAL VARCHAR(30) NOT NULL,
        RUNTIME CHAR(2) NOT NULL,
        DATE_FIELD VARCHAR(30),
        CLEAR_CONDITION VARCHAR(200),
        COMMIT_CNT INTEGER NOT NULL,
        FLAG CHAR(1) NOT NULL,
        JOBNO INTEGER NOT NULL,
        MODE_TYPE CHAR(1),
        BACKUP_FLAG CHAR(1) NOT NULL,
        DESC_TX VARCHAR(1024),
        LAST_UPD_TS CHAR(17),
        PRIMARY KEY (TABLE_SEQ)
    );
--批量运行断点(用于导出、清理表功能）
CREATE TABLE
    IFS_BAT_PROC
    (
        BHDATE CHAR(8) NOT NULL,
        JOBNO INTEGER NOT NULL,
        STEP INTEGER NOT NULL,
        SUB_STEP INTEGER NOT NULL,
        RESUME_POINT CHAR(60),
        PROC_VALUE VARCHAR(1024),
        PRIMARY KEY (BHDATE, JOBNO, STEP, SUB_STEP)
    );    