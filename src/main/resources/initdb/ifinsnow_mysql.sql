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
        ID VARCHAR(32) NOT NULL COMMENT '主键',
        DATA_TYPE_NO VARCHAR(20) NOT NULL COMMENT '数据类型编号',
        DATA_NO VARCHAR(20) NOT NULL COMMENT '数据编号',
        DATA_TYPE_NAME VARCHAR(60) COMMENT '数据类型名称',
        DATA_NO_LEN INTEGER COMMENT '数据编号长度',
        DATA_NAME VARCHAR(150) COMMENT '数据名称',
        LIMIT_FLAG CHAR(1) COMMENT '是否有上下限标志',
        HIGH_LIMIT VARCHAR(20) COMMENT '上限',
        LOW_LIMIT VARCHAR(40) COMMENT '下限',
        MISCFLGS VARCHAR(20) COMMENT '扩展字段',
        PRIMARY KEY (ID),
        INDEX IDX_IFS_DATA_DIC_0 (DATA_TYPE_NO),
        INDEX IDX_IFS_DATA_DIC_1 (DATA_NO)
    ) COMMENT='[框架自带]数据字典表';

CREATE TABLE
    IFS_SYS_INF
    (
        ID INTEGER NOT NULL COMMENT '主键',
        SYSTEM_NAME VARCHAR(20) COMMENT '系统简称',
        TBSDY CHAR(8) COMMENT '交易日期',
        BHDATE CHAR(8) COMMENT '批量日期',
        LBHDATE CHAR(8) COMMENT '上一批量日期',
        STATUS CHAR(1) COMMENT '状态 0=联机，1=批量',
        SYSTEM_TYPE CHAR(2) COMMENT '系统类型 默认1',
        MISCFLGS VARCHAR(20) COMMENT '扩展字段',
        PRIMARY KEY (ID)
    ) COMMENT='[框架自带]系统全局信息表';
    
CREATE TABLE
    IFS_MENU_INF
    (
        FUNCID VARCHAR(20) NOT NULL COMMENT '菜单编号(主键)',
        FUNCNAME VARCHAR(60) COMMENT '功能名称',
        PAGEPATH VARCHAR(250) COMMENT '访问路径(相对路径)',
        LOCATION INTEGER COMMENT '菜单排版（暂不使用）',
        ISDIRECTORY INTEGER COMMENT '是否为目录(1是；0否)',
        LASTDIRECTORY VARCHAR(20) COMMENT '上级节点编号，为空表示顶级',
        SHOWSEQ INTEGER COMMENT '显示顺序',
        FUNC_CLASS CHAR(1) COMMENT '暂不使用',
        FUNC_TYPE CHAR(1) COMMENT '菜单类型(0=菜单，2=功能)',
        WORKFLOW_FLAG CHAR(1) COMMENT '工作流标识(暂不使用)',
        UP_FUNC_CODE CHAR(10) COMMENT '上级菜单编号(暂不使用)',
        FUNC_DESC VARCHAR(60) COMMENT '功能描述',
        STATUS CHAR(1) COMMENT '状态(1有效，0无效)',
        MISCFLGS CHAR(20) COMMENT '扩展字段',
        MISC VARCHAR(1024) COMMENT '扩展字段',
        ICON_CLS VARCHAR(256) COMMENT '图标名称(字体图表)',
        PRIMARY KEY (FUNCID)
    ) COMMENT='[框架自带]系统菜单表';
    
CREATE TABLE
    IFS_SYS_PARAM
    (
        PARAM_ID VARCHAR(4) NOT NULL  COMMENT '参数分组(主键)',
        MAGIC_ID VARCHAR(20) NOT NULL COMMENT '参数编号(主键)',
        PARAM_START_TM CHAR(8) COMMENT '有效期开始日期(暂未使用)',
        PARAM_END_TM CHAR(8) COMMENT '有效期截止日期(暂未使用)',
        PARAM_CHANG_FLAG CHAR(1) COMMENT '参数修改标识(暂未使用)',
        PARAM_VALUE_TX VARCHAR(512) COMMENT '参数值',
        LAST_UPD_TLR VARCHAR(20) COMMENT '最后更新操作员',
        LAST_UPD_FUNC VARCHAR(20) COMMENT '最后修改交易码',
        LAST_UPD_DATE CHAR(8) COMMENT '最后更新日期',
        DESC0 VARCHAR(256) COMMENT '参数描述',
        ST CHAR(1) COMMENT '状态(暂未使用)',
        IS_LOCK CHAR(1) COMMENT '是否锁定(暂未使用)',
        IS_DEL CHAR(1) COMMENT '是否删除(暂未使用)',
        PRIMARY KEY (PARAM_ID, MAGIC_ID)
    ) COMMENT='[框架自带]系统参数表';

