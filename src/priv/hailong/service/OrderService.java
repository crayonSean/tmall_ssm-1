package priv.hailong.service;

import priv.hailong.pojo.Order;
import priv.hailong.pojo.OrderItem;

import java.util.List;

public interface OrderService {

	String waitPay = "waitPay";
	String waitDelivery = "waitDelivery";
	String waitConfirm = "waitConfirm";
	String waitReview = "waitReview";
	String finish = "finish";
	String delete = "delete";

	/**
	 * ����ID���ض�Ӧ��Order
	 *
	 * @param id
	 * @return
	 */
	Order get(int id);

	/**
	 * �������еĶ���
	 *
	 * @return
	 */
	List<Order> listAll();

	/**
	 * �����û�������״̬����ѯ
	 *
	 * @param user_id
	 * @param excludedStatus
	 * @return
	 */
	List<Order> list(Integer user_id, String excludedStatus);

	/**
	 * ����user_id�µ����ж���
	 *
	 * @param user_id
	 * @return
	 */
	List<Order> listByUserId(Integer user_id);

	/**
	 * ���¶���
	 *
	 * @param order
	 */
	void update(Order order);

	/**
	 * ���Ӷ���
	 *
	 * @param order
	 */
	void add(Order order);

	/**
	 * ���Ӷ���������һ��float���͵���ֵ������ʾ�ö������ܼ�
	 *
	 * @param order
	 * @param orderItems
	 * @return
	 */
	float add(Order order, List<OrderItem> orderItems);
}
