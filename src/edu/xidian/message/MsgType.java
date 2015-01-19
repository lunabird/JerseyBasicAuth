package edu.xidian.message;

/**
 * @ClassName: MsgType
 * @Description: ��ͬ����Ϣ���ͣ���ʾ��Ҫ��ͬ�Ĵ���
 * @author: wangyannan
 * @date: 2014-11-12 ����3:40:19
 */
public enum MsgType {
	/**
	 * ������������-9000
	 */
	changePasswd, changeSecRule, startService, stopService, viewErrLog, diskFormat, changeIP, changeAffiIP, changeUlimit,
	/**
	 * Ӧ���������-9100
	 */
	setupTomcat, setupJdk, setupMySql, setupApache, setupNginx, setupZendGuardLoader, setupPython, setupMemcached, setupIISRewrite, setupFTP,  setupSQLServer2008R2, setupSQLServer2000, setupOracle10g, setupOracle11g, setup360,
	/**
	 * Ӧ�����ж��-9600
	 */
	uninstallTomcat, uninstallJdk, uninstallMySql, uninstallApache, uninstallNginx, uninstallZendGuardLoader, uninstallPython, uninstallMemcached, uninstallIISRewrite,  uninstallFTP,  uninstallSQLServer2008R2, uninstallSQLServer2000, uninstallOracle10g, uninstallOracle11g, uninstall360,	
	/**
	 * Ӧ���������-9200
	 */
	configTomcat, configJdk, configMySql, configApache, configNginx, configZendGuardLoader, configPython, configMemcached, configIISRewrite, configFTP, configSQLServer2008R2, configSQLServer2000, configOracle10g, configOracle11g, config360,
	/**
	 * Ӧ���������-9300
	 */
	updateTomcat, updateJdk, updateMySql, updateApache, updateNginx, updateZendGuardLoader, updatePython, updateMemcached, updateIISRewrite,  updateFTP,  updateSQLServer2008R2, updateSQLServer2000, updateOracle10g, updateOracle11g, update360,
	/**
	 * ������ű�ִ��-9400
	 */
	executeVMScript,
	/**
	 * ����״̬��ȡ-9500
	 */
	getDownloadStatus,
	/**
	 * ����-9700
	 */
	reboot
	
}
