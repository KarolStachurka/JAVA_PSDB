package view;

public class MainController {
    private MainWindow view;
    private UserLoginController userLoginController;
    private RestorePasswordController restorePasswordController;
    private CreateClientController createClientController;

    public MainController(MainWindow view)
    {
        this.view = view;
        initView();
        initConnections();
    }

    // Initialize all view panels in application
    private void initView()
    {
        userLoginController = new UserLoginController(new UserLoginView());
        restorePasswordController = new RestorePasswordController(new RestorePasswordView());
        createClientController = new CreateClientController(new CreateClientView());
        view.addToMainPanel(userLoginController.getView().getWindowPanel(),userLoginController.getView().getWindowName());
        view.addToMainPanel(restorePasswordController.getView().getWindowPanel(),restorePasswordController.getView().getWindowName());
        view.addToMainPanel(createClientController.getView().getWindowPanel(),createClientController.getView().getWindowName());
        view.setWindowActive(userLoginController.getView().getWindowName());
    }

    // Creates connections between panels in application
    private void initConnections()
    {
        userLoginController.getView().getForgotPasswordButton().addActionListener(e->view.setWindowActive(restorePasswordController.getView().getWindowName()));
        userLoginController.getView().getCreateAccountButton().addActionListener(e->view.setWindowActive(createClientController.getView().getWindowName()));
        restorePasswordController.getView().getBackButton().addActionListener(e->view.setWindowActive(userLoginController.getView().getWindowName()));
        restorePasswordController.getView().getSendMailButton().addActionListener(e->view.setWindowActive(userLoginController.getView().getWindowName()));
        createClientController.getView().getBackButton().addActionListener(e->view.setWindowActive(userLoginController.getView().getWindowName()));
        createClientController.getView().getConfirmButton().addActionListener(e->view.setWindowActive(userLoginController.getView().getWindowName()));

    }

}
