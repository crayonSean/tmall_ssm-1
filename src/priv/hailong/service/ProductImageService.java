package priv.hailong.service;

import priv.hailong.pojo.Product;
import priv.hailong.pojo.ProductImage;

import java.util.List;

public interface ProductImageService {

	void add(ProductImage image);
	
	void delete(Integer id);

	void deleteByProductId(Integer product_id);

	void update(ProductImage image);

	ProductImage get(Integer id);

	/**
	 * ͨ��pid����ͼƬ����
	 */
	List<ProductImage> list(Integer product_id);
	
	/**
	 * ͨ��pid���ص�һ�Ų�ƷͼƬ
	 */
	ProductImage getFirst(Integer product_id);
	
	/**
	 * Ϊ������Ʒ����һ��ͼƬ
	 */
	void fill(Product product);
	
	/**
	 * Ϊ�����Ʒ������һ��ͼƬ
	 */
	void fill(List<Product> products);
	
}
