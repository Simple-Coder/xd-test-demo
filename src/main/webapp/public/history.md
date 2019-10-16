#History For Snow JS 1.2

##changes from version 1.1 to 1.2
-------------------------------
**bugfix：**

+ Cnd的!=null操作转成换is not null
+ Daos.delete语句不需要缓存，否则总是删除之前的
+ rql查询总记录的sql游标未关闭
+ 表单提交方式添加转码`iso-8859-1`->`utf-8`
+ 修复Grid标签中showRefresh和showPageSize参数无效的问题
+ 标签页右键菜单不好使


**feature：**

+ 数据源配置添加属性`maximumActiveTime`和`housekeepingsleeptime`
+ 数据源配置添加属性`testSql`，用于连接断开重连测试,默认`select 1 from dual`
+ 优化BeanConvert转换类名[或属性名]至表名[或列名]
+ 表格xls导出可以获取用户名和机构号
+ 表单中回车不提交


##changes from version 1.0 to 1.1
-------------------------------
**bugfix：**

+ 点刷新列表按钮后，顶头全选框勾没去掉
+ 下拉表格控件搜索框无效
+ 数据显示条数及总页码不对
+ 下路框提示被遮住，当对方接口服务停止后，后台抛出异常被遮住，显示不全
+ 上传时间，列表显示的是时间戳，但是点击详情，弹出框显示上传时间格式不对
+ 在文本域输入一段文件，列表显示换行符号未处理，显示^p
+ dtst配置文件中，Command添加属性submitdataset
+ number类型时，当数据库值为0时，表格中显示为空
+ 下拉表格排序导致列表清空
+ 表格的sortable参数未生效
+ 下拉控件的验证不完整
+ 浮动窗口消失时，里面验证失败的红色边框和提示信息未重置
+ 解决IE8下，对异常捕获的兼容性问题
+ 解决标签页tabs的select属性和标签页tab的icon属性未生效问题
+ 修复fieldmodel在开发模式多线程获取问题
+ 修复 表格前台排序 行未选中的问题
+ 解决beforeChange返回false 阻断页面赋值

**feature：**

+ 下拉表格可调整表格宽度或列宽
+ 下拉控件添加init事件*（静态下拉除外）*：`function [datasetid]_[fieldid]_init(options)`
+ 表格全选按钮联动处理
+ 金额前缀由常量`"$"`改成变量 `UIKit.properties.prefix`
+ 表单label可自定义：`function fieldlabel_[fieldid]_onRefresh(element, text)`
+ 表格支持前台分页
+ 表格参数`remotesort`（是否是后台排序）默认值改为：`false`
+ Field添加一个属性：`minsize`，
+ dataset对象添加方法：`setFieldRule(fieldid, ruleid, errmsg);`
+ Field的控件类型`numberbox`添加别名：`integerbox,doublebox,currencybox`
+ 优化可编辑表格
+ 优化多个窗口的遮罩
+ 添加事件：`editor_[fieldid]_onTip(element,option)`,option参数wrap：是否折行，默认false
+ 添加dao对象操作拦截器：snow_interceptor.xml中添加dao(只针对insert、update对象操作)
+ 添加snowweb前端显示全局配置，snow_webcfg.xml
+ RQL支持in
+ 标签Tab 添加事件 beforeTabChange和afterTabChange，添加方法 refresh(id, url)和length()
+ 优化下拉选择框中下拉列的宽度显示
+ 优化日期反选
+ grid无数据时占位提示
+ 添加snowweb前端显示全局配置，snow_webcfg.xml
+ rql 添加dao对象操作拦截器：snow_interceptor.xml中添加dao(只针对insert、update对象操作)
+ rql 支持in, Cnd条件组装
+ rql 在映射类字段的getter方法上加注解java.beans.Transient可忽略该字段

**deprecated:**

+ public/js/properties.js 废弃该文件


![Copyright](http://www.rmitec.cn/r/cms/www/red/images/logo.png "睿民")



