/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ontogeneticapp;

/**
 *
 * @author wfbarksdale
 */
public class OntogeneticApp {

    public static void main(String[] args) {
        MainWindow appWindow = new MainWindow();
        AppController myApp = new AppController(appWindow);
        appWindow.setVisible(true);
        System.out.println("main finished");
    }
}
