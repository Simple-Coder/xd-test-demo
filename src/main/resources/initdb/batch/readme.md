### 批量功能

---

- 与批量框架配合使用，包含批量设置及监控功能，以及批量相关表、菜单初始化


- 执行过程：


	1. 根据数据库执行batch_XXXX.sql添加批量基础表（如果已存在忽略）
	
	2. 执行batch_data.sql 添加批量远程控制及监控菜单

- 注意事项：	

	1.如果批量框架需要独立运行（不与snowweb集成），需要从ifinsnow_XXX.sql脚本中获取`IFS_SYS_INF、IFS_SYS_PARAM、IFS_ORG`表脚本并创建
	
	2.批量中需要使用到自增序号，mysql需要使用框架中`IFS_SER_NUM`,oracle使用数据库SEQUENCE（此处注意）
	
	3.IFS_SYS_INF需执行初始脚本，IFS_SYS_PARAM初始脚本参考batch_data.sql（batch/readme.md中有该部分说明）
	
	4.batch_param.sql 为批量自动运行时使用，参考脚本中说明修改后执行。