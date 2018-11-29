package view.admin;

import view.admin.EditReviewView;

public class EditReviewController {

    private EditReviewView view;

    public EditReviewController(EditReviewView view)
    {
        this.view = view;
    }

    public EditReviewView getView() {
        return view;
    }
}