CREATE TABLE
    IFS_ORG
    (
        BRCODE VARCHAR(20) NOT NULL COMMENT '内部机构号',
        BRNO VARCHAR(20) NOT NULL COMMENT '外部机构号',
        BRNAME VARCHAR(60) COMMENT '机构名称',
        BRCLASS CHAR(1) COMMENT '机构级别(DDIC:105)',
        BRATTR CHAR(1) COMMENT '机构属性(暂未使用)',
        BLN_BRANCH_CLASS CHAR(1) COMMENT '分支机构级别(暂未使用)',
        BLN_BRANCH_BRCODE VARCHAR(20) COMMENT '分支机构号(暂未使用)',
        BLN_MANAGE_BRCODE VARCHAR(20) COMMENT '管理机构号',
        BLN_UP_BRCODE VARCHAR(20) COMMENT '上级机构号',
        TXN_BRCODE VARCHAR(20) COMMENT '交易机构号(暂未使用)',
        AUTHLVL CHAR(1) COMMENT '授权级别(暂未使用)',
        LINKMAN VARCHAR(20) COMMENT '联系人(暂未使用)',
        TELENO CHAR(20) COMMENT '联系电话(暂未使用)',
        ADDRESS VARCHAR(60) COMMENT '机构地址',
        POSTNO CHAR(6) COMMENT '邮编',
        OTHER_AREA_FLAG CHAR(1) COMMENT '异地行标识(暂未使用)',
        REGIONALISM CHAR(6) COMMENT '行政区划代码(暂未使用)',
        FINANCE_CODE CHAR(14) COMMENT '金融机构代码(暂未使用)',
        STATUS CHAR(1) COMMENT '有效标识(1有效0无效)',
        MISCFLGS CHAR(20) COMMENT '扩展字段',
        MISC VARCHAR(256) COMMENT '扩展字段',
        LAST_UPD_TLR VARCHAR(20) COMMENT '最后更新操作员',
        LAST_UPD_FUNC VARCHAR(20) COMMENT '最后修改交易码',
        LAST_UPD_DATE CHAR(8) COMMENT '最后更新日期',
        ST CHAR(1) COMMENT '记录状态(暂未使用)',
        IS_LOCK CHAR(1) COMMENT '是否锁定(暂未使用)',
        IS_DEL CHAR(1) COMMENT '是否删除(暂未使用)',
        PRIMARY KEY (BRCODE),
        INDEX IDX_IFS_ORG_0 (BRNO),
        INDEX IDX_IFS_ORG_1 (BLN_UP_BRCODE)
    ) COMMENT='[框架自带]机构信息表';       
    
CREATE TABLE
    IFS_STAFF
    (
        TLRNO VARCHAR(20) NOT NULL COMMENT '操作员编号(主键)',
        TLR_NAME VARCHAR(30) COMMENT '操作员名称',
        TLR_TYPE CHAR(1) COMMENT '操作员类型(暂未使用)',
		EMAIL VARCHAR(40) COMMENT '邮箱',
        BRCODE VARCHAR(20) COMMENT '所属机构号',
        PASSWORD VARCHAR(50) COMMENT '操作员密码',
        STATUS CHAR(1) COMMENT '状态 0-签退、1-签到、2-离职',
		ROLEID INTEGER COMMENT '默认岗位(暂未使用)',
		MSRNO INTEGER COMMENT '分区编号(暂未使用)',
		FLAG CHAR(1) COMMENT '有效标识(1-有效0-无效)',
		LOGIN_IP VARCHAR(20) COMMENT '登录IP',
		SESSION_ID VARCHAR(256) COMMENT '会话ID',
        CHEK_DPWD_FLG CHAR(1) COMMENT '是否校验动态码(暂未使用)',
		CREATE_DATE CHAR(8) COMMENT '创建日期',
        LASTACCESSTM CHAR(14) COMMENT '上次成功登录时间',
		LASTLOGOUTTM  CHAR(14) COMMENT '上次登出时间',
		LASTPWDCHGTM CHAR(14) COMMENT '上次密码修改时间',
		LASTFAILEDTM CHAR(14) COMMENT '上次登陆失败时间',
        PSWDERRCNT INTEGER COMMENT '密码输入错次数',
        TOTPSWDERRCNT INTEGER COMMENT '密码连续错误次数',
        PSWDERRDATE CHAR(8) COMMENT '密码输入错误日期(暂未使用)',
        PASSWDENC VARCHAR(10) COMMENT '密码加密算法',
        FAILMAXLOGIN INTEGER COMMENT '最大错误输入次数(暂未使用)',
        PASSWDCHGINTERVAL INTEGER COMMENT '密码更换间隔天数(暂未使用)',
        PASSWDWARNINTERVAL INTEGER COMMENT '密码更换提醒天数(暂未使用)',
        IS_LOCK CHAR(1) COMMENT '是否锁定(0-未锁定1-锁定)',
        LOCK_REASON VARCHAR(200) COMMENT '锁定原因',
        IS_LOCK_MODIFY CHAR(1) COMMENT '锁定修改(暂未使用)',
        CRT_DT CHAR(8) COMMENT '创建日期',
        LAST_UPD_TMS VARCHAR(14) COMMENT '最后更新时间',
        LAST_UPD_OPER VARCHAR(20) COMMENT '最后更新操作员',
        ST CHAR(1) COMMENT '记录状态(暂未使用)',
        PRIMARY KEY (TLRNO),
        INDEX IDX_IFS_STAFF_0 (BRCODE)
    ) COMMENT='[框架自带]用户信息表';
    
