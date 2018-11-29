package view;

import view.client.*;
import view.user.RestorePasswordController;
import view.user.RestorePasswordView;
import view.user.UserLoginController;
import view.user.UserLoginView;

public class MainController {
    private MainWindow view;
    private UserLoginController userLoginController;
    private RestorePasswordController restorePasswordController;
    private ClientMainController clientMainController;
    private CreateClientController createClientController;
    private CreateOrderController createOrderController;
    private CreateReviewController createReviewController;
    private EditClientDataController editClientDataController;

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

        initView();
        initConnections();
    }

    // Initialize all view panels in application
    private void initView()
    {
        view.addToMainPanel(userLoginController.getView().getWindowPanel(),userLoginController.getView().getWindowName());
        view.addToMainPanel(restorePasswordController.getView().getWindowPanel(),restorePasswordController.getView().getWindowName());
        view.addToMainPanel(createClientController.getView().getWindowPanel(),createClientController.getView().getWindowName());
        view.addToMainPanel(clientMainController.getView().getWindowPanel(), clientMainController.getView().getWindowName());
        view.addToMainPanel(createOrderController.getView().getWindowPanel(),createOrderController.getView().getWindowName());
        view.addToMainPanel(createReviewController.getView().getWindowPanel(),createReviewController.getView().getWindowName());
        view.addToMainPanel(editClientDataController.getView().getWindowPanel(),editClientDataController.getView().getWindowName());


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
                        view.setWindowActive(clientMainController.getView().getWindowName());
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
    }

}
