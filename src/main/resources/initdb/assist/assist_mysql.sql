drop table IFS_NATION_INF;
drop table IFS_CUR_INF;
drop table IFS_CUR_RATE;

CREATE TABLE
    IFS_NATION_INF
    (
        NATIONREGION_CODE VARCHAR(10) NOT NULL COMMENT '国家/地区代码',
        CHINA_NAME VARCHAR(128) COMMENT '中文全称',
        NATIONREGION_NUMBER VARCHAR(10) COMMENT '国家/地区数字代码',
        CHINA_SHORT_NAME VARCHAR(128) COMMENT '中文简称',
        ENG_NAME VARCHAR(128) COMMENT '英文全称',
        ENG_SHORT_NAME VARCHAR(128) COMMENT '英文简称',
        ST CHAR(1) COMMENT '记录状态(暂未使用)',
        IS_LOCK CHAR(1) COMMENT '是否锁定(暂未使用)',
        IS_DEL CHAR(1) COMMENT '是否删除(暂未使用)',
        CRT_DT CHAR(8) COMMENT '创建日期',
        LAST_UPD_TMS VARCHAR(14) COMMENT '最后更新时间',
        LAST_UPD_OPER VARCHAR(20) COMMENT '最后更新人',
        PRIMARY KEY (NATIONREGION_CODE)
    ) COMMENT='[框架自带-辅助功能]国家地区表';
    
CREATE TABLE
    IFS_CUR_INF
    (
        CURCD VARCHAR(6) NOT NULL COMMENT '币种货币代码',
        CNNAME VARCHAR(52) COMMENT '币种中文名称',
        ST CHAR(1) COMMENT '记录状态(暂未使用)',
        IS_LOCK CHAR(1) COMMENT '是否锁定(暂未使用)',
        IS_DEL CHAR(1) COMMENT '是否删除(暂未使用)',
        CREATE_DATE CHAR(8) COMMENT '创建日期',
        LAST_UPD_DATE CHAR(8) COMMENT '最后更新日期',
        LAST_UPD_TLR VARCHAR(20) COMMENT '最后更新人',
        SHOWSEQ INTEGER COMMENT '显示顺序',
        ISUSD CHAR(1) COMMENT '是否使用1是0否',
        ENNAME VARCHAR(20) COMMENT '币种英文名称',
        CURSYMBOL VARCHAR(20) COMMENT '币种符号',
        CREATE_TLR VARCHAR(20) COMMENT '创建人员',
        BASE_UNIT VARCHAR(4) COMMENT '基本单位',
        MIN_UNIT VARCHAR(4) COMMENT '最小单位',
        CUR_EDICD CHAR(3) COMMENT 'EDI币种代码',
        CUR_EDINAME VARCHAR(25) COMMENT 'EDI币种名称',
        CURNO VARCHAR(6) COMMENT '外部币种代码',
        DRATEDAYS CHAR(1) COMMENT '日利息计算天数DDIC360',
        PRIMARY KEY (CURCD)
    ) COMMENT='[框架自带-辅助功能]币种信息表';
    
CREATE TABLE
    IFS_CUR_RATE
    (
        ID VARCHAR(32) NOT NULL COMMENT '主键',
        CURCD CHAR(3) NOT NULL COMMENT '币种',
        CURRRATE_DATE CHAR(8) NOT NULL COMMENT '汇率日期',
        LAST_UPD_DATE CHAR(8) COMMENT '最后更新日期',
        LAST_UPD_TLR VARCHAR(8) COMMENT '最后更新人',
        TO_CURCD CHAR(3) NOT NULL COMMENT '兑换币种',
        BUY_RATE DECIMAL(16,3) COMMENT '买入价',
        SELL_RATE DECIMAL(16,3) COMMENT '卖出价',
        EXCHG_RATE DECIMAL(16,3) COMMENT '中间价',
        PRIMARY KEY (ID)
    ) COMMENT='[框架自带-辅助功能]币种汇率信息表';