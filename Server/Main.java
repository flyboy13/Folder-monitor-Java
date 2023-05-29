import connection.Listener;
import view.MainView;

@SuppressWarnings("unused")
public class Main {
    public static void main(String[] args) {
        MainView mainView = new MainView();
        Listener a = new Listener(5000);
    }
}
