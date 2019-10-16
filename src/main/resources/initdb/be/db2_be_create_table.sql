-- noinspection SqlDialectInspectionForFile

-- noinspection SqlNoDataSourceInspectionForFile

--接入银行表
DROP TABLE tbl_be_bank;
CREATE TABLE TBL_BE_BANK
    (
        UUID VARCHAR(32) NOT NULL,
        BANK_CODE VARCHAR(10) NOT NULL,
        BANK_NAME VARCHAR(100) NOT NULL,
        BANK_NUMBER VARCHAR(20) NOT NULL,
        BANK_ADDRESS VARCHAR(200) NOT NULL,
        BANK_ACCOUNT VARCHAR(20) NOT NULL,
        BANK_ACCOUNT_NAME VARCHAR(100) NOT NULL,
        BANK_INTERNAL_ACCOUNT VARCHAR(20) NOT NULL,
        BANK_STATUS VARCHAR(1) NOT NULL,
        BANK_CUT_TRADE_CODE VARCHAR(100),
        BANK_SIGNING_DATE VARCHAR(10) NOT NULL,
        BANK_DESC VARCHAR(200),
        BANK_CLIENT_CODE VARCHAR(20) NOT NULL,
        BANK_OPERATOR VARCHAR(20) NOT NULL,
        BANK_OPERATOR_PWD VARCHAR(20) NOT NULL,
        BANK_INTERFACE_ADDRESS VARCHAR(100) NOT NULL,
        BANK_MESSAGE_TYPE VARCHAR(10) NOT NULL,
        BANK_WARNING_THRESHOLD VARCHAR(10) NOT NULL,
        BANK_TERMINATION_THRESHOLD VARCHAR(10) NOT NULL,
        CREATE_DATE VARCHAR(14) NOT NULL,
        LAST_UPDATE_USER VARCHAR(20) NOT NULL,
        LAST_UPDATE_TIME VARCHAR(14),
        BANK_RETAINEDSWEEP_TIME VARCHAR(10),
        CONSTRAINT PK_TBL_BE_BANK PRIMARY KEY (UUID)
    );
COMMENT ON TABLE tbl_be_bank IS '接入银行表';
COMMENT ON COLUMN tbl_be_bank.uuid IS '主键ID';
COMMENT ON COLUMN tbl_be_bank.bank_code IS '银行代码';
COMMENT ON COLUMN tbl_be_bank.bank_name IS '银行名称';
COMMENT ON COLUMN tbl_be_bank.bank_number IS '银行联行号';
COMMENT ON COLUMN tbl_be_bank.bank_address IS '开户行地址';
COMMENT ON COLUMN tbl_be_bank.bank_account IS '银行账号';
COMMENT ON COLUMN tbl_be_bank.bank_account_name IS '银行户名';
COMMENT ON COLUMN tbl_be_bank.bank_internal_account IS '内部账号';
COMMENT ON COLUMN tbl_be_bank.bank_status IS '银行状态，1：启用，0：停用';
COMMENT ON COLUMN tbl_be_bank.bank_cut_trade_code IS '银行停用暂停的交易代码，用逗号分开';
COMMENT ON COLUMN tbl_be_bank.bank_signing_date IS '签约日期，yyyy-mm-dd';
COMMENT ON COLUMN tbl_be_bank.bank_desc IS '银行描述';
COMMENT ON COLUMN tbl_be_bank.bank_client_code IS '银行客户号';
COMMENT ON COLUMN tbl_be_bank.bank_operator IS '操作员';
COMMENT ON COLUMN tbl_be_bank.bank_operator_pwd IS '操作员密码';
COMMENT ON COLUMN tbl_be_bank.bank_message_type IS '报文类型，Json/xml';
COMMENT ON COLUMN tbl_be_bank.bank_warning_threshold IS '异常警告阈值';
COMMENT ON COLUMN tbl_be_bank.bank_termination_threshold IS '终止服务阈值';
COMMENT ON COLUMN tbl_be_bank.bank_interface_address IS '接口地址，字段提示：协议为http时，填写【http://ip:port/上下文路径】；协议为tcp时填写【ip:port】';
COMMENT ON COLUMN tbl_be_bank.create_date IS '创建时间(创建时间yyyyMMddhhmmss)';
COMMENT ON COLUMN tbl_be_bank.last_update_user IS '编辑人(创建人或更新人用户id)';
COMMENT ON COLUMN tbl_be_bank.last_update_time IS '编辑时间(更新时间yyyyMMddhhmmss)';
COMMENT ON COLUMN tbl_be_bank.bank_retainedsweep_time IS '每日留存归集时间(hhmmss)';

