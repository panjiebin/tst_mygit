package cn.zyan.regist.action;

import java.io.IOException;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import cn.zyan.regist.domain.Item;
import cn.zyan.regist.domain.Student;
import cn.zyan.regist.domain.Team;
import cn.zyan.regist.service.StudentService;
import cn.zyan.regist.service.TeamService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class TeamAction extends ActionSupport{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1111853310632668710L;
	private Team team;
	private String validateCode;//��½ʱ����֤��
	private TeamService teamService;
	private StudentService studentService;
	private String first;
	
	
	public StudentService getStudentService() {
		return studentService;
	}
	public void setStudentService(StudentService studentService) {
		this.studentService = studentService;
	}
	public String getFirst() {
		return first;
	}
	public void setFirst(String first) {
		this.first = first;
	}
	
	public Team getTeam() {
		return team;
	}
	public void setTeam(Team team) {
		this.team = team;
	}
	public TeamService getTeamService() {
		return teamService;
	}
	public void setTeamService(TeamService teamService) {
		this.teamService = teamService;
	}
	
	public String getValidateCode() {
		return validateCode;
	}
	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}
	public String qisiren(){
		System.out.println("woshi team de name:"+ team.getName());
		teamService.save(team);
		ServletActionContext.getRequest().setAttribute("message","ע��ɹ������¼ȥ��Ӷ�Ա");
		return "success";
	}
	public String checkTeam() throws IOException{
		HttpServletRequest request = (HttpServletRequest) ActionContext
                .getContext()
                .get(org.apache.struts2.StrutsStatics.HTTP_REQUEST);
		String name = request.getParameter("name");//��ȡ
		System.out.println("woshi name"+name);
		boolean flag = teamService.find(name);
		System.out.println("woshifaxianl name:"+flag);
		HttpServletResponse response=  ServletActionContext.getResponse();  
	    response.setContentType("text/html;charset=UTF-8");  
	    if(flag){  
	        //����  
	        response.getWriter().println("<font color='red'>�ö����ѱ�ռ��</font>");  
	    }else{  
	        response.getWriter().println("<font color='green'>�ö�������ʹ��</font>");  
	    }  
		return NONE;
	}
	public String login(){
		Object obj = ActionContext.getContext().getSession().get("code");
		String code = obj==null?"":obj.toString();
		System.out.println("yanzhengma"+code);
		if(code.equalsIgnoreCase(this.validateCode))
		{
			if(teamService.login(team.getName(), team.getPassword()))
			{
				Team te = teamService.findTeam(team.getName());
				ActionContext.getContext().getSession().put("team", te);
				return SUCCESS;
			}
			else
			{
				this.addActionMessage("��¼ʧ�ܣ��˺��������,�������˻�δ����");
				//ServletActionContext.getRequest().setAttribute("message","��¼ʧ�ܣ��������˻�δ����");
			}
		}
		else
		{
			this.addActionMessage("У�������");
			//ServletActionContext.getRequest().setAttribute("message","У�������");
		}
		return NONE;
	}
	
	public String addStudent(){
		Object o = ActionContext.getContext().getSession().get("team");
		String name = ((Team)o).getName();
		if(teamService.findTeam(name).getStudent1Set().size()<=3){
			Student stu1 = studentService.findByNumber(first);
			
			
			if(stu1==null){
				this.addActionMessage("ѧ����δע�ᣬ��ע������");
				System.out.println("xueshengzhuce,tianjia ");
			}else {
				Team t = teamService.findTeam(name);
				if(t.getStudent1Set().contains(stu1)){
					this.addActionMessage("ѧ���Ѿ��ڶ�����");
					System.out.println("yijingzaiduiwuzhong ");
				}else{
					t.getStudent1Set().add(stu1);
					teamService.update(t);
					ServletActionContext.getRequest().setAttribute("message","��ӳɹ�");
					return "success";
				}
			}
		}else{
			this.addActionMessage("��Ա�������������");
			System.out.println("manle");
		}
		
		return NONE;
	}
	public String allStudent(){
		Object o = ActionContext.getContext().getSession().get("team");
		Team team = (Team) o;
		Team t = teamService.findTeam(team.getName());
		Set<Student> set = t.getStudent1Set();
		ServletActionContext.getRequest().setAttribute("set",set);
		return "success";
	}
	
	public String stuDelete(){
		String id = ServletActionContext.getRequest().getParameter("sid");
		Student stu = studentService.findByNumber(id);
		Object o = ActionContext.getContext().getSession().get("team");
		Team team = (Team) o;
		Team t = teamService.findTeam(team.getName());
		System.out.println("woshi team "+t);
		t.getStudent1Set().remove(stu);
		System.out.println("�Ƴ�֮��"+t.getStudent1Set().contains(stu));
		teamService.update(t);
		
		Team t1 = teamService.findTeam(team.getName());
		for(Student s:t1.getStudent1Set()){
			System.out.println("woshi stu de name"+s.getName());
		}
		ServletActionContext.getRequest().setAttribute("message","ɾ���ɹ�");
		return "success";
	}
	/**
	 * �ŶӲ鿴�Լ����б�������Ŀ
	 * @return
	 */
	public String all(){
		Team team =(Team)ActionContext.getContext().getSession().get("team");
		Team t = teamService.findTeam(team.getName());
		Item item = t.getItem();
		System.out.println("item shi :"+item);
		System.out.println("item de item shi :"+item);
		ServletActionContext.getRequest().setAttribute("item",item);
		return "success";
	}
	public String withdrawal(){
		
		Team team =(Team)ActionContext.getContext().getSession().get("team");
		Team t = teamService.findTeam(team.getName());
		t.setItem(null);
		teamService.update(t);
		ServletActionContext.getRequest().setAttribute("message","��ѡ�ɹ�");
		return "message";
	}
	
}
