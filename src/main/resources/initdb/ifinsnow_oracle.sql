drop table IFS_DATA_DIC;
drop table IFS_SYS_INF;
drop table IFS_MENU_INF;
drop table IFS_SYS_PARAM;
drop table IFS_ORG;
drop table IFS_STAFF;
drop table IFS_ROLE;
drop table IFS_RES_INF;
drop table IFS_STAFF_ROLE_REL;
drop table IFS_SER_NUM;
drop table IFS_EXP_TASK_INF;
drop table IFS_CRON_JOB;
drop table IFS_PWD_HIS;
drop table IFS_STAFF_EXT;
drop table IFS_LOGIN_LOG;
drop table IFS_BIZ_LOG;
drop table IFS_CRON_JOB_LOG;

CREATE TABLE
    IFS_DATA_DIC
    (
        ID VARCHAR2(32) NOT NULL,
        DATA_TYPE_NO VARCHAR(20) NOT NULL,
        DATA_NO VARCHAR2(20) NOT NULL,
        DATA_TYPE_NAME VARCHAR2(60),
        DATA_NO_LEN INTEGER,
        DATA_NAME VARCHAR2(150),
        LIMIT_FLAG CHAR(1),
        HIGH_LIMIT VARCHAR2(20),
        LOW_LIMIT VARCHAR2(40),
        MISCFLGS VARCHAR2(20),
        PRIMARY KEY (ID)
    );
COMMENT ON TABLE IFS_DATA_DIC IS '[框架自带]数据字典表';
COMMENT ON COLUMN IFS_DATA_DIC.ID IS '主键';
COMMENT ON COLUMN IFS_DATA_DIC.DATA_TYPE_NO IS '数据类型编号';
COMMENT ON COLUMN IFS_DATA_DIC.DATA_NO IS '数据编号';
COMMENT ON COLUMN IFS_DATA_DIC.DATA_TYPE_NAME IS '数据类型名称';
COMMENT ON COLUMN IFS_DATA_DIC.DATA_NO_LEN IS '数据编号长度';
COMMENT ON COLUMN IFS_DATA_DIC.DATA_NAME IS '数据名称';
COMMENT ON COLUMN IFS_DATA_DIC.LIMIT_FLAG IS '是否有上下限标志';
COMMENT ON COLUMN IFS_DATA_DIC.HIGH_LIMIT IS '上限';
COMMENT ON COLUMN IFS_DATA_DIC.LOW_LIMIT IS '下限';
COMMENT ON COLUMN IFS_DATA_DIC.MISCFLGS IS '扩展字段';

CREATE TABLE
    IFS_SYS_INF
    (
        ID NUMBER(6) NOT NULL,
        SYSTEM_NAME VARCHAR2(20),
        TBSDY CHAR(8),
        BHDATE CHAR(8),
        LBHDATE CHAR(8),
        STATUS CHAR(1),
        SYSTEM_TYPE CHAR(2),
        MISCFLGS VARCHAR2(20),
        PRIMARY KEY (ID)
    );
COMMENT ON TABLE IFS_SYS_INF IS '[框架自带]系统全局信息表';
COMMENT ON COLUMN IFS_SYS_INF.ID IS '主键';
COMMENT ON COLUMN IFS_SYS_INF.SYSTEM_NAME IS '系统简称';
COMMENT ON COLUMN IFS_SYS_INF.TBSDY IS '交易日期';
COMMENT ON COLUMN IFS_SYS_INF.BHDATE IS '批量日期';
COMMENT ON COLUMN IFS_SYS_INF.LBHDATE IS '上一批量日期';
COMMENT ON COLUMN IFS_SYS_INF.STATUS IS '状态 0=联机，1=批量';
COMMENT ON COLUMN IFS_SYS_INF.SYSTEM_TYPE IS '系统类型 默认1';
COMMENT ON COLUMN IFS_SYS_INF.MISCFLGS IS '扩展字段';

CREATE TABLE
    IFS_MENU_INF
    (
        FUNCID VARCHAR2(20) NOT NULL,
        FUNCNAME VARCHAR2(60),
        PAGEPATH VARCHAR2(250),
        LOCATION INTEGER,
        ISDIRECTORY INTEGER,
        LASTDIRECTORY VARCHAR2(20),
        SHOWSEQ INTEGER,
        FUNC_CLASS CHAR(1),
        FUNC_TYPE CHAR(1),
        WORKFLOW_FLAG CHAR(1),
        UP_FUNC_CODE CHAR(10),
        FUNC_DESC VARCHAR2(60),
        STATUS CHAR(1),
        MISCFLGS CHAR(20),
        MISC VARCHAR2(1024),
        ICON_CLS VARCHAR2(256),
        PRIMARY KEY (FUNCID)
    );
