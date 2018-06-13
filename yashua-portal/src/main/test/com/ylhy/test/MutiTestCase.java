package com.ylhy.test;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ylhy.AgentGatewayApplication;
import com.ylhy.agent.core.service.ITransferCardService;
import com.ylhy.agent.gonghui.service.ILaborUnionService;
import com.ylhy.stress.testing.core.MutiThreadTest;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AgentGatewayApplication.class)// 指定spring-boot的启动类
public class MutiTestCase {
	@Resource
	private ILaborUnionService laborUnionService;
	
	@Resource
	private ITransferCardService transferCardService;
	
	@Test
	public void test() {
		// TODO Auto-generated constructor stub
		MutiThreadTest test = new MutiThreadTest(new MutiLaborUnionRunnable(laborUnionService),200,300l);
		test.start();
	}
	
	@Test
	public void transferCard(){
		MutiThreadTest test = new MutiThreadTest(new MutiTransferCardRunnable(transferCardService),200,100l);
		test.start();
	}
}
