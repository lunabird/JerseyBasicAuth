package edu.xidian.message;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @ClassName: Message
 * @Description: ��Ϣ��
 * @author: wangyannan
 * @date: 2014-11-12 ����10:21:06
 */
public class Message implements Serializable {
	private static final long serialVersionUID = 803592200362798025L;
	/**
	 * @fieldName: type
	 * @fieldType: MsgType
	 * @Description: ��Ϣ����
	 */
	private MsgType type;
	/**
	 * @fieldName: userID
	 * @fieldType: String
	 * @Description: �û�ID
	 */
	private String opID;
	/**
	 * @fieldName: values
	 * @fieldType: String[]
	 * @Description: �����б�
	 */
	private Object values;	
	
	/** 
	 * @Title:Message
	 * @Description:��Ϣ���췽�� 
	 * @param type
	 * @param userID
	 * @param time
	 * @param values 
	 */
	public Message(MsgType type, String opID,Object values) {
		this.type = type;
		this.opID = opID;
		this.values=values;
	}

	/**
	 * @Title: getType
	 * @Description: ��ȡ��Ϣ����
	 * @return: MsgType
	 */
	public MsgType getType() {
		return type;
	}
	
	/** 
	 * @Title: setType 
	 * @Description: ������Ϣ����
	 * @param t
	 * @return: void
	 */
	public void setType(MsgType t) {
		this.type = t;
	}
	
	/** 
	 * @Title: getUserID 
	 * @Description: ��ȡ�û�ID
	 * @return: String
	 */
	public String getOpID() {
		return opID;
	}
	
	/** 
	 * @Title: getValues 
	 * @Description: ��ȡ�û�����Ĳ����б�
	 * @return: String[]
	 */
	public Object getValues(){
		return values;
	}

}