COMMENT ON TABLE IFS_MENU_INF IS '[框架自带]系统菜单表';
COMMENT ON COLUMN IFS_MENU_INF.FUNCID IS '菜单编号(主键)';
COMMENT ON COLUMN IFS_MENU_INF.FUNCNAME IS '功能名称';
COMMENT ON COLUMN IFS_MENU_INF.PAGEPATH IS '访问路径(相对路径)';
COMMENT ON COLUMN IFS_MENU_INF.LOCATION IS '菜单排版(暂不使用)';
COMMENT ON COLUMN IFS_MENU_INF.ISDIRECTORY IS '是否为目录(1是；0否)';
COMMENT ON COLUMN IFS_MENU_INF.LASTDIRECTORY IS '上级节点编号，为空表示顶级';
COMMENT ON COLUMN IFS_MENU_INF.SHOWSEQ IS '显示顺序';
COMMENT ON COLUMN IFS_MENU_INF.FUNC_CLASS IS '暂不使用';
COMMENT ON COLUMN IFS_MENU_INF.FUNC_TYPE IS '菜单类型(0=菜单，2=功能)';
COMMENT ON COLUMN IFS_MENU_INF.WORKFLOW_FLAG IS '工作流标识(暂不使用)';
COMMENT ON COLUMN IFS_MENU_INF.UP_FUNC_CODE IS '上级菜单编号(暂不使用)';
COMMENT ON COLUMN IFS_MENU_INF.FUNC_DESC IS '功能描述';
COMMENT ON COLUMN IFS_MENU_INF.STATUS IS '状态(1有效，0无效)';
COMMENT ON COLUMN IFS_MENU_INF.MISCFLGS IS '扩展字段';
COMMENT ON COLUMN IFS_MENU_INF.MISC IS '扩展字段';
COMMENT ON COLUMN IFS_MENU_INF.ICON_CLS IS '图标名称(字体图表)';

CREATE TABLE
    IFS_SYS_PARAM
    (
        PARAM_ID VARCHAR2(4) NOT NULL,
        MAGIC_ID VARCHAR2(20) NOT NULL,
        PARAM_START_TM CHAR(8),
        PARAM_END_TM CHAR(8),
        PARAM_CHANG_FLAG CHAR(1),
        PARAM_VALUE_TX VARCHAR2(512),
        LAST_UPD_TLR VARCHAR2(20),
        LAST_UPD_FUNC VARCHAR2(20),
        LAST_UPD_DATE CHAR(8),
        DESC0 VARCHAR2(256),
        ST CHAR(1),
        IS_LOCK CHAR(1),
        IS_DEL CHAR(1),
        PRIMARY KEY (PARAM_ID, MAGIC_ID)
    );
COMMENT ON TABLE IFS_SYS_PARAM IS '[框架自带]系统参数表';
COMMENT ON COLUMN IFS_SYS_PARAM.PARAM_ID IS '参数分组(主键)';
COMMENT ON COLUMN IFS_SYS_PARAM.MAGIC_ID IS '参数编号(主键)';
COMMENT ON COLUMN IFS_SYS_PARAM.PARAM_START_TM IS '有效期开始日期(暂未使用)';
COMMENT ON COLUMN IFS_SYS_PARAM.PARAM_END_TM IS '有效期截止日期(暂未使用)';
COMMENT ON COLUMN IFS_SYS_PARAM.PARAM_CHANG_FLAG IS '参数修改标识(暂未使用)';
COMMENT ON COLUMN IFS_SYS_PARAM.PARAM_VALUE_TX IS '参数值';
COMMENT ON COLUMN IFS_SYS_PARAM.LAST_UPD_TLR IS '最后更新操作员';
COMMENT ON COLUMN IFS_SYS_PARAM.LAST_UPD_FUNC IS '最后修改交易码';
COMMENT ON COLUMN IFS_SYS_PARAM.LAST_UPD_DATE IS '最后更新日期';
COMMENT ON COLUMN IFS_SYS_PARAM.DESC0 IS '参数描述';
COMMENT ON COLUMN IFS_SYS_PARAM.ST IS '状态(暂未使用)';
COMMENT ON COLUMN IFS_SYS_PARAM.IS_LOCK IS '是否锁定(暂未使用)';
COMMENT ON COLUMN IFS_SYS_PARAM.IS_DEL IS '是否删除(暂未使用)';

CREATE TABLE
    IFS_ORG
    (
        BRCODE VARCHAR2(20) NOT NULL,
        BRNO VARCHAR2(20) NOT NULL,
        BRNAME VARCHAR2(60),
        BRCLASS CHAR(1),
        BRATTR CHAR(1),
        BLN_BRANCH_CLASS CHAR(1),
        BLN_BRANCH_BRCODE VARCHAR2(20),
        BLN_MANAGE_BRCODE VARCHAR2(20),
        BLN_UP_BRCODE VARCHAR2(20),
        TXN_BRCODE VARCHAR2(20),
        AUTHLVL CHAR(1),
        LINKMAN VARCHAR2(20),
        TELENO CHAR(20),
        ADDRESS VARCHAR2(60),
        POSTNO CHAR(6),
        OTHER_AREA_FLAG CHAR(1),
        REGIONALISM CHAR(6),
        FINANCE_CODE CHAR(14),
        STATUS CHAR(1),
        MISCFLGS CHAR(20),
        MISC VARCHAR2(256),
        LAST_UPD_TLR VARCHAR2(8),
        LAST_UPD_FUNC VARCHAR2(20),
        LAST_UPD_DATE CHAR(8),
		ST CHAR(1),
        IS_LOCK CHAR(1),
        IS_DEL CHAR(1),
        PRIMARY KEY (BRCODE)
    );
