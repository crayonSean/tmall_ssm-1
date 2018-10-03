package priv.hailong.service;

import priv.hailong.dao.ProductImageMapper;
import priv.hailong.pojo.Product;
import priv.hailong.pojo.ProductImage;
import priv.hailong.pojo.ProductImageExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ProductImageService ʵ����
 *
 * @author: @��û����������
 * @create: 2018-04-28-���� 11:04
 */
@Service
public class ProductImageServiceImpl implements ProductImageService {

	@Autowired
	ProductImageMapper productImageMapper;

	public void add(ProductImage image) {
		productImageMapper.insert(image);
	}

	@Override
	public void delete(Integer id) {
		productImageMapper.deleteByPrimaryKey(id);
	}
	
	public void deleteByProductId(Integer product_id) {

		// ��������ѯ����Ҫɾ�����б�
		ProductImageExample example = new ProductImageExample();
		example.or().andPidEqualTo(product_id);
		List<ProductImage> productImages = list(product_id);

		// ѭ��ɾ��
		for (int i = 0; i < productImages.size(); i++) {
			productImageMapper.deleteByPrimaryKey(productImages.get(i).getId());
		}
	}

	public void update(ProductImage image) {
		productImageMapper.updateByPrimaryKey(image);
	}

	public ProductImage get(Integer id) {
		return productImageMapper.selectByPrimaryKey(id);
	}

	public List<ProductImage> list(Integer product_id) {
		ProductImageExample example = new ProductImageExample();
		example.or().andPidEqualTo(product_id);
		List<ProductImage> productImages = productImageMapper.selectByExample(example);
		return productImages;
	}

	@Override
	public ProductImage getFirst(Integer product_id) {
		ProductImageExample example = new ProductImageExample();
		example.or().andPidEqualTo(product_id);
		List<ProductImage> productImages = productImageMapper.selectByExample(example);
		return productImages.get(0);

	}


	@Override
	public void fill(Product product) {
		
		ProductImageExample example = new ProductImageExample();
		example.or().andPidEqualTo(product.getId());
		List<ProductImage> productImages = productImageMapper.selectByExample(example);
		if(!productImages.isEmpty()){
			product.setFirstProductImage(productImages.get(0));
		}
		
	}

	//��ÿ���д�����ٵ�����֮��,��һ���Բ������ͼƬ����
	@Override
	public void fill(List<Product> products) {
		for(Product product:products){
			fill(product);
		}

		
	}

}
