drop table IFS_CAL_INF;
drop table IFS_BAT_JOB_INF;
drop table IFS_BAT_PROC_STEP;
drop table IFS_BAT_PROC_LOG;
drop table IFS_BAT_PROC_STATUS;
drop table IFS_BAT_WARNING_LOG;

CREATE TABLE
    IFS_CAL_INF
    (
        ID CHAR(32) NOT NULL COMMENT '主键',
        YEAR INTEGER COMMENT '年份',
        HOLIDAY_DEF VARCHAR(366) COMMENT '日历字符1工作日0节假日',
        LAST_UPD_OPER_ID VARCHAR(32) COMMENT '最后更新人',
        LAST_UPD_TIME CHAR(14) COMMENT '最后更新时间',
        PRIMARY KEY (ID),
        CONSTRAINT IDX_IFS_CAL_INF_0 UNIQUE (YEAR)
    ) COMMENT='[框架自带-批量功能]工作日设置表';    
    
CREATE TABLE
    IFS_BAT_JOB_INF
    (
        JOBNO INTEGER NOT NULL COMMENT '任务编号',
        PRE_JOBNO INTEGER COMMENT '前置任务编号',
        PRE_AUTORUN CHAR(1) COMMENT '前置任务是否自动运行1=自动0=不自动',
        SYSTEM_TYPE CHAR(2) COMMENT '系统类型,与IFS_SYS_INF表中类型SYSTEM_TYPE保持一致，默认1',
        RUNONLINE CHAR(2) COMMENT '作业是否允许在联机状态下运行0=不允许1=允许2=忽略联机状态',
        TIMESTAMPS CHAR(17) COMMENT '创建时间',
        MISCFLGS VARCHAR(256) COMMENT '扩展字段',
        MISC VARCHAR(256) COMMENT '扩展字段(任务描述)',
        PRIMARY KEY (JOBNO)
    ) COMMENT='[框架自带-批量功能]批量任务表';
    
CREATE TABLE
    IFS_BAT_PROC_STEP
    (
        ID INTEGER NOT NULL COMMENT '主键',
        JOBNO INTEGER NOT NULL COMMENT '任务编号',
        STEP INTEGER NOT NULL COMMENT '步骤编号',
        SUB_STEP INTEGER NOT NULL COMMENT '子步骤编号(SUB_FLAG=1,此处使用分行号)',
        PROCESS_FUNCTION VARCHAR(120) NOT NULL COMMENT '批量运行类',
        PROCESS_PARAM VARCHAR(512) COMMENT '运行参数',
        PROCESS_TLRNO VARCHAR(20) COMMENT '批量操作员',
        RUNTIME CHAR(2) COMMENT '运行时间9N=不运行90=每天91=每旬(10/20/月底)93=每月末94=每季末95=每半年96=每年41-47对应每周1-7或者为指定每月某天',
        SUB_FLAG CHAR(1) COMMENT '是否需要分行并行处理1=是0=否,配合IFS_ORG表BRCLASS=1使用，SUB_STEP等于机构编号',
        MAXPROC INTEGER COMMENT '该步骤最大运行线程数,默认为3',
        TEMP_FLAG CHAR(1) COMMENT '临时监控标志,1=有效0=无效,设置为有效标识该批量子步骤这次不运行',
        SUSPEND CHAR(1) COMMENT '批量运行出错可忽略标志1=可忽略0=不可忽略,1表示忽略后继续运行',
        AUTO CHAR(1) COMMENT '子步骤是否允许独立运行1=是0=否',
        TIMESTAMPS CHAR(17) COMMENT '创建时间',
        DESC0 VARCHAR(128) COMMENT '任务描述',
        DESC1 VARCHAR(1024) COMMENT '配合SUB_FLAG使用,过滤不需要处理子步骤号,格式为:!分行号1,分行号2,...',
        DESC2 VARCHAR(1024) COMMENT '扩展字段',
        REPORT_FLAG VARCHAR(10) COMMENT '报表功能(暂未使用)',
        REPORT_DATA_FLAG CHAR(1) COMMENT '报表功能(暂未使用)',
        REPEAT_CNT INTEGER COMMENT '批量步骤执行错误重试次数',
        PRIMARY KEY (ID)
    ) COMMENT='[框架自带-批量功能]批量任务步骤表';
    