COMMENT ON TABLE IFS_ORG IS '[框架自带]机构信息表';
COMMENT ON COLUMN IFS_ORG.BRCODE IS '内部机构号';
COMMENT ON COLUMN IFS_ORG.BRNO IS '外部机构号';
COMMENT ON COLUMN IFS_ORG.BRNAME IS '机构名称';
COMMENT ON COLUMN IFS_ORG.BRCLASS IS '机构级别(DDIC:105)';
COMMENT ON COLUMN IFS_ORG.BRATTR IS '机构属性(暂未使用)';
COMMENT ON COLUMN IFS_ORG.BLN_BRANCH_CLASS IS '分支机构级别(暂未使用)';
COMMENT ON COLUMN IFS_ORG.BLN_BRANCH_BRCODE IS '分支机构号(暂未使用)';
COMMENT ON COLUMN IFS_ORG.BLN_MANAGE_BRCODE IS '管理机构号';
COMMENT ON COLUMN IFS_ORG.BLN_UP_BRCODE IS '上级机构号';
COMMENT ON COLUMN IFS_ORG.TXN_BRCODE IS '交易机构号(暂未使用)';
COMMENT ON COLUMN IFS_ORG.AUTHLVL IS '授权级别(暂未使用)';
COMMENT ON COLUMN IFS_ORG.LINKMAN IS '联系人(暂未使用)';
COMMENT ON COLUMN IFS_ORG.TELENO IS '联系电话(暂未使用)';
COMMENT ON COLUMN IFS_ORG.ADDRESS IS '机构地址';
COMMENT ON COLUMN IFS_ORG.POSTNO IS '邮编';
COMMENT ON COLUMN IFS_ORG.OTHER_AREA_FLAG IS '异地行标识(暂未使用)';
COMMENT ON COLUMN IFS_ORG.REGIONALISM IS '行政区划代码(暂未使用)';
COMMENT ON COLUMN IFS_ORG.FINANCE_CODE IS '金融机构代码(暂未使用)';
COMMENT ON COLUMN IFS_ORG.STATUS IS '有效标识(1有效0无效)';
COMMENT ON COLUMN IFS_ORG.MISCFLGS IS '扩展字段';
COMMENT ON COLUMN IFS_ORG.MISC IS '扩展字段';
COMMENT ON COLUMN IFS_ORG.LAST_UPD_TLR IS '最后更新操作员';
COMMENT ON COLUMN IFS_ORG.LAST_UPD_FUNC IS '最后修改交易码';
COMMENT ON COLUMN IFS_ORG.LAST_UPD_DATE IS '最后更新日期';
COMMENT ON COLUMN IFS_ORG.ST IS '记录状态(暂未使用)';
COMMENT ON COLUMN IFS_ORG.IS_LOCK IS '是否锁定(暂未使用)';
COMMENT ON COLUMN IFS_ORG.IS_DEL IS '是否删除(暂未使用)';
    
CREATE TABLE
    IFS_STAFF
    (
        TLRNO VARCHAR2(20) NOT NULL,
        TLR_NAME VARCHAR2(30),
        TLR_TYPE CHAR(1),
		EMAIL VARCHAR2(40),
        BRCODE VARCHAR2(20),
        PASSWORD VARCHAR2(50),
        STATUS CHAR(1),
		ROLEID INTEGER,
		MSRNO INTEGER,
		FLAG CHAR(1),
		LOGIN_IP VARCHAR2(20),
		SESSION_ID VARCHAR2(256),
        CHEK_DPWD_FLG CHAR(1),
		CREATE_DATE CHAR(8),
        LASTACCESSTM CHAR(14),
		LASTLOGOUTTM  CHAR(14),
		LASTPWDCHGTM CHAR(14),
		LASTFAILEDTM CHAR(14),
        PSWDERRCNT INTEGER,
        TOTPSWDERRCNT INTEGER,
        PSWDERRDATE CHAR(8),
        PASSWDENC VARCHAR2(10),
        FAILMAXLOGIN INTEGER,
        PASSWDCHGINTERVAL INTEGER,
        PASSWDWARNINTERVAL INTEGER,
        IS_LOCK CHAR(1),
        LOCK_REASON VARCHAR2(200),
        IS_LOCK_MODIFY CHAR(1),
        CRT_DT CHAR(8),
        LAST_UPD_TMS VARCHAR2(14),
        LAST_UPD_OPER VARCHAR2(20),
        ST CHAR(1),
        PRIMARY KEY (TLRNO)
    );
