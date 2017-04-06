package activiti;

import org.activiti.engine.ProcessEngine;
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

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.ObjDoubleConsumer;

/***
 * 工单申请测试类
 */
@ContextConfiguration(locations = {"classpath:activiti.cfg.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class OrderApplyTest {
    private final static Log logger = LogFactory.getLog(OrderApplyTest.class);

    //流程引擎
    private ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    /**
     *获取流程引擎
     * @throws Exception
     */
//    @Test
//    public void getProcessEngineTest() throws Exception {
//
//        ProcessEngine processEngine = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource(
//                "activiti.cfg.xml").buildProcessEngine();
//
//        logger.info("-------引擎获取成功 ProcessEngine-----");
//    }


    /**
     * 流程部署
     * @throws Exception
     */
    @Test
    public void deploymentTest() throws Exception {
        InputStream bmpnInputStream = this.getClass().getResourceAsStream("orderApply.bpmn");
        Deployment deployment = processEngine.getRepositoryService()// 与流程定义和部署对象相关的service
                .createDeployment()// 创建一个部署对象
                .name("工单审批")
                .addInputStream("orderApply.bpmn",bmpnInputStream)
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
        String processDefinitionKey = "orderApply";
        Map<String,Object> variableMap = new HashMap<String,Object>();
        variableMap.put("userid","zhangsan");
        ProcessInstance pi = processEngine.getRuntimeService()// 于正在执行的流程实例和执行对象相关的Service
//                .startProcessInstanceByKey(processDefinitionKey)// 使用流程定义的key启动流程实例，key对应hellworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
                  .startProcessInstanceByKey(processDefinitionKey,variableMap); //启动流程的同时设置流程变量

        System.out.println("流程实例ID:" + pi.getId());// 流程实例ID 101
        System.out.println("流程定义ID:" + pi.getProcessDefinitionId()); // 流程定义ID HelloWorld:1:4

    }

    /**
     * 查询当前人的个人任务
     */
    @Test
    public void findMyPersonTask() {
        String assignee = "lisi";
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
        String taskId="52502";
        Map<String,Object> variableMap = new HashMap<String,Object>();
        variableMap.put("operation","yes");
//        variableMap.put("userid","lisi");
        processEngine.getTaskService()//与正在执行的认为管理相关的Service
//                .complete(taskId)
                .complete(taskId,variableMap);
        System.out.println("完成任务:任务ID:"+taskId);

    }





}
