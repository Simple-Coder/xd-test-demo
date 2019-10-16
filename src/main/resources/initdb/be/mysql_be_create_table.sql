-- noinspection SqlDialectInspectionForFile

-- noinspection SqlNoDataSourceInspectionForFile

--接入银行表
DROP TABLE tbl_be_bank;
CREATE TABLE tbl_be_bank(
  uuid VARCHAR(32) PRIMARY KEY NOT NULL COMMENT '主键ID',
  bank_code VARCHAR(10) NOT NULL COMMENT '银行代码',
  bank_name VARCHAR(100) NOT NULL COMMENT '银行名称',
  bank_number VARCHAR(20) NOT NULL COMMENT '银行联行号',
  bank_address VARCHAR(200) NOT NULL COMMENT '开户行地址',
  bank_account VARCHAR(20) NOT NULL COMMENT '银行账号',
  bank_account_name VARCHAR(100) NOT NULL COMMENT '银行户名',
  bank_internal_account VARCHAR(20) NOT NULL COMMENT '内部账号',
  bank_status VARCHAR(1) NOT NULL COMMENT '银行状态，1：启用，0：停用',
  bank_cut_trade_code VARCHAR (100) COMMENT '银行日切暂停交易代码',
  bank_signing_date VARCHAR(10) NOT NULL COMMENT '签约日期，yyyy-mm-dd',
  bank_desc VARCHAR(200) COMMENT '银行描述',
  bank_client_code VARCHAR(20) NOT NULL COMMENT '银行客户号',
  bank_operator VARCHAR(20) NOT NULL COMMENT '操作员',
  bank_operator_pwd VARCHAR(20) NOT NULL COMMENT '操作员密码',
  bank_interface_address VARCHAR(100) NOT NULL COMMENT '接口地址，字段提示：协议为http时，填写【http://ip:port/上下文路径】；协议为tcp时填写【ip:port】',
  bank_message_type VARCHAR(10) NOT NULL COMMENT '报文类型，Json/xml',
  bank_warning_threshold VARCHAR(10) NOT NULL COMMENT '异常警告阈值',
  bank_termination_threshold VARCHAR(10) NOT NULL COMMENT '终止服务阈值',
  create_date VARCHAR(14) NOT NULL COMMENT '创建时间(创建时间yyyyMMddhhmmss)',
  last_update_user VARCHAR(20) NOT NULL COMMENT '编辑人(创建人或更新人用户id)',
  last_update_time  VARCHAR(14) COMMENT '编辑时间(更新时间yyyyMMddhhmmss)',
  bank_retainedsweep_time VARCHAR(10) COMMENT '余额归集时间'
) COMMENT='接入银行表'

--接入渠道表
DROP TABLE tbl_be_channel;
CREATE TABLE tbl_be_channel(
  uuid VARCHAR(32) PRIMARY KEY NOT NULL COMMENT '主键ID',
  channel_code VARCHAR(15) NOT NULL COMMENT '系统代码',
  channel_name VARCHAR(64) NOT NULL COMMENT '系统名称',
  channel_desc VARCHAR(200) COMMENT '渠道描述',
  create_date VARCHAR(14) NOT NULL COMMENT '创建时间(创建时间yyyyMMddhhmmss)',
  last_update_user VARCHAR(20) NOT NULL COMMENT '编辑人(创建人或更新人用户id)',
  last_update_time  VARCHAR(14) COMMENT '编辑时间(更新时间yyyyMMddhhmmss)'
) COMMENT='接入渠道表'