COMMENT ON TABLE IFS_STAFF IS '[框架自带]用户信息表';
COMMENT ON COLUMN IFS_STAFF.TLRNO IS '操作员编号(主键)';
COMMENT ON COLUMN IFS_STAFF.TLR_NAME IS '操作员名称';
COMMENT ON COLUMN IFS_STAFF.TLR_TYPE IS '操作员类型(暂未使用)';
COMMENT ON COLUMN IFS_STAFF.EMAIL IS '邮箱';
COMMENT ON COLUMN IFS_STAFF.BRCODE IS '所属机构号';
COMMENT ON COLUMN IFS_STAFF.PASSWORD IS '操作员密码';
COMMENT ON COLUMN IFS_STAFF.STATUS IS '状态 0-签退、1-签到、2-离职';
COMMENT ON COLUMN IFS_STAFF.ROLEID IS '默认岗位(暂未使用)';
COMMENT ON COLUMN IFS_STAFF.MSRNO IS '分区编号(暂未使用)';
COMMENT ON COLUMN IFS_STAFF.FLAG  IS '有效标识(1-有效0-无效)';
COMMENT ON COLUMN IFS_STAFF.LOGIN_IP  IS '登录IP';
COMMENT ON COLUMN IFS_STAFF.SESSION_ID  IS '会话ID';
COMMENT ON COLUMN IFS_STAFF.CHEK_DPWD_FLG  IS '是否校验动态码(暂未使用)';
COMMENT ON COLUMN IFS_STAFF.CREATE_DATE  IS '创建日期';
COMMENT ON COLUMN IFS_STAFF.LASTACCESSTM  IS '上次成功登录时间';
COMMENT ON COLUMN IFS_STAFF.LASTLOGOUTTM   IS '上次登出时间';
COMMENT ON COLUMN IFS_STAFF.LASTPWDCHGTM  IS '上次密码修改时间';
COMMENT ON COLUMN IFS_STAFF.LASTFAILEDTM  IS '上次登陆失败时间';
COMMENT ON COLUMN IFS_STAFF.PSWDERRCNT IS '密码输入错次数';
COMMENT ON COLUMN IFS_STAFF.TOTPSWDERRCNT IS '密码连续错误次数';
COMMENT ON COLUMN IFS_STAFF.PSWDERRDATE  IS '密码输入错误日期(暂未使用)';
COMMENT ON COLUMN IFS_STAFF.PASSWDENC  IS '密码加密算法';
COMMENT ON COLUMN IFS_STAFF.FAILMAXLOGIN IS '最大错误输入次数(暂未使用)';
COMMENT ON COLUMN IFS_STAFF.PASSWDCHGINTERVAL IS '密码更换间隔天数(暂未使用)';
COMMENT ON COLUMN IFS_STAFF.PASSWDWARNINTERVAL IS '密码更换提醒天数(暂未使用)';
COMMENT ON COLUMN IFS_STAFF.IS_LOCK  IS '是否锁定(0-未锁定1-锁定)';
COMMENT ON COLUMN IFS_STAFF.LOCK_REASON  IS '锁定原因';
COMMENT ON COLUMN IFS_STAFF.IS_LOCK_MODIFY  IS '锁定修改(暂未使用)';
COMMENT ON COLUMN IFS_STAFF.CRT_DT  IS '创建日期';
COMMENT ON COLUMN IFS_STAFF.LAST_UPD_TMS  IS '最后更新时间';
COMMENT ON COLUMN IFS_STAFF.LAST_UPD_OPER  IS '最后更新操作员';
COMMENT ON COLUMN IFS_STAFF.ST  IS '记录状态(暂未使用)';

CREATE TABLE
    IFS_ROLE
    (
        ROLE_ID INTEGER NOT NULL,
        ROLE_NAME VARCHAR2(30),
        STATUS CHAR(1),
        EFFECT_DATE CHAR(8),
        EXPIRE_DATE CHAR(8),
        BRCLASS CHAR(1),
        MISCFLGS CHAR(20),
        MISC VARCHAR2(256),
        ROLE_TYPE CHAR(1),
        IS_LOCK CHAR(1),
        CRT_DT CHAR(8),
        LAST_UPD_TMS VARCHAR2(14),
        LAST_UPD_OPER VARCHAR2(20),
        ST CHAR(1),
        PRIMARY KEY (ROLE_ID)
    );
COMMENT ON TABLE IFS_ROLE IS '[框架自带]岗位/角色表';
COMMENT ON COLUMN IFS_ROLE.ROLE_ID IS '岗位编号(主键)';
COMMENT ON COLUMN IFS_ROLE.ROLE_NAME  IS '岗位名称';
COMMENT ON COLUMN IFS_ROLE.STATUS  IS '状态 1有效;0无效';
COMMENT ON COLUMN IFS_ROLE.EFFECT_DATE  IS '有效日期开始(暂未使用)';
COMMENT ON COLUMN IFS_ROLE.EXPIRE_DATE  IS '有效日期结束(暂未使用)';
COMMENT ON COLUMN IFS_ROLE.BRCLASS  IS '所属行级别(暂未使用)';
COMMENT ON COLUMN IFS_ROLE.MISCFLGS  IS '扩展字段';
COMMENT ON COLUMN IFS_ROLE.MISC  IS '扩展字段';
COMMENT ON COLUMN IFS_ROLE.ROLE_TYPE  IS '岗位类型(暂未使用)';
COMMENT ON COLUMN IFS_ROLE.IS_LOCK  IS '是否锁定(暂未使用)';
COMMENT ON COLUMN IFS_ROLE.CRT_DT  IS '创建日期';
COMMENT ON COLUMN IFS_ROLE.LAST_UPD_TMS  IS '最后更新时间';
COMMENT ON COLUMN IFS_ROLE.LAST_UPD_OPER  IS '最后更新人';
COMMENT ON COLUMN IFS_ROLE.ST  IS '记录状态(暂未使用)';

