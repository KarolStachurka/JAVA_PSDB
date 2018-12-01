package psbd;

import psbd.admin.AdminMainController;
import psbd.admin.AdminMainView;
import psbd.client.*;
import psbd.cook.CookMainController;
import psbd.cook.CookMainView;
import psbd.courier.CourierMainController;
import psbd.courier.CourierMainView;
import psbd.manager.ManagerMainController;
import psbd.manager.ManagerMainView;
import psbd.supplier.SupplierMainController;
import psbd.supplier.SupplierMainView;
import psbd.user.RestorePasswordController;
import psbd.user.RestorePasswordView;
import psbd.user.UserLoginController;
import psbd.user.UserLoginView;

// todo tidy up imports

public class MainController {
    private MainWindow view;
    private UserLoginController userLoginController;
    private RestorePasswordController restorePasswordController;
    private ClientMainController clientMainController;
    private CreateClientController createClientController;
    private CreateOrderController createOrderController;
    private CreateReviewController createReviewController;
    private EditClientDataController editClientDataController;
    private CourierMainController courierMainController;
    private CookMainController cookMainController;
    private AdminMainController adminMainController;
    private ManagerMainController managerMainController;
    private SupplierMainController supplierMainController;

    public MainController(MainWindow view)
    {
        this.view = view;

        userLoginController = new UserLoginController(new UserLoginView());
        restorePasswordController = new RestorePasswordController(new RestorePasswordView());
        createClientController = new CreateClientController(new CreateClientView());
        clientMainController = new ClientMainController(new ClientMainView());
        createOrderController = new CreateOrderController(new CreateOrderView());
        createReviewController = new CreateReviewController(new CreateReviewView());
        editClientDataController = new EditClientDataController(new EditClientDataView());
        cookMainController =  new CookMainController(new CookMainView());
        courierMainController = new CourierMainController(new CourierMainView());
        adminMainController = new AdminMainController(new AdminMainView());
        managerMainController = new ManagerMainController(new ManagerMainView());
        supplierMainController = new SupplierMainController(new SupplierMainView());

        initView();
        initConnections();
    }

    // Initialize all psbd panels in application
    private void initView()
    {
        view.addToMainPanel(userLoginController.getView().getWindowPanel(),userLoginController.getView().getWindowName());
        view.addToMainPanel(restorePasswordController.getView().getWindowPanel(),restorePasswordController.getView().getWindowName());
        view.addToMainPanel(createClientController.getView().getWindowPanel(),createClientController.getView().getWindowName());
        view.addToMainPanel(clientMainController.getView().getWindowPanel(), clientMainController.getView().getWindowName());
        view.addToMainPanel(createOrderController.getView().getWindowPanel(),createOrderController.getView().getWindowName());
        view.addToMainPanel(createReviewController.getView().getWindowPanel(),createReviewController.getView().getWindowName());
        view.addToMainPanel(editClientDataController.getView().getWindowPanel(),editClientDataController.getView().getWindowName());
        view.addToMainPanel(cookMainController.getView().getWindowPanel(), cookMainController.getView().getWindowName());
        view.addToMainPanel(courierMainController.getView().getWindowPanel(), courierMainController.getView().getWindowName());
        view.addToMainPanel(adminMainController.getView().getWindowPanel(), adminMainController.getView().getWindowName());
        view.addToMainPanel(managerMainController.getView().getWindowPanel(), managerMainController.getView().getWindowName());
        view.addToMainPanel(supplierMainController.getView().getWindowPanel(), supplierMainController.getView().getWindowName());


        view.setWindowActive(userLoginController.getView().getWindowName());
    }

