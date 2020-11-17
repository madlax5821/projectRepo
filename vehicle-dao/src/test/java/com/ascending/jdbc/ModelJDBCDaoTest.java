package com.ascending.jdbc;

import com.ascending.dao.BrandDao;
import com.ascending.dao.ModelDao;
import com.ascending.init.AppInitializer;
import com.ascending.model.Brand;
import com.ascending.model.Model;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = AppInitializer.class)
public class ModelJDBCDaoTest {
    private Logger logger = LoggerFactory.getLogger(ModelJDBCDaoTest.class);

    @Autowired()
    @Qualifier("ModelJDBCDaoImpl")
    private ModelDao modelDao;

    @Autowired
    @Qualifier("BrandJDBCDaoImpl")
    private BrandDao brandDao;

    private Model testModel;
    private Brand testBrand;

//    @BeforeClass
//    public static void setup(){modelDao = new ModelJDBCDaoImpl();}

    @Before
    public void setTestModel() {
        testBrand = brandDao.getBrandById(1l);
        testModel = new Model();
        testModel.setModelName("testModel");
        modelDao.save(testModel, testBrand);
        testModel = modelDao.getModelByName(testModel.getModelName());
    }
    @After
    public void cleanUp() {
        modelDao.delete(testModel);
    }

    @Test
    public void saveModelTest(){
        Model model = testModel;
        modelDao.save(model,testBrand);
        Assert.assertEquals("model objects comparison",testModel,model);
    }

    @Test
    public void deleteModelTest(){
        boolean ifDelete = modelDao.delete(testModel);
        Assert.assertTrue(ifDelete);
    }

    @Test
    public void updateModelTest() {
        testModel.setModelName("updateTest");
        boolean ifUpdated = modelDao.update(testModel,testBrand);
        Assert.assertTrue("if updated", ifUpdated);
    }

    @Test
    public void getModelsTest(){
        List<Model> models = modelDao.getModels();
        Assert.assertEquals("model amount comparison",5,5);

    }

    @Test
    public void deleteModelByNameTest(){
        boolean ifDelete = modelDao.deleteByName(testModel.getModelName());
        Assert.assertTrue(ifDelete);
    }

    @Test
    public void getModelByIdTest(){
        long id = testModel.getId();
        Model model = modelDao.getModelById(id);
        Assert.assertEquals("model ids comparison",testModel.getId(),model.getId());
    }

    @Test
    public void getModelByNameTest(){
        String name = testModel.getModelName();
        Model model = modelDao.getModelByName(name);
        Assert.assertEquals("model names comparison",testModel.getModelName(),model.getModelName());
    }
}
