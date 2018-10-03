package priv.hailong.test;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

/**
 * ���򹤳̲�����
 *
 * @author: @hailong
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration("classpath:spring-mybatis.xml")
public class TestMybatisGenerator {

    public static void main(String[] args) throws Exception {
        List<String> warnings = new ArrayList<String>();
        boolean overwrite = true;
        InputStream is= TestMybatisGenerator.class.getClassLoader().getResource("generatorConfig.xml").openStream();
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(is);
        is.close();
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
        System.out.println("���ɴ���ɹ���ˢ����Ŀ���鿴�ļ�,Ȼ��ִ��TestMybatis.java");
        
    }

//	@Autowired
//	CategoryMapper categoryMapper;
//
//	@Test
//	public void test() {
//		Category category = new Category();
//		category.setName("����1");
//		categoryMapper.insert(category);
//	}


}

