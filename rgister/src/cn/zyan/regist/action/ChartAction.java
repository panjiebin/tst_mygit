package cn.zyan.regist.action;

import java.util.List;
import java.util.Set;

import org.apache.struts2.ServletActionContext;

import cn.zyan.regist.domain.Item;
import cn.zyan.regist.domain.Student;
import cn.zyan.regist.domain.Team;
import cn.zyan.regist.service.AdminService;
import cn.zyan.regist.service.StudentService;
import cn.zyan.regist.service.TeamService;

import com.opensymphony.xwork2.ActionSupport;

public class ChartAction extends ActionSupport{
	
	private AdminService adminService;
	
	private TeamService teamService;
	public AdminService getAdminService() {
		return adminService;
	}
	public void setAdminService(AdminService adminService) {
		this.adminService = adminService;
	}
	
	public TeamService getTeamService() {
		return teamService;
	}
	public void setTeamService(TeamService teamService) {
		this.teamService = teamService;
	}
	public ChartAction() {
		super();
	}

	
	/* <option value="��ϢѧԺ">��ϢѧԺ </option>
     <option value="��ʳѧԺ">��ʳѧԺ</option>
     <option value="����ѧԺ">����ѧԺ </option>
     <option value="����ѧԺ">����ѧԺ</option>
     <option value="����ѧԺ">����ѧԺ</option>*/
	public String censusDepartment(){
		String uid = ServletActionContext.getRequest().getParameter("itemid");
		int itemid = Integer.parseInt(uid);
		StringBuffer sb = new StringBuffer();
		
		int xc = 0;
		int lc = 0;
		int tc = 0;
		int cc = 0;
		int wc = 0;
		Item item = adminService.findItem(itemid);
		System.out.println("wohsi item"+item);
		String[] color ={"#FF0F00","#FF6600","#CD0D74","#FCD202","##2A0CD0","#0D52D1","#B0DE09"};
		int i = 0;
		if(item.getIort().equals("individual")){
			Set<Student> set = item.getStudentSet();
			if(set.size()==0){
				ServletActionContext.getRequest().setAttribute("message","��û���˱�������Ŀ");
				return "message";
			}
			for(Student s:set){
				if(s.getDepartment().equals("��ϢѧԺ")){
					xc++;
				}
				if(s.getDepartment().equals("��ʳѧԺ")){
					lc++;
				}
				if(s.getDepartment().equals("����ѧԺ")){
					tc++;
				}
				if(s.getDepartment().equals("����ѧԺ")){
					cc++;
				}
				if(s.getDepartment().equals("����ѧԺ")){
					wc++;
				}
				
			}
			/* [{
	         "country": "USA",
	         "visits": 4025,
	         "color": "#FF0F00"
	     },  {
	         "country": "Poland",
	         "visits": 328,
	         "color": "#000000"
	     }]*/
			String dep = "department";
			sb.append("[");
			sb.append("{ \""+dep+"\": \"��ϢѧԺ\", \"number\":"+xc+",\"color\": \""+color[i]+"\"},");
			sb.append("{ \""+dep+"\": \"��ʳѧԺ\", \"number\":"+lc+",\"color\": \""+color[i+1]+"\"},");
			sb.append("{ \""+dep+"\": \"����ѧԺ\", \"number\":"+tc+",\"color\": \""+color[i+2]+"\"},");
			sb.append("{ \""+dep+"\": \"����ѧԺ\", \"number\":"+cc+",\"color\": \""+color[i+3]+"\"},");
			sb.append("{ \""+dep+"\": \"����ѧԺ\", \"number\":"+wc+",\"color\": \""+color[i+4]+"\"} ]");
			ServletActionContext.getRequest().setAttribute("data",sb.toString());
			System.out.println(sb.toString());
		}else{
			ServletActionContext.getRequest().setAttribute("message","�Ŷ���δͳ��");
			return "message";
		}
		return "success";
	}
	
	public String countItem(){
		List<Item> list = adminService.findAllItem();
		StringBuffer sb1 = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		sb1.append("[ ");
		sb2.append("[ ");
		String[] s ={"#FF0F00","#FF6600","#CD0D74","#FCD202","##2A0CD0","#0D52D1","#B0DE09"};
		int m = 0;
		for(Item i:list){
			if(i.getIort().equals("individual")){
				sb1.append("{ \"������Ŀ\": \"" + i.getName()+"\",");
				sb1.append("\"����\":"  + i.getStudentSet().size()+",");
				sb1.append("\"color\":"  + "\""+s[m]+"\""+"},");
				m = (m+1)%7;
				System.out.println("woshi yanse m:"+ m + s[m]);
			}else{
				List<Team> l = teamService.findTeamByItem(i);
				sb2.append("{ \"������Ŀ\": \"" + i.getName()+"\",");
				sb2.append("\"�Ŷ���\":"  + l.size()+",");
				sb2.append("\"color\":"  + "\""+s[m]+"\""+"},");
				m = (m+1)%7;
				System.out.println("woshi tuandui yanse m:"+ m + s[m]);
			}
		}
		sb1.deleteCharAt(sb1.length()-1);
		sb1.append("]");
		sb2.deleteCharAt(sb2.length()-1);
		sb2.append("]");
		ServletActionContext.getRequest().setAttribute("result",sb1.toString());
		ServletActionContext.getRequest().setAttribute("result1",sb2.toString());
		System.out.println(sb1.toString());
		System.out.println(sb2.toString());
		return "success";
	}

}
