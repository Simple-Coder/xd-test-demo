-- JOB[5] 5表示为任务表配置JOBNO，保留关键字 SD:日切批量(批量开始执行，IFS_SYS_INF表中批量日期切换为下一日，状态修改为批量)，SO：联机批量（批量任务结束，IFS_SYS_INF表中状态修改为联机）
-- 10 00 * * * 表示任务自动运行时间(RUN_MODE=1),格式为[分钟0-59] [小时0-23]，当前表达式为零点10分
--INSERT INTO IFS_SYS_PARAM (PARAM_ID, MAGIC_ID, PARAM_START_TM, PARAM_END_TM, PARAM_CHANG_FLAG, PARAM_VALUE_TX, LAST_UPD_TLR, LAST_UPD_FUNC, LAST_UPD_DATE, DESC0, ST, IS_LOCK, IS_DEL) VALUES ('BATH', 'JOB[15]', null, null, null, '30 00 * * *', null, null, null, '支付宝通道对账', null, null, null);
INSERT INTO IFS_SYS_PARAM (PARAM_ID, MAGIC_ID, PARAM_START_TM, PARAM_END_TM, PARAM_CHANG_FLAG, PARAM_VALUE_TX, LAST_UPD_TLR, LAST_UPD_FUNC, LAST_UPD_DATE, DESC0, ST, IS_LOCK, IS_DEL) VALUES ('BATH', 'JOB[5]', null, null, null, '10 00 * * *', null, null, null, '联机交易明细抽取', null, null, null);
--INSERT INTO IFS_SYS_PARAM (PARAM_ID, MAGIC_ID, PARAM_START_TM, PARAM_END_TM, PARAM_CHANG_FLAG, PARAM_VALUE_TX, LAST_UPD_TLR, LAST_UPD_FUNC, LAST_UPD_DATE, DESC0, ST, IS_LOCK, IS_DEL) VALUES ('BATH', 'JOB[SD]', null, null, null, '05 00 * * *', null, null, null, '批量自动运行时间([分钟0-59] [小时0-23][日期切换及当日任务初始化]', null, null, null);
--INSERT INTO IFS_SYS_PARAM (PARAM_ID, MAGIC_ID, PARAM_START_TM, PARAM_END_TM, PARAM_CHANG_FLAG, PARAM_VALUE_TX, LAST_UPD_TLR, LAST_UPD_FUNC, LAST_UPD_DATE, DESC0, ST, IS_LOCK, IS_DEL) VALUES ('BATH', 'JOB[SO]', null, null, null, '30 10 * * *', null, null, null, '切换批量未连接状态', null, null, null);

