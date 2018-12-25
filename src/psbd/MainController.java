package psbd;

import psbd.admin.*;
import psbd.client.*;
import psbd.cook.*;
import psbd.courier.*;
import psbd.manager.*;
import psbd.supplier.*;
import psbd.user.*;


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
    private CreateAccountController createAccountController;
    private EditReviewController editReviewController;

    private ManagerMainController managerMainController;
    private DiscountManagerController discountManagerController;
    private RecipeManagerController recipeManagerController;

    private SupplierMainController supplierMainController;
    private CreateDeliveryController createDeliveryController;
    private EditStorageController editStorageController;
    private WarehouseStatisticsController warehouseStatisticsController;

    public MainController(MainWindow view)
    {
        this.view = view;

        userLoginController = new UserLoginController(new UserLoginView());
        restorePasswordController = new RestorePasswordController(new RestorePasswordView());


        clientMainController = new ClientMainController(new ClientMainView());
        createClientController = new CreateClientController(new CreateClientView());
        createOrderController = new CreateOrderController(new CreateOrderView());
        createReviewController = new CreateReviewController(new CreateReviewView());
        editClientDataController = new EditClientDataController(new EditClientDataView());

        cookMainController =  new CookMainController(new CookMainView());

        courierMainController = new CourierMainController(new CourierMainView());

        adminMainController = new AdminMainController(new AdminMainView());
        createAccountController = new CreateAccountController(new CreateAccountView());
        editReviewController = new EditReviewController(new EditReviewView());

        managerMainController = new ManagerMainController(new ManagerMainView());
        discountManagerController= new DiscountManagerController(new DiscountManagerView());
        recipeManagerController = new RecipeManagerController(new RecipeManagerView());

        supplierMainController = new SupplierMainController(new SupplierMainView());
        createDeliveryController = new CreateDeliveryController(new CreateDeliveryView());
        editStorageController = new EditStorageController(new EditStorageView());
        warehouseStatisticsController = new WarehouseStatisticsController(new WarehouseStatisticsView());
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
        view.addToMainPanel(createAccountController.getView().getWindowPanel(), createAccountController.getView().getWindowName());
        view.addToMainPanel(editReviewController.getView().getWindowPanel(), editReviewController.getView().getWindowName());

        view.addToMainPanel(managerMainController.getView().getWindowPanel(), managerMainController.getView().getWindowName());
        view.addToMainPanel(discountManagerController.getView().getWindowPanel(), discountManagerController.getView().getWindowName());
        view.addToMainPanel(recipeManagerController.getView().getWindowPanel(), recipeManagerController.getView().getWindowName());

        view.addToMainPanel(supplierMainController.getView().getWindowPanel(), supplierMainController.getView().getWindowName());
        view.addToMainPanel(createDeliveryController.getView().getWindowPanel(), createDeliveryController.getView().getWindowName());
        view.addToMainPanel(editStorageController.getView().getWindowPanel(), editStorageController.getView().getWindowName());
        view.addToMainPanel(warehouseStatisticsController.getView().getWindowPanel(), warehouseStatisticsController.getView().getWindowName());

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
                            case CLIENT:
                                view.setWindowActive(clientMainController.getView().getWindowName());
                                break;
                            case COURIER:
                                view.setWindowActive(courierMainController.getView().getWindowName());
                                break;
                            case COOK:
                                view.setWindowActive(cookMainController.getView().getWindowName());
                                break;
                            case ADMIN:
                                view.setWindowActive(adminMainController.getView().getWindowName());
                                adminMainController.showAllUsers();
                                break;
                            case SUPPLIER:
                                view.setWindowActive(supplierMainController.getView().getWindowName());
                                break;
                            case MANAGER:
                                view.setWindowActive(managerMainController.getView().getWindowName());
                                break;
                        }

                    });

        //restore password buttons
        restorePasswordController.getView().getBackButton().addActionListener(e->view.setWindowActive(userLoginController.getView().getWindowName()));

        //create clients buttons
        createClientController.getView().getBackButton().addActionListener(e->view.setWindowActive(userLoginController.getView().getWindowName()));

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

        // create order buttons
        createOrderController.getView().getBackButton().addActionListener(e->view.setWindowActive(clientMainController.getView().getWindowName()));


        // edit client data buttons
        editClientDataController.getView().getBackButton().addActionListener(e->view.setWindowActive(clientMainController.getView().getWindowName()));

        // courier main page buttons
        courierMainController.getView().getLogoutButton().addActionListener(e->view.setWindowActive(userLoginController.getView().getWindowName()));

        // cook main page buttons
        cookMainController.getView().getLogoutButton().addActionListener(e->view.setWindowActive(userLoginController.getView().getWindowName()));

        // admin main page buttons
        adminMainController.getView().getLogoutButton().addActionListener(e->view.setWindowActive(userLoginController.getView().getWindowName()));
        adminMainController.getView().getAddNewUserButton().addActionListener(e->view.setWindowActive(createAccountController.getView().getWindowName()));
        adminMainController.getView().getEditUserButton().addActionListener(e->{
            view.setWindowActive(createAccountController.getView().getWindowName());

        });
        adminMainController.getView().getEditReviewsButton().addActionListener(e->view.setWindowActive(editReviewController.getView().getWindowName()));
        adminMainController.getView().getEditDiscountsButton().addActionListener(e->{
            view.setWindowActive(discountManagerController.getView().getWindowName());
            discountManagerController.updateTable();
        });

        //admin create account page buttons
        createAccountController.getView().getBackButton().addActionListener(e-> {
                    view.setWindowActive(adminMainController.getView().getWindowName());
                    createAccountController.getView().cleanAll();
                    adminMainController.showAllUsers();
                }
        );

        // edit review page buttons
        editReviewController.getView().getBackButton().addActionListener(e->view.setWindowActive(adminMainController.getView().getWindowName()));
        editReviewController.getView().getRemoveThisUserButton().addActionListener(e->view.setWindowActive(createAccountController.getView().getWindowName()));

        //manager main page buttons
        managerMainController.getView().getLogoutButton().addActionListener(e->view.setWindowActive(userLoginController.getView().getWindowName()));
        managerMainController.getView().getDiscountsControlButton().addActionListener(e->view.setWindowActive(discountManagerController.getView().getWindowName()));
        managerMainController.getView().getRecipesControlButton().addActionListener(e->view.setWindowActive(recipeManagerController.getView().getWindowName()));

        // discount manager page buttons
        discountManagerController.getView().getBackButton().addActionListener(e->view.setWindowActive(adminMainController.getView().getWindowName()));

        //recipe manager page buttons
        recipeManagerController.getView().getBackButton().addActionListener(e->view.setWindowActive(managerMainController.getView().getWindowName()));

        // supplier main page buttons
        supplierMainController.getView().getLogoutButton().addActionListener(e->view.setWindowActive(userLoginController.getView().getWindowName()));
        supplierMainController.getView().getCreateNewDeliveryButton().addActionListener(e->view.setWindowActive(createDeliveryController.getView().getWindowName()));
        supplierMainController.getView().getEditStorageButton().addActionListener(e->view.setWindowActive(editStorageController.getView().getWindowName()));
        supplierMainController.getView().getStatisticsButton().addActionListener(e->view.setWindowActive(warehouseStatisticsController.getView().getWindowName()));

        //create delivery page buttons
        createDeliveryController.getView().getBackButton().addActionListener(e->view.setWindowActive(supplierMainController.getView().getWindowName()));

        //edit storage page buttons
        editStorageController.getView().getBackButton().addActionListener(e->view.setWindowActive(supplierMainController.getView().getWindowName()));

        // warehouse statistics page buttons
        warehouseStatisticsController.getView().getBackButton().addActionListener(e->view.setWindowActive(supplierMainController.getView().getWindowName()));
    }

}