CREATE TABLE
    IFS_ROLE
    (
        ROLE_ID INTEGER NOT NULL COMMENT '岗位编号(主键)',
        ROLE_NAME VARCHAR(30) COMMENT '岗位名称',
        STATUS CHAR(1) COMMENT '状态 1有效,0无效',
        EFFECT_DATE CHAR(8) COMMENT '有效日期开始(暂未使用)',
        EXPIRE_DATE CHAR(8) COMMENT '有效日期结束(暂未使用)',
        BRCLASS CHAR(1) COMMENT '所属行级别(暂未使用)',
        MISCFLGS CHAR(20) COMMENT '扩展字段',
        MISC VARCHAR(256) COMMENT '扩展字段',
        ROLE_TYPE CHAR(1) COMMENT '岗位类型(暂未使用)',
        IS_LOCK CHAR(1) COMMENT '是否锁定(暂未使用)',
        CRT_DT CHAR(8) COMMENT '创建日期',
        LAST_UPD_TMS VARCHAR(14) COMMENT '最后更新时间',
        LAST_UPD_OPER VARCHAR(20) COMMENT '最后更新人',
        ST CHAR(1) COMMENT '记录状态(暂未使用)',
        PRIMARY KEY (ROLE_ID)
    ) COMMENT='[框架自带]岗位/角色表';   
    
CREATE TABLE
    IFS_RES_INF
    (
        ID CHAR(32) NOT NULL COMMENT '主键',
        ROLE_ID INTEGER COMMENT '岗位/角色编号',
        FUNCID VARCHAR(20) COMMENT '菜单编号',
        PRIMARY KEY (ID),
        INDEX IDX_IFS_RES_INF_0 (ROLE_ID)
    ) COMMENT='[框架自带]岗位/角色菜单关联表';   
    
CREATE TABLE
    IFS_STAFF_ROLE_REL
    (
        ID CHAR(32) NOT NULL COMMENT '主键',
        TLRNO VARCHAR(20) COMMENT '用户编号',
        ROLE_ID INTEGER COMMENT '岗位/角色编号',
        STATUS CHAR(1) COMMENT '状态 1有效0无效',
        PRIMARY KEY (ID),
        INDEX IDX_IFS_STAFF_ROLE_REL_0 (TLRNO)
    ) COMMENT='[框架自带]用户岗位/角色关联表';  

CREATE TABLE
    IFS_SER_NUM
    (
        ID CHAR(32) NOT NULL COMMENT '主键',
        VALUE_NO INTEGER DEFAULT 0 COMMENT '序号类型',
        VALUE_INDEX VARCHAR(40) COMMENT '序号索引',
        VALUE_CURR INTEGER COMMENT '当前值',
        MAX_VALUE INTEGER COMMENT '最大值',
        MIN_VALUE INTEGER COMMENT '最小值',
        PRIMARY KEY (ID),
        CONSTRAINT IDX_IFS_SER_NUM_0 UNIQUE (VALUE_NO, VALUE_INDEX)
    ) COMMENT='[框架自带]序号控制表';    

