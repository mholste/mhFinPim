package de.mho.finpim.service;

import org.eclipse.e4.core.contexts.ContextFunction;
import org.eclipse.e4.core.contexts.ContextInjectionFactory;
import org.eclipse.e4.core.contexts.IEclipseContext;
import org.eclipse.e4.ui.model.application.MApplication;

public class FinPimServiceContextFunction extends ContextFunction{
	
	@Override
	public Object compute(IEclipseContext context, String contextKey) {
		IFinPimService finPimService =
				ContextInjectionFactory.make(FinPimServiceImpl.class, context);
		
		MApplication app = context.get(MApplication.class);
		IEclipseContext appCtx = app.getContext();
		appCtx.set(IFinPimService.class, finPimService);

		return finPimService;
	}

}
