package me.supercube.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.repository.Deployment;

/**
 * Created by wangdz on 2017/3/30.
 */
public class MyActiviti {

    public static void main(String args[]){
        // 工作流引擎的全部配置
        ProcessEngineConfiguration processEngineConfiguration = ProcessEngineConfiguration
                .createStandaloneProcessEngineConfiguration();

        // 链接数据的配置
        processEngineConfiguration.setJdbcDriver("com.mysql.jdbc.Driver");
        processEngineConfiguration.setJdbcUrl("jdbc:mysql://192.168.0.199:3306/activitytest?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=utf8");
        processEngineConfiguration.setJdbcUsername("root");
        processEngineConfiguration.setJdbcPassword("123456");

         /*
     * public static final String DB_SCHEMA_UPDATE_FALSE = "false";
     * 不能自动创建表，需要表存在 public static final String DB_SCHEMA_UPDATE_CREATE_DROP
     * = "create-drop"; 先删除表再创建表 public static final String
     * DB_SCHEMA_UPDATE_TRUE = "true";如果表不存在，自动创建表
     */
        //如果表不存在，自动创建表
       // processEngineConfiguration.setDatabaseSchemaUpdate(processEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        // 工作流的核心对象，ProcessEnginee对象
        ProcessEngine processEngine = processEngineConfiguration
                .buildProcessEngine();
       // System.out.println(processEngine);

        Deployment deployment = processEngine.getRepositoryService()// 与流程定义和部署对象相关的service
                .createDeployment()// 创建一个部署对象
                .name("helloworld入门程序")// 添加部署的名称
                .addClasspathResource("helloworld.bpmn")// classpath的资源中加载，一次只能加载
                // 一个文件
                .addClasspathResource("helloworld.png")// classpath的资源中加载，一次只能加载
                // 一个文件
                .deploy();// 完成部署
        System.out.println("部署ID:" + deployment.getId());
        System.out.println("部署名称：" + deployment.getName());


    }

}
