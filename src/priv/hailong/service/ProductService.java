package priv.hailong.service;

import priv.hailong.pojo.Category;
import priv.hailong.pojo.Product;

import java.util.List;

public interface ProductService {

	/**
	 * ����һ������
	 *
	 * @param product
	 */
	void add(Product product);

	/**
	 * ͨ��idɾ��һ������
	 *
	 * @param id
	 */
	void delete(Integer id);

	/**
	 * ����һ������
	 *
	 * @param product
	 */
	void update(Product product);

	/**
	 * ����id��ȡһ������
	 *
	 * @param id
	 * @return
	 */
	Product get(Integer id);

	/**
	 * ����category_id�������ж�Ӧ��Product����
	 *
	 * @param category_id
	 * @return
	 */
	List<Product> list(Integer category_id);

	/**
	 * Ϊ�����������Ʒ����
	 *
	 * @param categories
	 */
	void fill(List<Category> categories);

	/**
	 * Ϊһ����������Ʒ����
	 *
	 * @param category
	 */
	void fill(Category category);

	/**
	 * Ϊ�����������Ʒ����
	 *
	 * @param categories
	 */
	void fillByRow(List<Category> categories);

	
	
	/**
	 * Ϊ��Ʒ���ReviewCount�ֶ�
	 *
	 * @param product
	 */
	void setReviewCount(Product product);

	
	
	
	/**
	 * ����keyword������Ӧ�Ĳ�Ʒ����
	 *
	 * @param keyword
	 * @return
	 */
	List<Product> search(String keyword);
}
