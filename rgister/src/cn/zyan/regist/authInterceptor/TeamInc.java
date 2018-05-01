package cn.zyan.regist.authInterceptor;

import org.apache.struts2.ServletActionContext;

import cn.zyan.regist.domain.Team;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class TeamInc extends AbstractInterceptor{

	@Override
	public String intercept(ActionInvocation arg0) throws Exception {
		System.out.println("Ȩ����������ʼִ��");
		Team obj = (Team) ActionContext.getContext().getSession().get("team");

		if(obj!=null)
		{
		    String str =  arg0.invoke();
		    System.out.println("Ȩ������������ִ��");
		    return str;
		}
		else	
		{
			ServletActionContext.getRequest().setAttribute("message", "��û��Ȩ�޽��д˲���");
			return "message";
		}
	}

}
