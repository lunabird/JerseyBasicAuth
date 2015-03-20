package sample.hello.client;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = new String("C:\\FileZilla_Server-0_9_48");
		String[] ss = s.split("\\\\");
//		StringBuilder sb = new StringBuilder();
//		for(int i=0;i<ss.length-1;i++) {
//			sb.append(ss[i]+"/");
//		}
		
		System.out.println(ss[ss.length-1]);
		
	}

}
