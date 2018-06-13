package com.ylhy.test;


import com.ylhy.agent.core.em.EmCardType;
import com.ylhy.agent.core.em.EmChargeType;
import com.ylhy.agent.core.service.ITransferCardService;
import com.ylhy.agent.core.vo.ParamTranscard;
import com.ylhy.commons.exception.I18nMessageException;
import com.ylhy.commons.exception.SuccessMessage;
import com.ylhy.stress.testing.core.MutiTestRunnable;

public class MutiTransferCardRunnable extends MutiTestRunnable{

	private ITransferCardService transferCardService;

	public MutiTransferCardRunnable(ITransferCardService transferCardService) {
		// TODO Auto-generated constructor stub
		this.transferCardService = transferCardService;
	}
	
	@Override
	public Object execute() {
		// TODO Auto-generated method stub
		SuccessMessage sm = new SuccessMessage();
		try{
			ParamTranscard paramTranscard = new ParamTranscard();
			paramTranscard.setServerCode("sichuan_db");
			paramTranscard.setCardNum(1);
			paramTranscard.setUserid(1459008);
			paramTranscard.setCardType(EmCardType.FOUR.getKey());
			paramTranscard.setOperType(EmChargeType.BUY.getKey());
			sm = transferCardService.execute(paramTranscard, 992913151);
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
