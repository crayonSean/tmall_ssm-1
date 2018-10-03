package priv.hailong.service;

import priv.hailong.pojo.PropertyValue;

import java.util.List;

public interface PropertyValueService {

	/**
	 * ����һ������
	 *
	 * @param propertyValue
	 */
	void add(PropertyValue propertyValue);

	/**
	 * ����idɾ��һ������
	 *
	 * @param id
	 */
	void delete(Integer id);

	/**
	 * ���� product_id ��ɾ����Ӧ������
	 *
	 * @param product_id
	 */
	void deleteByProductId(Integer product_id);

	/**
	 * ����һ������
	 *
	 * @param propertyValue
	 */
	void update(PropertyValue propertyValue);

	/**
	 * PropertyValue ���������������Ҫ�����������������ƥ��
	 * ���ض�Ӧ������ PropertyValue ֵ
	 *
	 * @return
	 */
	List<PropertyValue> list(Integer product_id, Integer category_id);

	/**
	 * ����id����һ������
	 *
	 * @param id
	 */
	PropertyValue get(Integer id);

	/**
	 * ����product_id����PropertyValue��
	 * �����ú������Property
	 *
	 * @param product_id
	 * @return
	 */
	List<PropertyValue> listByProductId(Integer product_id);
}