--接入渠道表
DROP TABLE tbl_be_channel;
CREATE TABLE tbl_be_channel(
  uuid VARCHAR(32) NOT NULL PRIMARY KEY,
  channel_code VARCHAR(15) NOT NULL,
  channel_name VARCHAR(64) NOT NULL,
  channel_desc VARCHAR(200),
  create_date VARCHAR(14) NOT NULL,
  last_update_user VARCHAR(20) NOT NULL,
  last_update_time  VARCHAR(14)
);
COMMENT ON TABLE tbl_be_channel IS '接入渠道表';
COMMENT ON COLUMN tbl_be_channel.uuid IS '主键ID';
COMMENT ON COLUMN tbl_be_channel.channel_code IS '系统代码';
COMMENT ON COLUMN tbl_be_channel.channel_name IS '系统名称';
COMMENT ON COLUMN tbl_be_channel.channel_desc IS '渠道描述';
COMMENT ON COLUMN tbl_be_channel.create_date IS '创建时间(创建时间yyyyMMddhhmmss)';
COMMENT ON COLUMN tbl_be_channel.last_update_user IS '编辑人(创建人或更新人用户id)';
COMMENT ON COLUMN tbl_be_channel.last_update_time IS '编辑时间(更新时间yyyyMMddhhmmss)';

--交易配置表
DROP TABLE tbl_be_trade_cfg;
CREATE TABLE TBL_BE_TRADE_CFG
    (
        UUID VARCHAR(32) NOT NULL,
        TRADE_CODE VARCHAR(10) NOT NULL,
        BANK_CODE VARCHAR(10) NOT NULL,
        TRADE_NAME VARCHAR(64) NOT NULL,
        TRADE_STATUS VARCHAR(1) NOT NULL,
        LOOP_TIME INTEGER NOT NULL,
        SINGLE_DEAL_NUM INTEGER,
        THREAD_NUMS INTEGER NOT NULL,
        TRADE_OPEN_TIME VARCHAR(6) NOT NULL,
        TRADE_CLOSE_TIME VARCHAR(6) NOT NULL,
        TRADE_LAST_RUN_TIME VARCHAR(14),
        TRADE_DESC VARCHAR(200),
        TRADE_RESP_ERR_CODE VARCHAR(100),
        SWITCH_STOP_FLAG VARCHAR(1),
        TIMED_EXEC_TIME VARCHAR(6),
        TRADE_PATTERN VARCHAR(1),
        CREATE_DATE VARCHAR(14) NOT NULL,
        LAST_UPDATE_USER VARCHAR(20) NOT NULL,
        LAST_UPDATE_TIME VARCHAR(14),
        PRIMARY KEY (UUID)
    );
COMMENT ON TABLE TBL_BE_TRADE_CFG
IS
    '交易配置表';
COMMENT ON COLUMN TBL_BE_TRADE_CFG.UUID
IS
    '主键ID';
COMMENT ON COLUMN TBL_BE_TRADE_CFG.TRADE_CODE
IS
    '交易功能代码';
COMMENT ON COLUMN TBL_BE_TRADE_CFG.BANK_CODE
IS
    '银行代码';
COMMENT ON COLUMN TBL_BE_TRADE_CFG.TRADE_NAME
IS
    '交易功能名称';
COMMENT ON COLUMN TBL_BE_TRADE_CFG.TRADE_STATUS
IS
    '交易状态：1启用、2暂停、3禁用';
COMMENT ON COLUMN TBL_BE_TRADE_CFG.LOOP_TIME
IS
    '循环间隔时间';
COMMENT ON COLUMN TBL_BE_TRADE_CFG.SINGLE_DEAL_NUM
IS
    '单次处理数量';
COMMENT ON COLUMN TBL_BE_TRADE_CFG.THREAD_NUMS
IS
    '线程数';
COMMENT ON COLUMN TBL_BE_TRADE_CFG.TRADE_OPEN_TIME
IS
    '交易开始时间(hhmmss)';
COMMENT ON COLUMN TBL_BE_TRADE_CFG.TRADE_CLOSE_TIME
IS
    '交易结束时间(hhmmss)';
