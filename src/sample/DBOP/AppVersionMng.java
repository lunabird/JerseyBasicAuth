package sample.DBOP;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

/**
 *
 * @brief 
 * @author huangpeng
 * @version 
 * @date 2015-3-17 ����9:38:54 
 * 
 */
public class AppVersionMng {
	/**
	 * 
	 * @author huangpeng
	 * @date 2015-3-17
	 * @brief ��һ���汾���б�(��������Ϊ2)����ѡ��һ�����µİ汾��
	 */
	public String pickNewestVerionNumber(ArrayList<String> versionList){
		String newestVersion = "";
		String firstVersion = versionList.get(0);
		String regEx1 = "[0-9]+\\.[0-9]+\\.[0-9]+";
		String regEx2 = "[0-9]+\\.[0-9]+";
		if(Pattern.matches(regEx1, firstVersion)){
			newestVersion=versionList.get(0);
			for(int i=1;i<versionList.size();i++){
				newestVersion=compareTwoVersionWithThreePoint(newestVersion,versionList.get(i));
			}
		}else if(Pattern.matches(regEx2, firstVersion)){
			newestVersion=versionList.get(0);
			for(int i=1;i<versionList.size();i++){
				newestVersion=compareTwoVersionWithTwoPoint(newestVersion,versionList.get(i));
			}
		}else{
			System.out.println("�汾�Ÿ�ʽ�����޷�����");
		}
		return newestVersion;
	}
	
	public String compareTwoVersionWithTwoPoint(String version1,String version2){
		String[] s1 = version1.split("\\.");
		String[] s2 = version2.split("\\.");
		if(Integer.parseInt(s1[0])>Integer.parseInt(s2[0])){
			return version1;
		}else if(Integer.parseInt(s2[0])>Integer.parseInt(s1[0])){
			return version2;
		}else if(Integer.parseInt(s1[1])>Integer.parseInt(s2[1])){
			return version1;
		}else if(Integer.parseInt(s2[1])>Integer.parseInt(s1[1])){
			return version2;
		}
		return null;
	}
	public String compareTwoVersionWithThreePoint(String version1,String version2){
		String[] s1 = version1.split("\\.");
		String[] s2 = version2.split("\\.");
		if(Integer.parseInt(s1[0])>Integer.parseInt(s2[0])){
			return version1;
		}else if(Integer.parseInt(s2[0])>Integer.parseInt(s1[0])){
			return version2;
		}else if(Integer.parseInt(s1[1])>Integer.parseInt(s2[1])){
			return version1;
		}else if(Integer.parseInt(s2[1])>Integer.parseInt(s1[1])){
			return version2;
		}else if(Integer.parseInt(s1[2])>Integer.parseInt(s2[2])){
			return version1;
		}else if(Integer.parseInt(s2[2])>Integer.parseInt(s1[2])){
			return version2;
		}
		return null;
	}
	/**
	 * 
	 * @author huangpeng
	 * @date 2015-3-17
	 * @brief �ж�version1��version2�����ؽ��µİ汾��
	 */
	public String whichIsNew(String version1,String version2){
		String regEx1 = "[0-9]+\\.[0-9]+\\.[0-9]+";
		String regEx2 = "[0-9]+\\.[0-9]+";
		if(Pattern.matches(regEx1, version1)&&Pattern.matches(regEx1, version2)){
			String[] s1 = version1.split("\\.");
			String[] s2 = version2.split("\\.");
			if(Integer.parseInt(s1[0])>Integer.parseInt(s2[0])){
				return version1;
			}else if(Integer.parseInt(s2[0])>Integer.parseInt(s1[0])){
				return version2;
			}else if(Integer.parseInt(s1[1])>Integer.parseInt(s2[1])){
				return version1;
			}else if(Integer.parseInt(s2[1])>Integer.parseInt(s1[1])){
				return version2;
			}else if(Integer.parseInt(s1[2])>Integer.parseInt(s2[2])){
				return version1;
			}else if(Integer.parseInt(s2[2])>Integer.parseInt(s1[2])){
				return version2;
			}
		}else if(Pattern.matches(regEx2, version1)&&Pattern.matches(regEx2, version2)){
			String[] s1 = version1.split("\\.");
			String[] s2 = version2.split("\\.");
			if(Integer.parseInt(s1[0])>Integer.parseInt(s2[0])){
				return version1;
			}else if(Integer.parseInt(s2[0])>Integer.parseInt(s1[0])){
				return version2;
			}else if(Integer.parseInt(s1[1])>Integer.parseInt(s2[1])){
				return version1;
			}else if(Integer.parseInt(s2[1])>Integer.parseInt(s1[1])){
				return version2;
			}
		}else{
			System.out.println("�汾�Ÿ�ʽ�����޷�����");
		}
		return null;
	}
	public void StringFilter(String str) throws PatternSyntaxException {
		// ֻ��������
		// String regEx = "[^a-zA-Z0-9]";
		// ��������������ַ�
//		String regEx = "[a-zA-Z`~!@#$%^&*()+=|{}':;',//[//]_.<>/?~��@#��%����&*��������+|{}������������������������]";
//		Pattern p = Pattern.compile(regEx);
//		Matcher m = p.matcher(str);
//		return m.replaceAll("").trim();
		
		
		String regEx1 = "[0-9]+\\.[0-9]+\\.[0-9]+";
//		Pattern p = Pattern.compile(regEx1);
		System.out.println(Pattern.matches(regEx1, str));
	}
	
	
	public static void main(String[] args){
		AppVersionMng avm = new AppVersionMng();
//		avm.StringFilter("2008.0.2");
		ArrayList<String> versionList = new ArrayList<String>();
		versionList.add("2.4.1012");
		versionList.add("2.5.1123");
		String newest = avm.pickNewestVerionNumber(versionList);
		System.out.println(newest);
	}
}