--交易配置表
DROP TABLE tbl_be_trade_cfg;
CREATE TABLE tbl_be_trade_cfg(
  uuid VARCHAR(32) PRIMARY KEY NOT NULL COMMENT '主键ID',
  trade_code VARCHAR(10) NOT NULL COMMENT '交易功能代码',
  bank_code VARCHAR(10) NOT NULL COMMENT '银行代码',
  trade_name VARCHAR(64) NOT NULL COMMENT '交易功能名称',
  trade_status VARCHAR(1) NOT NULL COMMENT '交易状态：1启用、2暂停、3禁用',
  loop_time INT NOT NULL COMMENT '循环间隔时间(s)',
  single_deal_num INT COMMENT '单次处理数量',
  thread_nums INT NOT NULL COMMENT '线程数',
  trade_open_time  VARCHAR(6) NOT NULL COMMENT '交易开始时间(hhmmss)',
  trade_close_time  VARCHAR(6) NOT NULL COMMENT '交易结束时间(hhmmss)',
  trade_last_run_time VARCHAR(14) COMMENT '最后运行时间(yyyyMMddhhmmss)',
  trade_desc VARCHAR(200) NOT NULL COMMENT '交易描述',
  trade_resp_err_code VARCHAR(100) COMMENT '交易失败返回码',
  switch_stop_flag VARCHAR(1) COMMENT '日切时是否停止运行',
  timed_exec_time VARCHAR(6) COMMENT '定时发起时间(hhmmss)',
  trade_pattern VARCHAR(1) COMMENT '交易形态',
  create_date VARCHAR(14) NOT NULL COMMENT '创建时间(创建时间yyyyMMddhhmmss)',
  last_update_user VARCHAR(20) NOT NULL COMMENT '编辑人(创建人或更新人用户id)',
  last_update_time  VARCHAR(14) COMMENT '编辑时间(更新时间yyyyMMddhhmmss)'
) COMMENT='交易配置表'
--银行接口配置表
DROP TABLE tbl_be_bank_inter;
CREATE TABLE tbl_be_bank_inter(
  uuid VARCHAR(32) PRIMARY KEY NOT NULL COMMENT '主键ID',
  bank_code VARCHAR(10) NOT NULL COMMENT '银行代码',
  inter_code VARCHAR(10) NOT NULL COMMENT '银行交易码',
  inter_name VARCHAR(10) NOT NULL COMMENT '接口名称',
  open_time VARCHAR(6) NOT NULL COMMENT '接口开放时间(例如，080000)',
  close_time VARCHAR(6) NOT NULL COMMENT '接口关闭时间(例如，235959)',
  create_time VARCHAR(14) NOT NULL COMMENT '创建时间(创建时间yyyyMMddhhmmss)',
  last_update_user VARCHAR(20) NOT NULL COMMENT '编辑人(创建人或更新人用户id)',
  last_update_time  VARCHAR(14) COMMENT '编辑时间(更新时间yyyyMMddhhmmss)',
  request_template VARCHAR(2000) COMMENT '请求报文模版'
) COMMENT='银行接口配置表'

--交易日志表
DROP TABLE tbl_be_record_log;
CREATE TABLE tbl_be_record_log(
  uuid VARCHAR(32) PRIMARY KEY NOT NULL COMMENT '主键ID',
  req_seq_no VARCHAR(32) NOT NULL COMMENT '请求流水号',
  req_time VARCHAR(17) NOT NULL COMMENT '请求时间',
  resp_time VARCHAR(17) NOT NULL COMMENT '响应时间',
  req_sys VARCHAR(10) NOT NULL COMMENT '请求系统',
  resp_sys VARCHAR(10) NOT NULL COMMENT '响应系统',
  req_accounts VARCHAR(32) COMMENT '请求账号',
  resp_accounts varchar(32) COMMENT '对手银行',
  original_seq_no VARCHAR(32) COMMENT '关联流水号（支付和归集状态刷新原交易流水号）',
  tran_code VARCHAR(20) NOT NULL COMMENT '交易码',
  req_msg VARCHAR(4000) NOT NULL COMMENT '请求报文',
  resp_msg VARCHAR(4000) NOT NULL COMMENT '响应报文',
  resp_code VARCHAR(10) NOT NULL COMMENT '响应码',
  server_ip VARCHAR(15) COMMENT '银企子系统ip(ipv4)',
  resp_desc VARCHAR(255) COMMENT '响应描述'
) COMMENT='交易日志表'

--接入银行与银企平台代码转换表
DROP TABLE tbl_be_bank_dict;
CREATE TABLE tbl_be_bank_dict(
  uuid VARCHAR(32) PRIMARY KEY NOT NULL COMMENT '主键ID',
  bank_code VARCHAR(10) NOT NULL COMMENT '接入银行代码',
  bank_dic_code VARCHAR(10) NOT NULL COMMENT '银行方代码',
  bank_dic_name VARCHAR(20) NOT NULL COMMENT '银行方代码名称',
  be_dic_code VARCHAR(10) NOT NULL COMMENT '银企代码',
  be_dic_name VARCHAR(20) NOT NULL COMMENT '银企代码名称',
  create_date VARCHAR(20) COMMENT '创建时间',
  last_update_user VARCHAR(20) COMMENT '操作人',
  last_update_time VARCHAR(20) COMMENT '更新时间'
) COMMENT='接入银行与银企平台代码转换表 '

drop table TBL_BE_TRADE_DEF;
--交易定义
CREATE TABLE TBL_BE_TRADE_DEF (
  uuid VARCHAR(32) PRIMARY KEY NOT NULL COMMENT '主键ID',
  trade_code VARCHAR(10) NOT NULL COMMENT '交易功能代码',
  trade_name VARCHAR(64) NOT NULL COMMENT '交易名称',
  trade_open_time  VARCHAR(6) NOT NULL COMMENT '交易开始时间(hhmmss)',
  trade_close_time  VARCHAR(6) NOT NULL COMMENT '交易结束时间(hhmmss)',
  trade_status VARCHAR(1) NOT NULL COMMENT '交易状态（1.启用、3.禁用）',
  create_date VARCHAR(14) NOT NULL COMMENT '创建时间(创建时间yyyyMMddhhmmss)',
  last_update_user VARCHAR(20) NOT NULL COMMENT '编辑人(创建人或更新人用户id)',
  last_update_time  VARCHAR(14) COMMENT '编辑时间(更新时间yyyyMMddhhmmss)'
)COMMENT='交易定义表';

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
