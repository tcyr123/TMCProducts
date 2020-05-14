package edu.slcc.asdv.utilities;

import edu.slcc.asdv.beans.PurchaseBean;
import javax.el.ELContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class AppHttpSessionListener implements HttpSessionListener {
PurchaseBean purchaseBean;
        @Override
	public void sessionCreated(HttpSessionEvent event) {
		event.getSession().setMaxInactiveInterval(10 * 60); //10 min timeout
                                              ELContext elContext = FacesContext.getCurrentInstance().getELContext();
         purchaseBean  = (PurchaseBean) FacesContext.getCurrentInstance().getApplication().getELResolver().getValue(elContext, null, "purchaseBean");
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		          System.out.println("DESTROYA CALLED");
                            purchaseBean.removeAll();
	}
}