CREATE TABLE
    IFS_RES_INF
    (
        ID CHAR(32) NOT NULL,
        ROLE_ID INTEGER,
        FUNCID VARCHAR2(20),
        PRIMARY KEY (ID)
    );
COMMENT ON TABLE IFS_RES_INF IS '[框架自带]岗位/角色菜单关联表';
COMMENT ON COLUMN IFS_RES_INF.ID IS '主键';
COMMENT ON COLUMN IFS_RES_INF.ROLE_ID  IS '岗位/角色编号';
COMMENT ON COLUMN IFS_RES_INF.FUNCID  IS '菜单编号';

CREATE TABLE
    IFS_STAFF_ROLE_REL
    (
        ID CHAR(32) NOT NULL,
        TLRNO VARCHAR2(20),
        ROLE_ID INTEGER,
        STATUS CHAR(1),
        PRIMARY KEY (ID)
    );
COMMENT ON TABLE IFS_STAFF_ROLE_REL IS '[框架自带]用户岗位/角色关联表';
COMMENT ON COLUMN IFS_STAFF_ROLE_REL.ID IS '主键';
COMMENT ON COLUMN IFS_STAFF_ROLE_REL.TLRNO  IS '用户编号';
COMMENT ON COLUMN IFS_STAFF_ROLE_REL.ROLE_ID  IS '岗位/角色编号';
COMMENT ON COLUMN IFS_STAFF_ROLE_REL.STATUS  IS '状态 1有效0无效';

CREATE TABLE
    IFS_SER_NUM
    (
        ID CHAR(32) NOT NULL,
        VALUE_NO INTEGER DEFAULT 0,
        VALUE_INDEX VARCHAR2(40),
        VALUE_CURR INTEGER,
        MAX_VALUE INTEGER,
        MIN_VALUE INTEGER,
        PRIMARY KEY (ID)
    );
COMMENT ON TABLE IFS_SER_NUM IS '[框架自带]序号控制表';
COMMENT ON COLUMN IFS_SER_NUM.ID IS '主键';
COMMENT ON COLUMN IFS_SER_NUM.VALUE_NO  IS '序号类型';
COMMENT ON COLUMN IFS_SER_NUM.VALUE_INDEX  IS '序号索引';
COMMENT ON COLUMN IFS_SER_NUM.VALUE_CURR  IS '当前值';
COMMENT ON COLUMN IFS_SER_NUM.MAX_VALUE  IS '最大值';
COMMENT ON COLUMN IFS_SER_NUM.MIN_VALUE  IS '最小值';

CREATE TABLE
    IFS_EXP_TASK_INF
    (
        TSK_ID CHAR(36) NOT NULL,
        TSK_NM VARCHAR2(50),
        TSK_START_TMS CHAR(14),
        TSK_START_OP VARCHAR2(40),
        TSK_DESC VARCHAR2(60),
        TSK_PARAM1 VARCHAR2(2048),
        TSK_PARAM2 VARCHAR2(2048),
        TSK_OWNER VARCHAR2(40),
        TSK_END_TMS CHAR(14),
        TSK_RUN_CLASS CHAR(2),
        EXP_FILE_NM VARCHAR2(128),
        EXP_RCD_NUM INTEGER,
        EXP_RCD_SUM_NUM INTEGER,
        EXP_FILE_SIZE INTEGER,
        TSK_STAT CHAR(1),
        TSK_ERR_MSG VARCHAR(2048),
        PRIMARY KEY (TSK_ID)
    );
