package interceptors;

import action.User;
import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import model.UserBean;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: joaosimoes
 * Date: 11/29/13
 * Time: 10:47 AM
 * To change this template use File | Settings | File Templates.
 */
public class AuthenticationInterceptor implements Interceptor {

    private static final long serialVersionUID = -5011962009065225959L;

    @Override
    public void destroy() {
        //release resources here
    }

    @Override
    public void init() {
        System.out.println("Initializing MyLoggingInterceptor...");
    }

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        System.out.println("inside auth interceptor");
        Map<String, Object> sessionAttributes = actionInvocation.getInvocationContext().getSession();

        UserBean user = (UserBean) sessionAttributes.get("user");

        if(user == null) {
            System.out.println("Bean will be created");
            sessionAttributes.put("user", new UserBean());
            return Action.LOGIN;
        }
        else {
            System.out.println("Bean in session");
            /*User action = (User) actionInvocation.getAction();

            int user_id = action.getUser().getUserID();*/

            int user_id = ((UserBean) sessionAttributes.get("user")).getUserID();
            System.out.println(user_id + "\n");
            if(user_id != -1 && user_id != 0) {
                System.out.println("User logged!");
                return actionInvocation.invoke();
            }
            System.out.println("User Not logged!");
            return Action.LOGIN;
        }
    }

}
