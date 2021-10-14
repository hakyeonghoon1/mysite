package com.douzone.mysite.mvc.board;

import com.douzone.web.mvc.Action;
import com.douzone.web.mvc.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		if("add".equals(actionName)) {
			action = new AddAction();
		} else if("writeform".equals(actionName)) {
			action = new WriteFormAction();
		} else if("viewform".equals(actionName)) {
			action = new ViewFormAction();
		} else if("updateform".equals(actionName)) {
			action = new UpdateFormAction();
		} else if("update".equals(actionName)) {
			action = new UpdateAction();
		} else {
			action = new ListAction();
		}
		
		return action;
	}

}
