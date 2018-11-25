package view;

public abstract  class ViewTemplate {
    private PanelEnum windowName;

    public PanelEnum getWindowName() {
        return windowName;
    }

    public void setWindowName(PanelEnum windowName) {
        this.windowName = windowName;
    }

    abstract public void cleanAll();
}
