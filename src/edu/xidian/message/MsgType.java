package edu.xidian.message;

/**
 * @ClassName: MsgType
 * @Description: 不同的消息类型，表示需要不同的处理
 * @author: wangyannan
 * @date: 2014-11-12 下午3:40:19
 */
public enum MsgType {
	/**
	 * 基础环境配置
	 */
	changePasswd, changeSecRule, startService, stopService, viewErrLog, diskFormat, changeIP, changeAffiIP, changeUlimit,getSysService,
	/**
	 * 应用软件部署
	 */
	setupTomcat, setupJdk, setupMySql, setupApache, setupNginx, setupZendGuardLoader, setupPython, setupMemcached, setupIISRewrite, setupASP, setupFTP, setupASPNET, setupSQLServer2008R2, setupSQLServer2000, setupOracle10g, setupOracle11g, setup360,
	/**
	 * 应用软件配置
	 */
	configTomcat, configJdk, configMySql, configApache, configNginx, configZendGuardLoader, configPython, configMemcached, configIISRewrite, configASP, configFTP, configASPNET, configSQLServer2008R2, configSQLServer2000, configOracle10g, configOracle11g, config360,
	/**
	 * 应用软件更新
	 */
	updateTomcat, updateJdk, updateMySql, updateApache, updateNginx, updateZendGuardLoader, updatePython, updateMemcached, updateIISRewrite, updateASP, updateFTP, updateASPNET, updateSQLServer2008R2, updateSQLServer2000, updateOracle10g, updateOracle11g, update360,
	/**
	 * 虚拟机脚本执行
	 */
	executeVMScript,
	/**
	 * 操作状态获取
	 */
	getStatus
}