CREATE TABLE
    IFS_EXP_TASK_INF
    (
        TSK_ID CHAR(36) NOT NULL  COMMENT '主键',
        TSK_NM VARCHAR(50) COMMENT '任务名称',
        TSK_START_TMS CHAR(14) COMMENT '任务发起时间',
        TSK_START_OP VARCHAR(40) COMMENT '任务发起人',
        TSK_DESC VARCHAR(60) COMMENT '任务描述',
        TSK_PARAM1 VARCHAR(2048) COMMENT '任务参数1',
        TSK_PARAM2 VARCHAR(2048) COMMENT '任务参数2',
        TSK_OWNER VARCHAR(40) COMMENT '任务拥有者',
        TSK_END_TMS CHAR(14) COMMENT '任务完成时间',
        TSK_RUN_CLASS CHAR(2) COMMENT '任务运行类型，默认01',
        EXP_FILE_NM VARCHAR(128) COMMENT '导出文件路径',
        EXP_RCD_NUM INTEGER COMMENT '已导出记录笔数',
        EXP_RCD_SUM_NUM INTEGER COMMENT '导出记录总笔数',
        EXP_FILE_SIZE INTEGER COMMENT '导出文件大小（以byte为单位）',
        TSK_STAT CHAR(1) COMMENT '任务执行状态0:初始状态 1:准备执行 2:正在执行 3:任务完成 4执行失败',
        TSK_ERR_MSG VARCHAR(2048) COMMENT '运行失败原因',
        PRIMARY KEY (TSK_ID),
        INDEX IDX_IFS_EXP_TASK_INF_0 (TSK_START_OP)
    ) COMMENT='[框架自带]批量导出任务表';   

CREATE TABLE 
    IFS_CRON_JOB
    (
        ID VARCHAR(32) NOT NULL COMMENT '主键',
        JOBNO INTEGER NOT NULL COMMENT '任务编号(唯一)',
        PROCESS_FUNCTION VARCHAR(256) NOT NULL COMMENT '执行任务类',
        PROCESS_PARAM VARCHAR(512) COMMENT '任务参数,格式为:key1=值1;key2=值2;..',
        MAXPROC INTEGER COMMENT '最大次数(暂未使用)',
        RUNTIME CHAR(2) COMMENT '执行方式90=每日93=每月初97=每月某日98=每月末9N不运行',
        DAYS_OF_MONTH INTEGER COMMENT '每月某日',
        REPEAT_TIME INTEGER COMMENT '执行间隔(单位为分钟)',
        REPEAT_CNT INTEGER COMMENT '日执行次数(-1表示不控制)',
        START_TIME CHAR(6) COMMENT '开始时间,格式为:HHmmss',
        END_TIME CHAR(6) COMMENT '结束时间,格式为:HHmmss',
        LAST_RUN_TIME CHAR(14) COMMENT '最后的执行时间',
        DUE_TIME CHAR(14) COMMENT '到期时间(暂未使用)',
        SUC_FLAG CHAR(1) COMMENT '成功标识(暂未使用)',
        FAIL_FLAG CHAR(1) COMMENT '失败标识(暂未使用)',
        AUTO CHAR(1) COMMENT '是否自动(暂未使用)',
        LOCK_OWN VARCHAR(10) COMMENT '锁定用户(暂未使用)',
        DESC0 VARCHAR(128) COMMENT '任务描述0',
        DESC1 VARCHAR(1024) COMMENT '任务描述1',
        DESC2 VARCHAR(1024) COMMENT '任务描述2',
        TIMESTAMPS CHAR(14) COMMENT '设置时间',
        DUALCONTROL_LOCKSTATUS VARCHAR(1) COMMENT '(暂未使用)',
        PRIMARY KEY (ID),
        CONSTRAINT IDX_IFS_CRON_JOB_0 UNIQUE (JOBNO)
    ) COMMENT='[框架自带]定时任务配置表';
    
CREATE TABLE
    IFS_PWD_HIS
    (
        ID VARCHAR(32) NOT NULL COMMENT '主键',
        USERID VARCHAR(32) NOT NULL COMMENT '用户编号',
        PASSWORD VARCHAR(100) NOT NULL COMMENT '密码',
        ENC_MODE VARCHAR(10) COMMENT '加密方式',
        MODIFIED_TIME CHAR(14) NOT NULL COMMENT '修改时间',
        PRIMARY KEY (ID),
        INDEX IDX_IFS_PWD_HIS_0 (USERID)
    ) COMMENT='[框架自带]密码修改记录表';
    
