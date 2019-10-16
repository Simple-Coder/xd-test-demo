### 微交换集成

---

1.集成至ifs-snowweb步骤

	2.1 在[/genconf/resources/zh_CN/]目录下添加错误信息配置[switchcode.properties]
	2.2 在[src/main/resources/]目录下载添加配置目录[microswitch.Buffer、microswitch.Channel、microswitch.Client]
	
		其中：	
		microswitch.Buffer 放置报文格式化配置
		microswitch.Channel 放置发布服务配置
		microswitch.Client 放置客户端配置
	2.3 在pom.xml中添加依赖
		
		<dependency>
			<groupId>com.ruimin.ifs</groupId>
			<artifactId>ifs-micro-switch</artifactId>
			<version>${project.parent.version}</version>
		</dependency>
		
	2.4 在启动加载类（/ifs-snowweb/src/main/java/com/ruimin/ifs/listener/CustomListener.java） 中添加如下代码：
		
		@Override
		public void contextInitialized(ServletContextEvent event) {
			....
			try{
				....
				//微交换启动
				MicroSwitchCtl.init();
				MicroSwitchCtl.start();
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		@Override
		public void contextDestroyed(ServletContextEvent event) {
			...
			//微交换卸载
			try {
				MicroSwitchCtl.stop();
				MicroSwitchCtl.destory();
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		
	2.5 执行/initdb/micro/micro_data.sql,添加启动加载参数
	
2.微交换的具体配置使用，参考框架示例配置：`microswitch目录`