COMMENT ON COLUMN TBL_BE_TRADE_CFG.TRADE_LAST_RUN_TIME
IS
    '最后运行时间(yyyyMMddhhmmss)';
COMMENT ON COLUMN TBL_BE_TRADE_CFG.TRADE_DESC
IS
    '交易描述';
COMMENT ON COLUMN TBL_BE_TRADE_CFG.TRADE_RESP_ERR_CODE
IS
    '交易失败返回码';
COMMENT ON COLUMN TBL_BE_TRADE_CFG.SWITCH_STOP_FLAG
IS
    '日切时是否停止运行';
COMMENT ON COLUMN TBL_BE_TRADE_CFG.TIMED_EXEC_TIME
IS
    '定时发起时间(hhmmss)';
COMMENT ON COLUMN TBL_BE_TRADE_CFG.TRADE_PATTERN
IS
    '交易形态';
COMMENT ON COLUMN TBL_BE_TRADE_CFG.CREATE_DATE
IS
    '创建时间(创建时间yyyyMMddhhmmss)';
COMMENT ON COLUMN TBL_BE_TRADE_CFG.LAST_UPDATE_USER
IS
    '编辑人(创建人或更新人用户id)';
COMMENT ON COLUMN TBL_BE_TRADE_CFG.LAST_UPDATE_TIME
IS
    '编辑时间(更新时间yyyyMMddhhmmss)';


--银行接口配置表
DROP TABLE tbl_be_bank_inter;
CREATE TABLE tbl_be_bank_inter(
  uuid VARCHAR(32) NOT NULL PRIMARY KEY,
  bank_code VARCHAR(10) NOT NULL,
  inter_code VARCHAR(10) NOT NULL,
  inter_name VARCHAR(10) NOT NULL,
  open_time VARCHAR(6) NOT NULL,
  close_time VARCHAR(6) NOT NULL,
  create_date VARCHAR(14) NOT NULL,
  last_update_user VARCHAR(20) NOT NULL,
  last_update_time  VARCHAR(14),
  request_template VARCHAR(2000)
);
COMMENT ON TABLE tbl_be_bank_inter IS '银行接口配置表';
COMMENT ON COLUMN tbl_be_bank_inter.uuid IS '主键ID';
COMMENT ON COLUMN tbl_be_bank_inter.bank_code IS '银行代码';
COMMENT ON COLUMN tbl_be_bank_inter.inter_code IS '银行交易码';
COMMENT ON COLUMN tbl_be_bank_inter.inter_name IS '接口名称';
COMMENT ON COLUMN tbl_be_bank_inter.open_time IS '接口开放时间(例如，080000)';
COMMENT ON COLUMN tbl_be_bank_inter.close_time IS '接口关闭时间(例如，235959)';
COMMENT ON COLUMN tbl_be_bank_inter.create_date IS '创建时间(创建时间yyyyMMddhhmmss)';
COMMENT ON COLUMN tbl_be_bank_inter.last_update_user IS '编辑人(创建人或更新人用户id)';
COMMENT ON COLUMN tbl_be_bank_inter.last_update_time IS '编辑时间(更新时间yyyyMMddhhmmss)';
COMMENT ON COLUMN tbl_be_bank_inter.request_template IS '请求报文模板';


--交易日志表
DROP TABLE TBL_BE_RECORD_LOG
CREATE TABLE TBL_BE_RECORD_LOG
    (
        UUID VARCHAR(32) NOT NULL,
        REQ_SEQ_NO VARCHAR(32),
        REQ_TIME VARCHAR(17),
        RESP_TIME VARCHAR (17),
        REQ_SYS VARCHAR(10),
        RESP_SYS VARCHAR(10),
        REQ_ACCOUNTS VARCHAR(32),
        RESP_ACCOUNTS varchar(32),
        ORIGINAL_SEQ_NO VARCHAR(32),
        TRAN_CODE VARCHAR(20),
        REQ_MSG VARCHAR(4000),
        RESP_MSG VARCHAR(4000),
        RESP_CODE VARCHAR(10),
        SERVER_IP VARCHAR(15),
        RESP_DESC VARCHAR(255),
        CONSTRAINT PK_TBL_BE_RECORD_LOG PRIMARY KEY (UUID)
    );