CREATE TABLE
    IFS_STAFF_EXT
    (
        TLRNO VARCHAR(20) NOT NULL COMMENT '用户编号(主键)',
        TEL_NO VARCHAR(20) COMMENT '联系电话',
        EMAIL VARCHAR(128) COMMENT '邮箱地址',
        QQ_NO VARCHAR(40) COMMENT 'QQ号',
        WX_NO VARCHAR(40) COMMENT '微信号',
        HEAD_IMG MEDIUMTEXT COMMENT '头像',
        PRIMARY KEY (TLRNO)
    ) COMMENT='[框架自带]用户信息扩展表';    

CREATE TABLE
    IFS_LOGIN_LOG
    (
        LOG_ID VARCHAR(32) NOT NULL COMMENT '主键',
        TLR_NO VARCHAR(20) COMMENT '用户编号',
        BR_NO VARCHAR(20) COMMENT '外部机构编号',
        LOGIN_SUC_TM CHAR(14) COMMENT '登录成功时间',
        LOGIN_OUT_TM CHAR(14) COMMENT '退出时间',
        LOGIN_FAIL_TM CHAR(14) COMMENT '登录失败时间',
        LOGIN_ADDR VARCHAR(20) COMMENT '登录IP地址',
        LOGIN_REMARK VARCHAR(256) COMMENT '备注',
        SESSION_ID VARCHAR(256) COMMENT '会话ID',
        CRT_TM CHAR(14) COMMENT '创建时间',
        PRIMARY KEY (LOG_ID),
        INDEX IDX_IFS_LOGIN_LOG_0 (TLR_NO)
    ) COMMENT='[框架自带]登录日志表'; 
    
CREATE TABLE
    IFS_BIZ_LOG
    (
        ID CHAR(36) NOT NULL COMMENT '主键',
        LOG_ID CHAR(36) NOT NULL COMMENT '日志序号',
        TXN_DATE CHAR(8) NOT NULL COMMENT '交易日期',
        TXN_START_TIME CHAR(6) NOT NULL COMMENT '交易开始时间',
        TXN_END_TIME CHAR(6) NOT NULL COMMENT '交易结束时间',
        TXN_RUN_TIME INTEGER NOT NULL COMMENT '交易耗时',
        BRCODE VARCHAR(20) COMMENT '交易机构号',
        OPRCODE VARCHAR(20) COMMENT '交易操作员',
        IP_ADR VARCHAR(20) COMMENT '交易IP',
        FUNCID VARCHAR(20) COMMENT '菜单编号',
        OPRTXNCD VARCHAR(100) COMMENT '操作码',
        TXN_BIZ_LOG1 VARCHAR(2048) COMMENT '操作日志1',
        TXN_BIZ_LOG2 VARCHAR(2048) COMMENT '操作日志2',
        TXN_STATUS CHAR(2) COMMENT '交易状态99=失败02=成功',
        TXN_FAIL_LOG VARCHAR(2048) COMMENT '失败信息',
        PRIMARY KEY (ID)
    ) COMMENT='[框架自带]操作日志表';

CREATE TABLE 
    IFS_CRON_JOB_LOG
    (
        ID VARCHAR(32) NOT NULL COMMENT '主键',
        JOBNO INTEGER NOT NULL COMMENT '任务标识号',
        SUB_PROCE_FUNCTION VARCHAR(256) NOT NULL COMMENT '执行任务类',
        EXCUTE_TIME CHAR(14) COMMENT '执行时间',
        EXCUTE_RESULT CHAR(1) COMMENT '执行结果1-成功，0-失败',
        EXCUTE_OWN VARCHAR(10) COMMENT '执行的服务器实例(暂未使用)',
        FAIL_FLAG CHAR(1) COMMENT '失败标志位0-未失败，1-失败',
        SUC_FLAG CHAR(1) COMMENT '成功标志0-未成功，1-成功',
        EXCEPTION_MSG VARCHAR(512) COMMENT '失败信息',
        END_EXCUTE_FLAG CHAR(1) COMMENT '是否已结束当日执行1是0否',
        PRIMARY KEY (ID),
        INDEX IDX_IFS_CRON_JOB_LOG_0 (JOBNO, EXCUTE_TIME)
    ) COMMENT='[框架自带]定时任务执行日志表';