COMMENT ON TABLE IFS_EXP_TASK_INF IS '[框架自带]批量导出任务表';
COMMENT ON COLUMN IFS_EXP_TASK_INF.TSK_ID IS '主键';
COMMENT ON COLUMN IFS_EXP_TASK_INF.TSK_NM IS '任务名称';
COMMENT ON COLUMN IFS_EXP_TASK_INF.TSK_START_TMS IS '任务发起时间';
COMMENT ON COLUMN IFS_EXP_TASK_INF.TSK_START_OP IS '任务发起人';
COMMENT ON COLUMN IFS_EXP_TASK_INF.TSK_DESC IS '任务描述';
COMMENT ON COLUMN IFS_EXP_TASK_INF.TSK_PARAM1 IS '任务参数1';
COMMENT ON COLUMN IFS_EXP_TASK_INF.TSK_PARAM2 IS '任务参数2';
COMMENT ON COLUMN IFS_EXP_TASK_INF.TSK_OWNER IS '任务拥有者';
COMMENT ON COLUMN IFS_EXP_TASK_INF.TSK_END_TMS IS '任务完成时间';
COMMENT ON COLUMN IFS_EXP_TASK_INF.TSK_RUN_CLASS IS '任务运行类型，默认01';
COMMENT ON COLUMN IFS_EXP_TASK_INF.EXP_FILE_NM IS '导出文件路径';
COMMENT ON COLUMN IFS_EXP_TASK_INF.EXP_RCD_NUM IS '已导出记录笔数';
COMMENT ON COLUMN IFS_EXP_TASK_INF.EXP_RCD_SUM_NUM IS '导出记录总笔数';
COMMENT ON COLUMN IFS_EXP_TASK_INF.EXP_FILE_SIZE IS '导出文件大小（以byte为单位）';
COMMENT ON COLUMN IFS_EXP_TASK_INF.TSK_STAT IS '任务执行状态0:初始状态 1:准备执行 2:正在执行 3:任务完成 4执行失败';
COMMENT ON COLUMN IFS_EXP_TASK_INF.TSK_ERR_MSG IS '运行失败原因';

CREATE TABLE 
    IFS_CRON_JOB
    (
        ID VARCHAR2(32) NOT NULL,
        JOBNO INTEGER NOT NULL,
        PROCESS_FUNCTION VARCHAR2(256) NOT NULL,
        PROCESS_PARAM VARCHAR2(512),
        MAXPROC INTEGER,
        RUNTIME CHAR(2),
        DAYS_OF_MONTH INTEGER,
        REPEAT_TIME NUMBER,
        REPEAT_CNT INTEGER,
        START_TIME CHAR(6),
        END_TIME CHAR(6),
        LAST_RUN_TIME CHAR(14),
        DUE_TIME CHAR(14),
        SUC_FLAG CHAR(1),
        FAIL_FLAG CHAR(1),
        AUTO CHAR(1),
        LOCK_OWN VARCHAR2(10),
        DESC0 VARCHAR2(128),
        DESC1 VARCHAR2(1024),
        DESC2 VARCHAR2(1024),
        TIMESTAMPS CHAR(14),
        DUALCONTROL_LOCKSTATUS VARCHAR2(1),
        PRIMARY KEY (ID)
    );
COMMENT ON TABLE IFS_CRON_JOB IS '[框架自带]定时任务配置表';
COMMENT ON COLUMN IFS_CRON_JOB.ID IS '主键';
COMMENT ON COLUMN IFS_CRON_JOB.JOBNO IS '任务编号(唯一)';
COMMENT ON COLUMN IFS_CRON_JOB.PROCESS_FUNCTION IS '执行任务类';
COMMENT ON COLUMN IFS_CRON_JOB.PROCESS_PARAM IS '任务参数;格式为:key1=值1;key2=值2;..';
COMMENT ON COLUMN IFS_CRON_JOB.MAXPROC IS '最大次数(暂未使用)';
COMMENT ON COLUMN IFS_CRON_JOB.RUNTIME IS '执行方式90=每日93=每月初97=每月某日98=每月末9N不运行';
COMMENT ON COLUMN IFS_CRON_JOB.DAYS_OF_MONTH IS '每月某日';
COMMENT ON COLUMN IFS_CRON_JOB.REPEAT_TIME IS '执行间隔(单位为分钟)';
COMMENT ON COLUMN IFS_CRON_JOB.REPEAT_CNT IS '日执行次数(-1表示不控制)';
COMMENT ON COLUMN IFS_CRON_JOB.START_TIME IS '开始时间;格式为:HHmmss';
COMMENT ON COLUMN IFS_CRON_JOB.END_TIME IS '结束时间;格式为:HHmmss';
COMMENT ON COLUMN IFS_CRON_JOB.LAST_RUN_TIME IS '最后的执行时间';
COMMENT ON COLUMN IFS_CRON_JOB.DUE_TIME IS '到期时间(暂未使用)';
COMMENT ON COLUMN IFS_CRON_JOB.SUC_FLAG IS '成功标识(暂未使用)';
COMMENT ON COLUMN IFS_CRON_JOB.FAIL_FLAG IS '失败标识(暂未使用)';
COMMENT ON COLUMN IFS_CRON_JOB.AUTO IS '是否自动(暂未使用)';
COMMENT ON COLUMN IFS_CRON_JOB.LOCK_OWN IS '锁定用户(暂未使用)';
COMMENT ON COLUMN IFS_CRON_JOB.DESC0 IS '任务描述0';
COMMENT ON COLUMN IFS_CRON_JOB.DESC1 IS '任务描述1';
COMMENT ON COLUMN IFS_CRON_JOB.DESC2 IS '任务描述2';
COMMENT ON COLUMN IFS_CRON_JOB.TIMESTAMPS IS '设置时间';
COMMENT ON COLUMN IFS_CRON_JOB.DUALCONTROL_LOCKSTATUS IS '(暂未使用)';