COMMENT ON TABLE TBL_BE_RECORD_LOG IS '交易日志表';
COMMENT ON COLUMN TBL_BE_RECORD_LOG.UUID IS '主键ID';
COMMENT ON COLUMN TBL_BE_RECORD_LOG.REQ_SEQ_NO IS '请求流水号';
COMMENT ON COLUMN TBL_BE_RECORD_LOG.REQ_TIME IS '请求时间';
COMMENT ON COLUMN TBL_BE_RECORD_LOG.RESP_TIME IS '响应时间';
COMMENT ON COLUMN TBL_BE_RECORD_LOG.REQ_SYS IS '请求系统';
COMMENT ON COLUMN TBL_BE_RECORD_LOG.RESP_SYS IS '响应系统';
COMMENT ON COLUMN TBL_BE_RECORD_LOG.TRAN_CODE IS '交易码';
COMMENT ON COLUMN TBL_BE_RECORD_LOG.REQ_ACCOUNTS IS '请求账号';
COMMENT ON COLUMN TBL_BE_RECORD_LOG.RESP_ACCOUNTS IS '对手银行';
COMMENT ON COLUMN TBL_BE_RECORD_LOG.ORIGINAL_SEQ_NO IS '关联流水号（支付和归集状态刷新原交易流水号）';
COMMENT ON COLUMN TBL_BE_RECORD_LOG.REQ_MSG IS '请求报文';
COMMENT ON COLUMN TBL_BE_RECORD_LOG.RESP_MSG IS '响应报文';
COMMENT ON COLUMN TBL_BE_RECORD_LOG.RESP_CODE IS '响应码';
COMMENT ON COLUMN TBL_BE_RECORD_LOG.SERVER_IP IS '银企子系统ip(ipv4)';
COMMENT ON COLUMN TBL_BE_RECORD_LOG.RESP_DESC IS '响应描述';

