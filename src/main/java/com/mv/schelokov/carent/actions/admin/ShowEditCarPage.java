package com.mv.schelokov.carent.actions.admin;

import com.mv.schelokov.carent.actions.AbstractAction;
import com.mv.schelokov.carent.actions.JspForward;
import com.mv.schelokov.carent.consts.Jsps;
import com.mv.schelokov.carent.exceptions.ActionException;
import com.mv.schelokov.carent.model.services.CarService;
import com.mv.schelokov.carent.model.services.exceptions.ServiceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;

/**
 *
 * @author Maxim Chshelokov <schelokov.mv@gmail.com>
 */
public class ShowEditCarPage extends AbstractAction {

    private static final Logger LOG = Logger.getLogger(ShowEditUserPage.class);
    private static final String ERROR = "Unable to prepare an edit page for car";

    @Override
    public JspForward execute(HttpServletRequest req, HttpServletResponse res)
            throws ActionException {
        
        JspForward forward = new JspForward();
        
        if (isAdmin(req)) {
            int id = getIntParam(req, "id");
            try {
                CarService carService = CarService.getInstance();

                req.setAttribute("car", carService.getCarById(id));
                
                forward.setUrl(Jsps.ADMIN_EDIT_CAR);
                
                return forward;
            }
            catch (ServiceException ex) {
                LOG.error(ERROR, ex);
                throw new ActionException(ERROR, ex);
            }
        }
        sendForbidden(res);
        return forward;
    }
}