CREATE TABLE
    IFS_PWD_HIS
    (
        ID VARCHAR2(32) NOT NULL,
        USERID VARCHAR2(32) NOT NULL,
        PASSWORD VARCHAR2(100) NOT NULL,
        ENC_MODE VARCHAR2(10),
        MODIFIED_TIME CHAR(14) NOT NULL,
        PRIMARY KEY (ID)
    );
COMMENT ON TABLE IFS_PWD_HIS IS '[框架自带]密码修改记录表';
COMMENT ON COLUMN IFS_PWD_HIS.ID IS '主键';
COMMENT ON COLUMN IFS_PWD_HIS.USERID IS '用户编号';
COMMENT ON COLUMN IFS_PWD_HIS.PASSWORD IS '密码';
COMMENT ON COLUMN IFS_PWD_HIS.ENC_MODE IS '加密方式';
COMMENT ON COLUMN IFS_PWD_HIS.MODIFIED_TIME IS '修改时间';

CREATE TABLE
    IFS_STAFF_EXT
    (
        TLRNO VARCHAR2(20) NOT NULL,
        TEL_NO VARCHAR2(20),
        EMAIL VARCHAR2(128),
        QQ_NO VARCHAR2(40),
        WX_NO VARCHAR2(40),
        HEAD_IMG CLOB,
        PRIMARY KEY (TLRNO)
    );
COMMENT ON TABLE IFS_STAFF_EXT IS '用户扩展信息';
COMMENT ON COLUMN IFS_STAFF_EXT.TLRNO IS '用户编号(主键)';
COMMENT ON COLUMN IFS_STAFF_EXT.TEL_NO IS '联系电话';
COMMENT ON COLUMN IFS_STAFF_EXT.EMAIL IS '邮箱地址';
COMMENT ON COLUMN IFS_STAFF_EXT.QQ_NO IS 'QQ号';
COMMENT ON COLUMN IFS_STAFF_EXT.WX_NO IS '微信号';
COMMENT ON COLUMN IFS_STAFF_EXT.HEAD_IMG IS '头像';

CREATE TABLE
    IFS_LOGIN_LOG
    (
        LOG_ID VARCHAR2(32) NOT NULL,
        TLR_NO VARCHAR2(20),
        BR_NO VARCHAR2(20),
        LOGIN_SUC_TM CHAR(14),
        LOGIN_OUT_TM CHAR(14),
        LOGIN_FAIL_TM CHAR(14),
        LOGIN_ADDR VARCHAR2(20),
        LOGIN_REMARK VARCHAR2(256),
        SESSION_ID VARCHAR2(256),
        CRT_TM CHAR(14),
        PRIMARY KEY (LOG_ID)
    );
COMMENT ON TABLE IFS_LOGIN_LOG IS '[框架自带]登录日志表';
COMMENT ON COLUMN IFS_LOGIN_LOG.LOG_ID IS '主键';
COMMENT ON COLUMN IFS_LOGIN_LOG.TLR_NO IS '用户编号';
COMMENT ON COLUMN IFS_LOGIN_LOG.BR_NO IS '外部机构编号';
COMMENT ON COLUMN IFS_LOGIN_LOG.LOGIN_SUC_TM IS '登录成功时间';
COMMENT ON COLUMN IFS_LOGIN_LOG.LOGIN_OUT_TM IS '退出时间';
COMMENT ON COLUMN IFS_LOGIN_LOG.LOGIN_FAIL_TM IS '登录失败时间';
COMMENT ON COLUMN IFS_LOGIN_LOG.LOGIN_ADDR IS '登录IP地址';
COMMENT ON COLUMN IFS_LOGIN_LOG.LOGIN_REMARK IS '备注';
COMMENT ON COLUMN IFS_LOGIN_LOG.SESSION_ID IS '会话ID';
COMMENT ON COLUMN IFS_LOGIN_LOG.CRT_TM IS '创建时间';

CREATE TABLE
    IFS_BIZ_LOG
    (
        ID CHAR(36) NOT NULL,
        LOG_ID CHAR(36) NOT NULL,
        TXN_DATE CHAR(8) NOT NULL,
        TXN_START_TIME CHAR(6) NOT NULL,
        TXN_END_TIME CHAR(6) NOT NULL,
        TXN_RUN_TIME INTEGER NOT NULL,
        BRCODE VARCHAR2(20),
        OPRCODE VARCHAR2(20),
        IP_ADR VARCHAR2(20),
        FUNCID VARCHAR2(20),
        OPRTXNCD VARCHAR2(100),
        TXN_BIZ_LOG1 VARCHAR2(2048),
        TXN_BIZ_LOG2 VARCHAR2(2048),
        TXN_STATUS CHAR(2),
        TXN_FAIL_LOG VARCHAR2(2048),
        PRIMARY KEY (ID)
    );
