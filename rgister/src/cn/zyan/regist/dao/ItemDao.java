package cn.zyan.regist.dao;

import java.util.List;

import cn.zyan.regist.domain.Item;

public interface ItemDao {
	public void save(Item item);
	
	public void update(Item item);
	
	public List<Item> findAllItem();
	
	public Item find(Integer id);
	
	/**
	 * ��ҳ��ѯ
	 * @param hql  ��ѯ����
	 * @param offset  ��ʼ��¼
	 * @param length  һ�β�ѯ������¼
	 * @return ��ѯ�ļ�¼����
	 */
	public List queryForPage(final String hql,final int offset,final int length);
	/**
	 * ��ѯ���еļ�¼��
	 * @param hql ��ѯ����
	 * @return �ܼ�¼��
	 */
	public int getAllRowCount(String hql);

	public void deleteItem(Integer id);
}
