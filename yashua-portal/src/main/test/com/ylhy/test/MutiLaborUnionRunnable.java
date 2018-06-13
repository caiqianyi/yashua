package com.ylhy.test;


import com.ylhy.agent.gonghui.service.ILaborUnionService;
import com.ylhy.commons.exception.I18nMessageException;
import com.ylhy.commons.exception.SuccessMessage;
import com.ylhy.stress.testing.core.MutiTestRunnable;

public class MutiLaborUnionRunnable extends MutiTestRunnable{

	private ILaborUnionService laborUnionService;

	public MutiLaborUnionRunnable(ILaborUnionService laborUnionService) {
		// TODO Auto-generated constructor stub
		this.laborUnionService = laborUnionService;
	}
	
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		SuccessMessage sm = new SuccessMessage();
		try{
			sm = laborUnionService.rechargeCard(992913151, 27512248, 100);
		}catch(I18nMessageException e){
			//e.printStackTrace();
			sm.setErrcode(e.getCode());
			sm.setErrmsg(e.getInfo());
		}catch(Exception e){
			//e.printStackTrace();
			sm.setErrcode("-1");
			sm.setErrmsg("error");
		}
		return sm;
	}

	@Override
	public int isOk(Object ob) {
		// TODO Auto-generated method stub
		SuccessMessage sm = (SuccessMessage) ob;
		if(sm != null)
			return Integer.parseInt(sm.getErrcode());
		return -2;
	}

}
