package priv.hailong.service;

import priv.hailong.pojo.Property;

import java.util.List;

public interface PropertyService {

	/**
	 * ����һ������
	 * @param property
	 */
	void add(Property property);

	/**
	 * ����idɾ��һ������
	 * @param id
	 */
	void delete(Integer id);

	/**
	 * ����һ������
	 * @param property
	 */
	void update(Property property);

	/**
	 * �������е�category_id�µ�Property�б�
	 * @return
	 */
	List<Property> list(Integer category_id);

	/**
	 * ����id����һ������
	 * @param id
	 */
	Property get(Integer id);
}
