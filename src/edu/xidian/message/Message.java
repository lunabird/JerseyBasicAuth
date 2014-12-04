package edu.xidian.message;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @ClassName: Message
 * @Description: 消息类
 * @author: wangyannan
 * @date: 2014-11-12 上午10:21:06
 */
public class Message implements Serializable {
	/**
	 * @fieldName: type
	 * @fieldType: MsgType
	 * @Description: 消息类型
	 */
	private MsgType type;
	/**
	 * @fieldName: userID
	 * @fieldType: String
	 * @Description: 用户ID
	 */
	private String userID;
	/**
	 * @fieldName: values
	 * @fieldType: String[]
	 * @Description: 参数列表
	 */
	private Object values;	
	
	/** 
	 * @Title:Message
	 * @Description:消息构造方法 
	 * @param type
	 * @param userID
	 * @param time
	 * @param values 
	 */
	public Message(MsgType type, String userID,Object values) {
		this.type = type;
		this.userID = userID;
		this.values=values;
	}

	/**
	 * @Title: getType
	 * @Description: 获取消息类型
	 * @return: MsgType
	 */
	public MsgType getType() {
		return type;
	}
	
	/** 
	 * @Title: setType 
	 * @Description: 设置消息类型
	 * @param t
	 * @return: void
	 */
	public void setType(MsgType t) {
		this.type = t;
	}
	
	/** 
	 * @Title: getUserID 
	 * @Description: 获取用户ID
	 * @return: String
	 */
	public String getUserID() {
		return userID;
	}
	
	/** 
	 * @Title: getValues 
	 * @Description: 获取用户请求的参数列表
	 * @return: String[]
	 */
	public Object getValues(){
		return values;
	}

}