    // Creates connections between panels in application
    private void initConnections()
    {
        //user login screen buttons
        userLoginController.getView().getForgotPasswordButton().addActionListener(e->view.setWindowActive(restorePasswordController.getView().getWindowName()));
        userLoginController.getView().getCreateAccountButton().addActionListener(e->view.setWindowActive(createClientController.getView().getWindowName()));
        userLoginController.getView().getLoginButton().addActionListener(
                e-> {
                    if(userLoginController.userLogin())
                        switch (userLoginController.userType())
                        {
                            case CLIENTMAIN:
                                view.setWindowActive(clientMainController.getView().getWindowName());
                                break;
                            case COURIERMAIN:
                                view.setWindowActive(courierMainController.getView().getWindowName());
                                break;
                            case COOKMAIN:
                                view.setWindowActive(cookMainController.getView().getWindowName());
                                break;
                            case ADMINMAIN:
                                view.setWindowActive(adminMainController.getView().getWindowName());
                                break;
                            case SUPPLIERMAIN:
                                view.setWindowActive(supplierMainController.getView().getWindowName());
                                break;
                            case MANAGERMAIN:
                                view.setWindowActive(managerMainController.getView().getWindowName());
                                break;
                        }

                    });

        //restore password buttons
        restorePasswordController.getView().getBackButton().addActionListener(e->view.setWindowActive(userLoginController.getView().getWindowName()));
        restorePasswordController.getView().getSendMailButton().addActionListener(e->view.setWindowActive(userLoginController.getView().getWindowName()));

        //create clients buttons
        createClientController.getView().getBackButton().addActionListener(e->view.setWindowActive(userLoginController.getView().getWindowName()));
        createClientController.getView().getConfirmButton().addActionListener(e->view.setWindowActive(userLoginController.getView().getWindowName()));

        // main client page buttons
        clientMainController.getView().getLogoutButton().addActionListener(e->
        {
            clientMainController.logout();
            view.setWindowActive(userLoginController.getView().getWindowName());
        });
        clientMainController.getView().getCreateDishReviewButton().addActionListener(e->view.setWindowActive(createReviewController.getView().getWindowName()));
        clientMainController.getView().getCreateNewOrderButton().addActionListener(e->view.setWindowActive(createOrderController.getView().getWindowName()));
        clientMainController.getView().getEditUserDataButton().addActionListener(e->view.setWindowActive(editClientDataController.getView().getWindowName()));

        // create review buttons
        createReviewController.getView().getBackButton().addActionListener(e->view.setWindowActive(clientMainController.getView().getWindowName()));
        createReviewController.getView().getConfirmButton().addActionListener(e->
        {
            createReviewController.createReview();
            view.setWindowActive(clientMainController.getView().getWindowName());
        });

        // create order buttons
        createOrderController.getView().getBackButton().addActionListener(e->view.setWindowActive(clientMainController.getView().getWindowName()));
        createOrderController.getView().getCompleteOrderButton().addActionListener(e->
        {
            createOrderController.createOrder();
            view.setWindowActive(clientMainController.getView().getWindowName());
        });

        // edit client data buttons
        editClientDataController.getView().getBackButton().addActionListener(e->view.setWindowActive(clientMainController.getView().getWindowName()));
        editClientDataController.getView().getConfirmButton().addActionListener(e->
        {
            editClientDataController.changeData();
            view.setWindowActive(clientMainController.getView().getWindowName());
        });

        // courier main page buttons
        courierMainController.getView().getLogoutButton().addActionListener(e->view.setWindowActive(userLoginController.getView().getWindowName()));

        // cook main page buttons
        cookMainController.getView().getLogoutButton().addActionListener(e->view.setWindowActive(userLoginController.getView().getWindowName()));

        // admin main page buttons
        adminMainController.getView().getLogoutButton().addActionListener(e->view.setWindowActive(userLoginController.getView().getWindowName()));

        //manager main page buttons
        managerMainController.getView().getLogoutButton().addActionListener(e->view.setWindowActive(userLoginController.getView().getWindowName()));

        // supplier main page buttons
        supplierMainController.getView().getLogoutButton().addActionListener(e->view.setWindowActive(userLoginController.getView().getWindowName()));
    }

}