--接入银行与银企平台代码转换表
DROP TABLE tbl_be_bank_dict;
CREATE TABLE tbl_be_bank_dict(
  uuid VARCHAR(32) PRIMARY KEY NOT NULL,
  bank_code VARCHAR(10) NOT NULL,
  trade_code VARCHAR(10) NOT NULL,
  bank_dic_code VARCHAR(10) NOT NULL,
  bank_dic_name VARCHAR(20) NOT NULL,
  be_dic_code VARCHAR(10) NOT NULL,
  be_dic_name VARCHAR(20) NOT NULL,
  create_date VARCHAR(20),
  last_update_user VARCHAR(20),
  last_update_time VARCHAR(20)
);
COMMENT ON TABLE tbl_be_bank_dict IS '接入银行与银企平台代码转换表';
COMMENT ON COLUMN tbl_be_bank_dict.uuid IS '主键ID';
COMMENT ON COLUMN tbl_be_bank_dict.bank_code IS '接入银行代码';
COMMENT ON COLUMN tbl_be_bank_dict.trade_code IS '接入银行接口代码';
COMMENT ON COLUMN tbl_be_bank_dict.bank_dic_code IS '银行方代码';
COMMENT ON COLUMN tbl_be_bank_dict.bank_dic_name IS '银行方代码名称';
COMMENT ON COLUMN tbl_be_bank_dict.be_dic_code IS '银企代码';
COMMENT ON COLUMN tbl_be_bank_dict.be_dic_name IS '银企代码名称';
COMMENT ON COLUMN tbl_be_bank_dict.create_date IS '创建时间';
COMMENT ON COLUMN tbl_be_bank_dict.last_update_user IS '操作人';
COMMENT ON COLUMN tbl_be_bank_dict.last_update_time IS '更新时间';
INSERT INTO TBL_BE_BANK_DICT (UUID, BANK_CODE, TRADE_CODE, BANK_DIC_CODE, BANK_DIC_NAME, BE_DIC_CODE, BE_DIC_NAME, CREATE_DATE, LAST_UPDATE_USER, LAST_UPDATE_TIME) VALUES ('1', '1001', '0002', '2', '待授权', '', '', '201908051548', 'admin', '201908051548');
INSERT INTO TBL_BE_BANK_DICT (UUID, BANK_CODE, TRADE_CODE, BANK_DIC_CODE, BANK_DIC_NAME, BE_DIC_CODE, BE_DIC_NAME, CREATE_DATE, LAST_UPDATE_USER, LAST_UPDATE_TIME) VALUES ('10', '1001', '0002', 'C', '处理失败', '3461', '支付失败', '201908051548', 'admin', '201908051548');
INSERT INTO TBL_BE_BANK_DICT (UUID, BANK_CODE, TRADE_CODE, BANK_DIC_CODE, BANK_DIC_NAME, BE_DIC_CODE, BE_DIC_NAME, CREATE_DATE, LAST_UPDATE_USER, LAST_UPDATE_TIME) VALUES ('11', '1001', '0002', 'D', '状态未知', '', '', '201908051548', 'admin', '201908051548');
INSERT INTO TBL_BE_BANK_DICT (UUID, BANK_CODE, TRADE_CODE, BANK_DIC_CODE, BANK_DIC_NAME, BE_DIC_CODE, BE_DIC_NAME, CREATE_DATE, LAST_UPDATE_USER, LAST_UPDATE_TIME) VALUES ('12', '1001', '0002', 'E', '大额查证', '', '', '201908051548', 'admin', '201908051548');
INSERT INTO TBL_BE_BANK_DICT (UUID, BANK_CODE, TRADE_CODE, BANK_DIC_CODE, BANK_DIC_NAME, BE_DIC_CODE, BE_DIC_NAME, CREATE_DATE, LAST_UPDATE_USER, LAST_UPDATE_TIME) VALUES ('13', '1001', '0002', 'b', '汇款失败已冲账', '3461', '支付失败', '201908051548', 'admin', '201908051548');
INSERT INTO TBL_BE_BANK_DICT (UUID, BANK_CODE, TRADE_CODE, BANK_DIC_CODE, BANK_DIC_NAME, BE_DIC_CODE, BE_DIC_NAME, CREATE_DATE, LAST_UPDATE_USER, LAST_UPDATE_TIME) VALUES ('2', '1001', '0002', '3', '部分授权（一录两审时预审通过状态） ', '', '', '201908051548', 'admin', '201908051548');
INSERT INTO TBL_BE_BANK_DICT (UUID, BANK_CODE, TRADE_CODE, BANK_DIC_CODE, BANK_DIC_NAME, BE_DIC_CODE, BE_DIC_NAME, CREATE_DATE, LAST_UPDATE_USER, LAST_UPDATE_TIME) VALUES ('3', '1001', '0002', '4', '授权拒绝', '3461', '支付失败', '201908051548', 'admin', '201908051548');
INSERT INTO TBL_BE_BANK_DICT (UUID, BANK_CODE, TRADE_CODE, BANK_DIC_CODE, BANK_DIC_NAME, BE_DIC_CODE, BE_DIC_NAME, CREATE_DATE, LAST_UPDATE_USER, LAST_UPDATE_TIME) VALUES ('4', '1001', '0002', '6', '主机交易成功', '3112', '交易成功', '201908051548', 'admin', '201908051548');
INSERT INTO TBL_BE_BANK_DICT (UUID, BANK_CODE, TRADE_CODE, BANK_DIC_CODE, BANK_DIC_NAME, BE_DIC_CODE, BE_DIC_NAME, CREATE_DATE, LAST_UPDATE_USER, LAST_UPDATE_TIME) VALUES ('5', '1001', '0002', '7', '主机交易失败', '3461', '支付失败', '201908051548', 'admin', '201908051548');
INSERT INTO TBL_BE_BANK_DICT (UUID, BANK_CODE, TRADE_CODE, BANK_DIC_CODE, BANK_DIC_NAME, BE_DIC_CODE, BE_DIC_NAME, CREATE_DATE, LAST_UPDATE_USER, LAST_UPDATE_TIME) VALUES ('6', '1001', '0002', '8', '状态未知，没有收到后台系统返回的应答', '', '', '201908051548', 'admin', '201908051548');
INSERT INTO TBL_BE_BANK_DICT (UUID, BANK_CODE, TRADE_CODE, BANK_DIC_CODE, BANK_DIC_NAME, BE_DIC_CODE, BE_DIC_NAME, CREATE_DATE, LAST_UPDATE_USER, LAST_UPDATE_TIME) VALUES ('7', '1001', '0002', '9', '查证取消交易', '3461', '支付失败', '201908051548', 'admin', '201908051548');
INSERT INTO TBL_BE_BANK_DICT (UUID, BANK_CODE, TRADE_CODE, BANK_DIC_CODE, BANK_DIC_NAME, BE_DIC_CODE, BE_DIC_NAME, CREATE_DATE, LAST_UPDATE_USER, LAST_UPDATE_TIME) VALUES ('8', '1001', '0002', 'A', '支付系统正在处理', '', '', '201908051548', 'admin', '201908051548');
INSERT INTO TBL_BE_BANK_DICT (UUID, BANK_CODE, TRADE_CODE, BANK_DIC_CODE, BANK_DIC_NAME, BE_DIC_CODE, BE_DIC_NAME, CREATE_DATE, LAST_UPDATE_USER, LAST_UPDATE_TIME) VALUES ('9', '1001', '0002', 'B', '处理成功', '3112', '交易成功', '201908051548', 'admin', '201908051548');
INSERT INTO TBL_BE_BANK_DICT (UUID, BANK_CODE, TRADE_CODE, BANK_DIC_CODE, BANK_DIC_NAME, BE_DIC_CODE, BE_DIC_NAME, CREATE_DATE, LAST_UPDATE_USER, LAST_UPDATE_TIME) VALUES ('b06ab9c5c9144c0f83e2191d8a05fb04', '1001', '0005', 'E', '大额查证', '3482', '待查证', '201908051548', 'admin', '201908051548');
INSERT INTO TBL_BE_BANK_DICT (UUID, BANK_CODE, TRADE_CODE, BANK_DIC_CODE, BANK_DIC_NAME, BE_DIC_CODE, BE_DIC_NAME, CREATE_DATE, LAST_UPDATE_USER, LAST_UPDATE_TIME) VALUES ('b06ab9c5c9144c0f83e2191d8a05fb05', '1001', '0005', '2', '待授权', '3482', '待查证', '201908051548', 'admin', '201908051548');
INSERT INTO TBL_BE_BANK_DICT (UUID, BANK_CODE, TRADE_CODE, BANK_DIC_CODE, BANK_DIC_NAME, BE_DIC_CODE, BE_DIC_NAME, CREATE_DATE, LAST_UPDATE_USER, LAST_UPDATE_TIME) VALUES ('b06ab9c5c9144c0f83e2191d8a05fb06', '1001', '0005', '3', '部分授权（一录两审时预审通过状态）', '3482', '待查证', '201908051548', 'admin', '201908051548');
INSERT INTO TBL_BE_BANK_DICT (UUID, BANK_CODE, TRADE_CODE, BANK_DIC_CODE, BANK_DIC_NAME, BE_DIC_CODE, BE_DIC_NAME, CREATE_DATE, LAST_UPDATE_USER, LAST_UPDATE_TIME) VALUES ('b06ab9c5c9144c0f83e2191d8a05fb07', '1001', '0005', '4', '授权拒绝', '3485', '归集失败', '201908051548', 'admin', '201908051548');
INSERT INTO TBL_BE_BANK_DICT (UUID, BANK_CODE, TRADE_CODE, BANK_DIC_CODE, BANK_DIC_NAME, BE_DIC_CODE, BE_DIC_NAME, CREATE_DATE, LAST_UPDATE_USER, LAST_UPDATE_TIME) VALUES ('b06ab9c5c9144c0f83e2191d8a05fb09', '1001', '0005', '6', '主机交易成功', '3484', '归集成功', '201908051548', 'admin', '201908051548');
INSERT INTO TBL_BE_BANK_DICT (UUID, BANK_CODE, TRADE_CODE, BANK_DIC_CODE, BANK_DIC_NAME, BE_DIC_CODE, BE_DIC_NAME, CREATE_DATE, LAST_UPDATE_USER, LAST_UPDATE_TIME) VALUES ('b06ab9c5c9144c0f83e2191d8a05fb10', '1001', '0005', '7', '主机交易失败', '3485', '归集失败', '201908051548', 'admin', '201908051548');
INSERT INTO TBL_BE_BANK_DICT (UUID, BANK_CODE, TRADE_CODE, BANK_DIC_CODE, BANK_DIC_NAME, BE_DIC_CODE, BE_DIC_NAME, CREATE_DATE, LAST_UPDATE_USER, LAST_UPDATE_TIME) VALUES ('b06ab9c5c9144c0f83e2191d8a05fb11', '1001', '0005', '8', '状态未知，没有收到后台系统返回的应答', '3481', '等待响应', '201908051548', 'admin', '201908051548');

