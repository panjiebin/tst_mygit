package cn.zyan.regist.service;

import java.text.ParseException;
import java.util.Date;

import org.springframework.transaction.annotation.Transactional;

import cn.zyan.regist.dao.StudentDao;
import cn.zyan.regist.domain.Student;
import cn.zyan.regist.exception.ServiceException;
import cn.zyan.regist.utils.MD5Util;
import cn.zyan.regist.utils.SendEmail;
//@Transactional
public class StudentService {
	private StudentDao studentDao;

	public StudentDao getStudentDao() {
		return studentDao;
	}

	public void setStudentDao(StudentDao studentDao) {
		this.studentDao = studentDao;
	}
	
	public Student findByNumber(String number){
		return studentDao.findByNumber(number);
	}
	
	public void save(Student student){
		studentDao.save(student);
		System.out.println("service student save");
	}
	
	public void update(Student student){
		studentDao.update(student);
		System.out.println("service student update");
	}
	
	public boolean login(String number,String password){
		boolean flag = false;
		flag = studentDao.login(number, password);
		System.out.println("login service");
		return flag;
	}

	public void processregister(Student student) {
		student.setRegisterTime(new Date());
		student.setStatus(1);
		student.setValidateCode(MD5Util.encode2hex(student.getEmail()));
		
		studentDao.save(student);
		
		StringBuffer sb=new StringBuffer("����������Ӽ����˺ţ�48Сʱ��Ч����������ע���˺ţ�����ֻ��ʹ��һ�Σ��뾡�켤�</br>");
        sb.append("<a href=\"http://localhost:8080/regist/active.action?email=");
        sb.append(student.getEmail()); 
        sb.append("&validateCode="); 
        sb.append(student.getValidateCode());
        sb.append("\">http://localhost:8080/regist/active.action?email="); 
        sb.append(student.getEmail());
        sb.append("&validateCode=");
        sb.append(student.getValidateCode());
        sb.append("</a>");

        //�����ʼ�
        SendEmail.send(student.getEmail(), sb.toString());
        System.out.println("�����ʼ�");
	}

	public void processActivate(String email, String validateCode)throws ServiceException, ParseException {
		 //��֤�û��Ƿ���� 
		Student student = studentDao.findStudentByEmail(email);
        if(student!=null) {  
            //��֤�û�����״̬  
            if(student.getStatus()==0) { 
                ///û����
                Date currentTime = new Date();//��ȡ��ǰʱ��  
                //��֤�����Ƿ���� 
                currentTime.before(student.getRegisterTime());
                if(currentTime.before(student.getLastActivateTime())) {  
                    //��֤�������Ƿ���ȷ  
                    if(validateCode.equals(student.getValidateCode())) {  
                        //����ɹ��� //�������û��ļ���״̬��Ϊ�Ѽ��� 
                        System.out.println("==sq==="+student.getStatus());
                        student.setStatus(1);//��״̬��Ϊ����
                        System.out.println("==sh==="+student.getStatus());
                        studentDao.update(student);
                    } else {  
                       throw new ServiceException("�����벻��ȷ");  
                    }  
                } else { throw new ServiceException("�������ѹ��ڣ�");  
                }  
            } else {
               throw new ServiceException("�����Ѽ�����¼��");  
            }  
        } else {
            throw new ServiceException("������δע�ᣨ�����ַ�����ڣ���");  
        }  

    }
		
	
	
	
}
