package edu.xidian.message;

/**
 * @ClassName: MsgType
 * @Description: 不同的消息类型，表示需要不同的处理
 * @author: wangyannan
 * @date: 2014-11-12 下午3:40:19
 */
public enum MsgType {
	/**
	 * 基础环境配置-9000
	 */
	changePasswd, changeSecRule, startService, stopService, viewErrLog, diskFormat, changeIP, changeAffiIP, changeUlimit,
	/**
	 * 应用软件部署-9100
	 */
	setupTomcat, setupJdk, setupMySql, setupApache, setupNginx, setupZendGuardLoader, setupPython, setupMemcached, setupIISRewrite, setupFTP,  setupSQLServer2008R2, setupSQLServer2000, setupOracle10g, setupOracle11g, setup360,
	/**
	 * 应用软件卸载-9600
	 */
	uninstallTomcat, uninstallJdk, uninstallMySql, uninstallApache, uninstallNginx, uninstallZendGuardLoader, uninstallPython, uninstallMemcached, uninstallIISRewrite,  uninstallFTP,  uninstallSQLServer2008R2, uninstallSQLServer2000, uninstallOracle10g, uninstallOracle11g, uninstall360,	
	/**
	 * 应用软件配置-9200
	 */
	configTomcat, configJdk, configMySql, configApache, configNginx, configZendGuardLoader, configPython, configMemcached, configIISRewrite, configFTP, configSQLServer2008R2, configSQLServer2000, configOracle10g, configOracle11g, config360,
	/**
	 * 应用软件更新-9300
	 */
	updateTomcat, updateJdk, updateMySql, updateApache, updateNginx, updateZendGuardLoader, updatePython, updateMemcached, updateIISRewrite,  updateFTP,  updateSQLServer2008R2, updateSQLServer2000, updateOracle10g, updateOracle11g, update360,
	/**
	 * 虚拟机脚本执行-9400
	 */
	executeVMScript,
	/**
	 * 操作状态获取-9500
	 */
	getDownloadStatus,
	/**
	 * 重启-9700
	 */
	reboot
	
}
