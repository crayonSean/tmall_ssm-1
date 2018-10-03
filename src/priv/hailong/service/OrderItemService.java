package priv.hailong.service;

import priv.hailong.pojo.Order;
import priv.hailong.pojo.OrderItem;

import java.util.List;

public interface OrderItemService {

	/**
	 * ͨ��id����OrderItem��
	 *
	 * @param id
	 * @return
	 */
	OrderItem getById(Integer id);

	/**
	 * ����һ����������
	 *
	 * @param orderItem
	 */
	void add(OrderItem orderItem);

	/**
	 * ͨ������id��ѯ���ö��������е�Item
	 *
	 * @param order_id
	 * @return
	 */
	List<OrderItem> getByOrderId(Integer order_id);

	/**
	 * ����OrderItem��Ҳֻ�Ǹ���number����
	 *
	 * @param orderItem
	 */
	void update(OrderItem orderItem);

	/**
	 * ����user_id�����û��¶�Ӧ�����ж�����
	 *
	 * @param user_id
	 * @return
	 */
	List<OrderItem> listByUserId(Integer user_id);

	/**
	 * ���ص�ǰuser_id�µĹ��ﳵ�����б�
	 *
	 * @param user_id
	 * @return
	 */
	List<OrderItem> listForCart(Integer user_id);
	
	/**
	 * ���ص�ǰproduct_id��oid��Ϊ�յĶ�����,����ר��
	 */
	List<OrderItem> listByProductId(Integer pid);

	/**
	 * ͨ��idɾ��������
	 *
	 * @param id
	 */
	void delete(Integer id);

	/**
	 * ΪList<Order>��䶩����
	 *
	 * @param orders
	 */
	void fill(List<Order> orders);

	/**
	 * ΪOrder��䶩����
	 *
	 * @param o
	 */
	void fill(Order o);
}