--核心交易状态表
CREATE TABLE BCI_TRADE_STATUS(
        UUID VARCHAR(32) PRIMARY KEY NOT NULL,
        CORE_STATUS VARCHAR(1) NOT NULL,
        BE_STATUS VARCHAR(1) NOT NULL,
        CORE_STOP_TIME VARCHAR(14),
        CORE_START_TIME VARCHAR(14),
        BE_STOP_TIME VARCHAR(14),
        BE_START_TIME VARCHAR(14),
        SYS_DATE VARCHAR (8) NOT NULL
);
COMMENT ON TABLE BCI_TRADE_STATUS IS '交易状态表';
COMMENT ON COLUMN BCI_TRADE_STATUS.uuid IS '主键ID';
COMMENT ON COLUMN BCI_TRADE_STATUS.CORE_STATUS IS '核心交易状态：1启用，0停用';
COMMENT ON COLUMN BCI_TRADE_STATUS.BE_STATUS IS '银企平台交易状态：1启用，0停用';
COMMENT ON COLUMN BCI_TRADE_STATUS.CORE_STOP_TIME IS '核心停用的时间戳yyyyMMddhhmmss（例如20190826182230）';
COMMENT ON COLUMN BCI_TRADE_STATUS.CORE_START_TIME IS '核心启用的时间戳yyyyMMddhhmmss（例如20190826182230）';
COMMENT ON COLUMN BCI_TRADE_STATUS.BE_STOP_TIME IS '银企平台停用的时间戳yyyyMMddhhmmss（例如20190826182230）';
COMMENT ON COLUMN BCI_TRADE_STATUS.BE_START_TIME IS '银企平台启用的时间戳yyyyMMddhhmmss（例如20190826182230）';
COMMENT ON COLUMN BCI_TRADE_STATUS.SYS_DATE IS '系统日期yyyyMMdd（例如20190826）';