CREATE TABLE
    IFS_BAT_PROC_LOG
    (
        ID INTEGER NOT NULL COMMENT '主键',
        BHDATE CHAR(8) NOT NULL COMMENT '批量日期',
        JOBNO INTEGER NOT NULL COMMENT '任务编号',
        STEP INTEGER NOT NULL COMMENT '步骤编号',
        SUB_STEP INTEGER NOT NULL COMMENT '子步骤编号',
        PROCESS_FUNCTION VARCHAR(120) COMMENT '批量运行类名称_JOBNO_STEP_SUB_STEP',
        PROCESSID INTEGER COMMENT '默认0',
        START_TIME CHAR(17) COMMENT '开始时间',
        END_TIME CHAR(17) COMMENT '结束时间',
        STATUS CHAR(1) COMMENT '运行状态0=未运行R=运行中E=已出错F=已完成',
        TIMESTAMPS CHAR(17) COMMENT '最后更新时间',
        ERR_MSG VARCHAR(1024) COMMENT '错误描述',
        DESC1 VARCHAR(1024) COMMENT '异常信息',
        DESC2 VARCHAR(1024) COMMENT '预留字段',
        PRIMARY KEY (ID),
        CONSTRAINT IDX_IFS_BAT_PROC_LOG_0 UNIQUE (BHDATE, JOBNO, STEP, SUB_STEP)
    ) COMMENT='[框架自带-批量功能]批量运行日志表';

CREATE TABLE
    IFS_BAT_PROC_STATUS
    (
        ID INTEGER NOT NULL COMMENT '主键',
        BHDATE CHAR(8) NOT NULL COMMENT '批量日期',
        JOBNO INTEGER NOT NULL COMMENT '任务编号',
        STEP INTEGER NOT NULL COMMENT '步骤编号(最后运行的步骤号)',
        SUB_STEP INTEGER NOT NULL COMMENT '子步骤编号,默认0',
        PROCESS_FUNCTION VARCHAR(120) COMMENT '批量运行类名称_JOBNO_STEP_SUB_STEP,默认空',
        PROCESSID INTEGER COMMENT '默认0',
        START_TIME CHAR(17) COMMENT '开始时间',
        END_TIME CHAR(17) COMMENT '结束时间',
        SUB_FLAG CHAR(1) COMMENT '是否分行并行处理1=是0=否,默认1',
        STATUS CHAR(1) COMMENT '运行状态0=未运行R=运行中E=已出错F=已完成',
        BRANCHLIST VARCHAR(1024) COMMENT '分行编号集合,多个逗号分隔',
        DESC1 VARCHAR(1024) COMMENT '预留字段,默认为空',
        DESC2 VARCHAR(1024) COMMENT '预留字段,默认为空',
        TIMESTAMPS CHAR(17) COMMENT '创建时间',
        PRIMARY KEY (ID),
       	CONSTRAINT IDX_IFS_BAT_PROC_STATUS_0 UNIQUE (BHDATE, JOBNO)
    ) COMMENT='[框架自带-批量功能]批量运行状态表';

CREATE TABLE
    IFS_BAT_WARNING_LOG
    (
        ID INTEGER NOT NULL COMMENT '主键',
        TXDATE CHAR(8) NOT NULL COMMENT '批量日期',
        JOBNO INTEGER NOT NULL COMMENT '任务编号',
        STEP INTEGER NOT NULL COMMENT '步骤编号',
        SUB_STEP INTEGER NOT NULL COMMENT '子步骤编号',
        CONTRACTNO CHAR(20) COMMENT '默认空',
        PERI INTEGER COMMENT '剩余重试次数,默认-1',
        MSG VARCHAR(1024) COMMENT '警告描述',
        LINE VARCHAR(1024) COMMENT '异常信息',
        TIMESTAMPS CHAR(17) COMMENT '创建时间',
        PRIMARY KEY (ID)
    ) COMMENT='[框架自带-批量功能]批量运行警告日志表';