COMMENT ON TABLE IFS_BIZ_LOG IS '[框架自带]操作日志表';
COMMENT ON COLUMN IFS_BIZ_LOG.ID IS '主键';
COMMENT ON COLUMN IFS_BIZ_LOG.LOG_ID IS '日志序号';
COMMENT ON COLUMN IFS_BIZ_LOG.TXN_DATE IS '交易日期';
COMMENT ON COLUMN IFS_BIZ_LOG.TXN_START_TIME IS '交易开始时间';
COMMENT ON COLUMN IFS_BIZ_LOG.TXN_END_TIME IS '交易结束时间';
COMMENT ON COLUMN IFS_BIZ_LOG.TXN_RUN_TIME IS '交易耗时';
COMMENT ON COLUMN IFS_BIZ_LOG.BRCODE IS '交易机构号';
COMMENT ON COLUMN IFS_BIZ_LOG.OPRCODE IS '交易操作员';
COMMENT ON COLUMN IFS_BIZ_LOG.IP_ADR IS '交易IP';
COMMENT ON COLUMN IFS_BIZ_LOG.FUNCID IS '菜单编号';
COMMENT ON COLUMN IFS_BIZ_LOG.OPRTXNCD IS '操作码';
COMMENT ON COLUMN IFS_BIZ_LOG.TXN_BIZ_LOG1 IS '操作日志1';
COMMENT ON COLUMN IFS_BIZ_LOG.TXN_BIZ_LOG2 IS '操作日志2';
COMMENT ON COLUMN IFS_BIZ_LOG.TXN_STATUS IS '交易状态99=失败02=成功';
COMMENT ON COLUMN IFS_BIZ_LOG.TXN_FAIL_LOG IS '失败信息';

CREATE TABLE 
    IFS_CRON_JOB_LOG
    (
        ID VARCHAR2(32) NOT NULL,
        JOBNO INTEGER NOT NULL,
        SUB_PROCE_FUNCTION VARCHAR2(256) NOT NULL,
        EXCUTE_TIME CHAR(14),
        EXCUTE_RESULT CHAR(1),
        EXCUTE_OWN VARCHAR2(10),
        FAIL_FLAG CHAR(1),
        SUC_FLAG CHAR(1),
        EXCEPTION_MSG VARCHAR2(512),
        END_EXCUTE_FLAG CHAR(1),
        PRIMARY KEY (ID)
    );
COMMENT ON TABLE IFS_CRON_JOB_LOG IS '[框架自带]定时任务执行日志表';
COMMENT ON COLUMN IFS_CRON_JOB_LOG.ID IS '主键';
COMMENT ON COLUMN IFS_CRON_JOB_LOG.JOBNO IS '任务标识号';
COMMENT ON COLUMN IFS_CRON_JOB_LOG.SUB_PROCE_FUNCTION IS '执行任务类';
COMMENT ON COLUMN IFS_CRON_JOB_LOG.EXCUTE_TIME IS '执行时间';
COMMENT ON COLUMN IFS_CRON_JOB_LOG.EXCUTE_RESULT IS '执行结果1-成功，0-失败';
COMMENT ON COLUMN IFS_CRON_JOB_LOG.EXCUTE_OWN IS '执行的服务器实例(暂未使用)';
COMMENT ON COLUMN IFS_CRON_JOB_LOG.FAIL_FLAG IS '失败标志位0-未失败，1-失败';
COMMENT ON COLUMN IFS_CRON_JOB_LOG.SUC_FLAG IS '成功标志0-未成功，1-成功';
COMMENT ON COLUMN IFS_CRON_JOB_LOG.EXCEPTION_MSG IS '失败信息';
COMMENT ON COLUMN IFS_CRON_JOB_LOG.END_EXCUTE_FLAG IS '是否已结束当日执行1是0否';

CREATE INDEX IDX_IFS_DATA_DIC_0 ON IFS_DATA_DIC (DATA_TYPE_NO);
CREATE INDEX IDX_IFS_DATA_DIC_1 ON IFS_DATA_DIC (DATA_NO);
CREATE INDEX IDX_IFS_ORG_0 ON IFS_ORG (BRNO);
CREATE INDEX IDX_IFS_ORG_1 ON IFS_ORG (BLN_UP_BRCODE);
CREATE INDEX IDX_IFS_STAFF_0 ON IFS_STAFF (BRCODE);
CREATE INDEX IDX_IFS_RES_INF_0 ON IFS_RES_INF (ROLE_ID);
CREATE INDEX IDX_IFS_STAFF_ROLE_REL_0 ON IFS_STAFF_ROLE_REL (TLRNO);
CREATE UNIQUE INDEX IDX_IFS_SER_NUM_0 ON IFS_SER_NUM (VALUE_NO, VALUE_INDEX);
CREATE INDEX IDX_IFS_EXP_TASK_INF_0 ON IFS_EXP_TASK_INF (TSK_START_OP);
CREATE UNIQUE INDEX IDX_IFS_CRON_JOB_0 ON IFS_CRON_JOB (JOBNO);
CREATE INDEX IDX_IFS_PWD_HIS_0 ON IFS_PWD_HIS (USERID);
CREATE INDEX IDX_IFS_LOGIN_LOG_0 ON IFS_LOGIN_LOG(TLR_NO);
CREATE INDEX IDX_IFS_CRON_JOB_LOG_0 ON IFS_CRON_JOB_LOG (JOBNO, EXCUTE_TIME);