--交易定义表
DROP TABLE tbl_be_trade_def;
CREATE TABLE tbl_be_trade_def(
  uuid VARCHAR(32) NOT NULL PRIMARY KEY,
  trade_code VARCHAR(10) NOT NULL,
  trade_name VARCHAR(64) NOT NULL,
  trade_open_time  VARCHAR(6) NOT NULL,
  trade_close_time  VARCHAR(6) NOT NULL,
  trade_status VARCHAR(1) NOT NULL,
  create_date VARCHAR(14),
  last_update_user VARCHAR(20),
  last_update_time  VARCHAR(14)
);
COMMENT ON TABLE tbl_be_trade_def IS '交易定义表';
COMMENT ON COLUMN tbl_be_trade_def.uuid IS '主键ID';
COMMENT ON COLUMN tbl_be_trade_def.trade_code IS '交易功能代码';
COMMENT ON COLUMN tbl_be_trade_def.trade_name IS '交易功能名称';
COMMENT ON COLUMN tbl_be_trade_def.trade_open_time IS '交易开始时间(hhmmss)';
COMMENT ON COLUMN tbl_be_trade_def.trade_close_time IS '交易结束时间(hhmmss)';
COMMENT ON COLUMN tbl_be_trade_def.trade_status IS '交易状态（1.启用、3.禁用）';
COMMENT ON COLUMN tbl_be_trade_def.create_date IS '创建时间(创建时间yyyyMMddhhmmss)';
COMMENT ON COLUMN tbl_be_trade_def.last_update_user IS '编辑人(创建人或更新人用户id)';
COMMENT ON COLUMN tbl_be_trade_def.last_update_time IS '编辑时间(更新时间yyyyMMddhhmmss)';


drop table IFS_CAL_INF;
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

DROP TABLE IFS_CAL_INF;
CREATE TABLE IFS_CAL_INF(
  ID VARCHAR(32) NOT NULL PRIMARY KEY,
  YEAR INTEGER NOT NULL,
  HOLIDAY_DEF VARCHAR(366) NOT NULL,
  LAST_UPD_OPER_ID  VARCHAR(32) NOT NULL,
  LAST_UPD_TIME  CHAR(14) NOT NULL
);
COMMENT ON TABLE IFS_CAL_INF IS '[框架自带-批量功能]工作日设置表';
COMMENT ON COLUMN IFS_CAL_INF.id IS '主键';
COMMENT ON COLUMN IFS_CAL_INF.YEAR IS '年份';
COMMENT ON COLUMN IFS_CAL_INF.HOLIDAY_DEF IS '日历字符1工作日0节假日';
COMMENT ON COLUMN IFS_CAL_INF.LAST_UPD_OPER_ID IS '最后更新人';
COMMENT ON COLUMN IFS_CAL_INF.LAST_UPD_TIME IS '最后更新时间';
