package cn.zyan.regist.action;

import java.util.List;

import org.apache.struts2.ServletActionContext;

import cn.zyan.regist.domain.Admin;
import cn.zyan.regist.domain.Item;
import cn.zyan.regist.domain.Notice;
import cn.zyan.regist.domain.PageBean;
import cn.zyan.regist.domain.Student;
import cn.zyan.regist.domain.Team;
import cn.zyan.regist.service.AdminService;
import cn.zyan.regist.service.TeamService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class AdminAction extends ActionSupport{
	private AdminService adminService;
	private TeamService teamService;
	private Admin admin;
	private Item item;
	private String validateCode;//��½ʱ����֤��
	private Notice notice;
	private PageBean pageBean;
	private List list;
	
	private int page = 1;
	
	

	public TeamService getTeamService() {
		return teamService;
	}

	public void setTeamService(TeamService teamService) {
		this.teamService = teamService;
	}

	public PageBean getPageBean() {
		return pageBean;
	}

	public void setPageBean(PageBean pageBean) {
		this.pageBean = pageBean;
	}

	public List getList() {
		return list;
	}

	public void setList(List list) {
		this.list = list;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public AdminService getAdminService() {
		return adminService;
	}

	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public Notice getNotice() {
		return notice;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}
	
	
	public Admin getAdmin() {
		return admin;
	}

	public void setAdmin(Admin admin) {
		this.admin = admin;
	}

	public String getValidateCode() {
		return validateCode;
	}

	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public String login(){
		Object obj = ActionContext.getContext().getSession().get("code");
		String code = obj==null?"":obj.toString();
		System.out.println("yanzhengma"+code);
		if(code.equalsIgnoreCase(this.validateCode))
		{
			if(adminService.adminLogin(admin.getName(), admin.getPassword()))
			{
				ActionContext.getContext().getSession().put("admin", admin);
				return SUCCESS;
			}
			else
			{
				this.addActionMessage("��¼ʧ�ܣ��˺Ż��������");
			}
		}
		else
		{
			this.addActionMessage("У�������");
		}
		return "input";
	}
	
	public String addItem(){
		adminService.saveItem(item);
		ServletActionContext.getRequest().setAttribute("message","������Ŀ�ɹ�");
		return "success";
	}
	/**
	 * �ҵ����д�������Ŀ
	 * @return
	 */
	public String registLookItem(){
		
		List<Item> itemList = adminService.findAllItem();
		
		ServletActionContext.getRequest().setAttribute("itemList", itemList);
		return "success";
	}
	
	public String registPreItem(){
		
		List<Item> itemList = adminService.findAllItem();
		
		ServletActionContext.getRequest().setAttribute("itemList", itemList);
		return "success";
	}
	public String registItem(){
		String id = ServletActionContext.getRequest().getParameter("iid");
		int id1 = Integer.parseInt(id);
		Item item = adminService.findItem(id1);
		if(item.getIort().equals("individual")){
			Student stu = (Student) ActionContext.getContext().getSession().get("student");
			if(item.getStudentSet().contains(stu)){
				ServletActionContext.getRequest().setAttribute("message","���ѲμӸñ����������ظ�����");
			}else{
				item.getStudentSet().add(stu);
				adminService.updateItem(item);
				ServletActionContext.getRequest().setAttribute("message","�����ɹ�����׼ʱ�μӱ���");
			}
		}
		else{
			ServletActionContext.getRequest().setAttribute("message","����Ŀ�����������Ŷӣ�����Ӻ���");
		}
		
		return "message";
	}
	public String teamRegist(){
		String id = ServletActionContext.getRequest().getParameter("iid");
		int id1 = Integer.parseInt(id);
		System.out.println("woshi iid"+id1);
		Item item = adminService.findItem(id1);
		System.out.println("item shi :"+item);
		if(item.getIort().equals("team")){
			Team team = (Team) ActionContext.getContext().getSession().get("team");
			Team t = teamService.findTeam(team.getName());
			//System.out.println("woshi team de item"+t.getItem());
			//if(t.getItem().getIid()==item.getIid()){
			//	ServletActionContext.getRequest().setAttribute("message","���ѲμӸñ����������ظ�����");
			//}else{
				t.setItem(item);
				teamService.update(t);
				ServletActionContext.getRequest().setAttribute("message","�����ɹ�����׼ʱ�μӱ���");
			//}
		}
		else{
			ServletActionContext.getRequest().setAttribute("message","����Ŀ���������Ǹ��ˣ����޷�����");
		}

		return "success";
	}
	public String withdrawalItem(){
		String id = ServletActionContext.getRequest().getParameter("iid");
		int id1 = Integer.parseInt(id);
		Item item = adminService.findItem(id1);
		System.out.println("woshi item"+item);
		Student stu = (Student) ActionContext.getContext().getSession().get("student");
		item.getStudentSet().remove(stu);
		for(Student s:item.getStudentSet()){
			System.out.println(s.getName());
		}
		System.out.println(item.getStudentSet().contains(stu));
		adminService.updateItem(item);
		System.out.println(item.getStudentSet().contains(stu));
		ServletActionContext.getRequest().setAttribute("message","��ѡ�ɹ�");
		
		return "message";
	}
	
	public String getItemPage(){
		this.pageBean = adminService.queryForPage(3, page);
		ServletActionContext.getRequest().setAttribute("pageBean",this.pageBean);
		
		return "success";
	}
	
	public String itemPreUpdate(){
		String uid = ServletActionContext.getRequest().getParameter("itemid");
		int itemid = Integer.parseInt(uid);
		this.item  = adminService.findItem(itemid);
		
		return "success";
	}
	
	public String itemUpdate(){
		
		int itemid = this.item.getIid();
		Item item1 = adminService.findItem(itemid);
		item1.setName(this.item.getName());
		item1.setItemInfo(this.item.getItemInfo());
		adminService.updateItem(item1);
		ServletActionContext.getRequest().setAttribute("message","���³ɹ�");
		
		return "message";
	}
	
	public String itemDelete(){
		String uid = ServletActionContext.getRequest().getParameter("itemid");
		
		int itemid = Integer.parseInt(uid);
		
		adminService.deleteItem(itemid);
		ServletActionContext.getRequest().setAttribute("message","ɾ���ɹ�");
		
		return "message";
	}
	public String chartTest(){
		StringBuffer a = new StringBuffer();
		a.append("[{ \"country\": \"��ϢѧԺ\",\"visits\": 100," +
				"\"color\": \"#FF0F00\"},{ \"country\": \"��ʳѧԺ\",\"visits\": 66," +
				"\"color\": \"#FFFF00\"},{ \"country\": \"����ѧԺ\",\"visits\": 82," +
				"\"color\": \"#0D0D00\"}]");
		ServletActionContext.getRequest().setAttribute("result",a.toString());
		return "success";
	}

	public String itemPreQuery(){
		String uid = ServletActionContext.getRequest().getParameter("itemid");
		int itemid = Integer.parseInt(uid);
		String hql = "FROM Item WHERE iid="+itemid;
		this.pageBean = adminService.queryForPage2(3, page,hql);
		ServletActionContext.getRequest().setAttribute("pageBean",this.pageBean);

		return "success";
	}
}
