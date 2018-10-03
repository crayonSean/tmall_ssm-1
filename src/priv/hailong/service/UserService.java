package priv.hailong.service;

import priv.hailong.pojo.User;

import java.util.List;

public interface UserService {

	/**
	 * �������е��û�
	 *
	 * @return
	 */
	List<User> list();

	/**
	 * �����û�����
	 *
	 * @param id
	 * @param password
	 */
	void updatePassword(int id, String password);

	/**
	 * ����id��ȡ�û�
	 *
	 * @param id
	 * @return
	 */
	User get(Integer id);

	/**
	 * �����û�������������ѯ�û�
	 *
	 * @param name
	 * @param password
	 * @return
	 */
	User get(String name, String password);

	/**
	 * �����û����ж��û��Ƿ����
	 *
	 * @param name
	 * @return
	 */
	boolean isExist(String name);

	/**
	 * ����һ���û�����
	 *
	 * @param user
	 */
	void add(User user);
}
