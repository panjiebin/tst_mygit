package cn.zyan.regist.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import cn.zyan.regist.dao.AdminDao;
import cn.zyan.regist.dao.ItemDao;
import cn.zyan.regist.domain.Item;
import cn.zyan.regist.domain.PageBean;
//@Transactional
public class AdminService {
	private ItemDao itemDao;
	private AdminDao adminDao;
	
	public AdminDao getAdminDao() {
		return adminDao;
	}

	public void setAdminDao(AdminDao adminDao) {
		this.adminDao = adminDao;
	}

	public ItemDao getItemDao() {
		return itemDao;
	}

	public void setItemDao(ItemDao itemDao) {
		this.itemDao = itemDao;
	}
	
	
	public void saveItem(Item item){
		itemDao.save(item);
	}
	
	public void updateItem(Item item){
		itemDao.update(item);
	}
	public void deleteItem(Integer id){
		itemDao.deleteItem(id);
	}
	
	public boolean adminLogin(String name,String password){
		return adminDao.login(name, password);
	}
	
	public List<Item> findAllItem(){
		return itemDao.findAllItem();
	}
	
	public Item findItem(Integer id){
		return itemDao.find(id);
	}
	
	/**
	 * ��ҳ��ѯ  
	 * @param pageSize  ÿҳ��ʾ���ټ�¼
	 * @param currentPage ��ǰҳ
	 * @return ��װ�˷�ҳ��Ϣ��bean
	 */
	public PageBean queryForPage(int pageSize, int page) {
		final String hql = "from Item"; //��ѯ���
		int allRow = itemDao.getAllRowCount(hql);  //�ܼ�¼��
		int totalPage = PageBean.countTatalPage(pageSize, allRow); //��ҳ��
		final int offset = PageBean.countOffset(pageSize, page); //��ǰҳ��ʼ��¼
		final int length = pageSize; // ÿҳ��¼��
		final int currentPage = PageBean.countCurrentPage(page); // ��ǰҳ
		List list = itemDao.queryForPage(hql, offset, length); //
		//�ѷ�ҳ��Ϣ���浽Bean����
		PageBean pageBean  = new PageBean();
		pageBean.setPageSize(pageSize);
		pageBean.setCurrentPage(currentPage);
		pageBean.setAllRow(allRow);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		pageBean.init();
		return pageBean;
	}

	public PageBean queryForPage2(int pageSize, int page,String hql) {

		int allRow = itemDao.getAllRowCount(hql);  //�ܼ�¼��
		int totalPage = PageBean.countTatalPage(pageSize, allRow); //��ҳ��
		final int offset = PageBean.countOffset(pageSize, page); //��ǰҳ��ʼ��¼
		final int length = pageSize; // ÿҳ��¼��
		final int currentPage = PageBean.countCurrentPage(page); // ��ǰҳ
		List list = itemDao.queryForPage(hql, offset, length); //
		//�ѷ�ҳ��Ϣ���浽Bean����
		PageBean pageBean  = new PageBean();
		pageBean.setPageSize(pageSize);
		pageBean.setCurrentPage(currentPage);
		pageBean.setAllRow(allRow);
		pageBean.setTotalPage(totalPage);
		pageBean.setList(list);
		pageBean.init();
		return pageBean;
	}
}
