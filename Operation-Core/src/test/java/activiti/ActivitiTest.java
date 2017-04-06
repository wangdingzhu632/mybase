package activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/***
 * 用户测试类
 */
@ContextConfiguration(locations = {"classpath:activiti.cfg.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class ActivitiTest {
    private final static Log logger = LogFactory.getLog(ActivitiTest.class);

    //流程引擎
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    /**
     *获取流程引擎
     * @throws Exception
     */
    @Test
    public void getProcessEngineTest() throws Exception {

        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource(
                "activiti.cfg.xml").buildProcessEngine();

        logger.info("-------引擎获取成功 ProcessEngine-----");
    }


    /**
     * 流程部署
     * @throws Exception
     */
    @Test
    public void deploymentTest() throws Exception {
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

    /**
     * 启动流程实例
     * @throws Exception
     */
    @Test
    public void startTest() throws Exception {
        // 流程定义的key
        String processDefinitionKey = "HelloWorld";
        ProcessInstance pi = processEngine.getRuntimeService()// 于正在执行的流程实例和执行对象相关的Service
                .startProcessInstanceByKey(processDefinitionKey);// 使用流程定义的key启动流程实例，key对应hellworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
        System.out.println("流程实例ID:" + pi.getId());// 流程实例ID 101
        System.out.println("流程定义ID:" + pi.getProcessDefinitionId()); // 流程定义ID HelloWorld:1:4

    }

    /**
     * 查询当前人的个人任务
     */
    @Test
    public void findMyPersonTask() {
        String assignee = "王五";
        List<Task> list = processEngine.getTaskService()// 与正在执行的认为管理相关的Service
                .createTaskQuery()// 创建任务查询对象
                .taskAssignee(assignee)// 指定个人认为查询，指定办理人
                .list();

        if (list != null && list.size() > 0) {
            for (Task task:list) {
                System.out.println("任务ID:"+task.getId());
                System.out.println("任务名称:"+task.getName());
                System.out.println("任务的创建时间"+task);
                System.out.println("任务的办理人:"+task.getAssignee());
                System.out.println("流程实例ID:"+task.getProcessInstanceId());
                System.out.println("执行对象ID:"+task.getExecutionId());
                System.out.println("流程定义ID:"+task.getProcessDefinitionId());
                System.out.println("#################################");
            }
        }

    }

    /**
     * 完成我的任务
     */
    @Test
    public void completeMyPersonTask(){
        //任务Id
        String taskId="10002";
        processEngine.getTaskService()//与正在执行的认为管理相关的Service
                .complete(taskId);
        System.out.println("完成任务:任务ID:"+taskId);

    }





}
