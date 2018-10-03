package priv.hailong.service;

import java.util.List;

import priv.hailong.pojo.Category;

public interface CategoryService {

	/**
	 * ���ط����б�
	 * @return
	 */
	List<Category> list();

	/**
	 * ͨ��id��ȡ��Ӧ������
	 * @param id
	 * @return
	 */
	Category get(Integer id);

	/**
	 * ���·���
	 * @param category
	 * @return
	 */
	void update(Category category);
	
	/*
	 * ���ڼ򵥲���
	 */
	Category get();

	/**
	 * ���ӷ���
	 * @param c
	 */
	void add(Category category);
	
	/**
	 * ��idɾ������
	 * @param id
	 */
	void delete(int id);
	
}
