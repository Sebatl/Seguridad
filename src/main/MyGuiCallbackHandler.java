/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;

public class MyGuiCallbackHandler implements CallbackHandler {

    public MyGuiCallbackHandler() {
    }

    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        throw new UnsupportedOperationException("Not supported yet!!!!"); //To change body of generated methods, choose Tools | Templates.
    }

}
