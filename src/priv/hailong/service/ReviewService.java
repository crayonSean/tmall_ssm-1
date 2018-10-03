package priv.hailong.service;

import priv.hailong.pojo.Review;

import java.util.List;

public interface ReviewService {

	/**
	 * ����һ������
	 *
	 * @param review
	 */
	void add(Review review);

	/**
	 * ɾ��һ������
	 *
	 * @param id
	 */
	void delete(int id);

	/**
	 * ����һ������
	 *
	 * @param review
	 */
	void update(Review review);

	/**
	 * ͨ��id����һ��Review
	 *
	 * @param id
	 * @return
	 */
	Review get(int id);

	/**
	 * ����product_id�����ص�ǰ��Ʒ�µ���������
	 *
	 * @param product_id
	 * @return
	 */
	List<Review> listByProductId(Integer product_id);

	/**
	 * ���ص�ǰ��Ʒ�����۵�����
	 *
	 * @param product_id
	 * @return
	 */
	int getCount(int product_id);
}
