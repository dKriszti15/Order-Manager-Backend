package edu.bbte.idde.dkim2226.desktop;

import edu.bbte.idde.dkim2226.service.exceptions.NoOrdersFoundException;
import edu.bbte.idde.dkim2226.desktop.view.UserInterface;



public class Main {
    public static void main(String[] args) throws NoOrdersFoundException {
        new UserInterface();
    }
}
