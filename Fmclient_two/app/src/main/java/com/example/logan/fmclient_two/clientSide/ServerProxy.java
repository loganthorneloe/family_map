package com.example.logan.fmclient_two.clientSide;

import facadeClasses.*;

/**
 * Created by logan on 11/14/2017.
 */

public class ServerProxy {

    public ServerProxy(String host, String port){
        client = new HttpClient(host, port);
    }

    HttpClient client;
    Model model = new Model();
    EncodeDecode coder = new EncodeDecode();

    public ResponseRegister register(RequestRegister request) {
        String responder = client.getUrl("/user/register", coder.encodeRegister(request), null);
        return coder.decodetoResponseRegister(responder);
    }

    public ResponseLogin login(RequestLogin request) {
        String responder = client.getUrl("/user/login", coder.encodeRequestLogin(request), null);
        return coder.decodetoResponseLogin(responder);
    }

    //called through task
    public ResponsePeople people(String auth_token) {
        String responder = client.getUrl("/person/", null, auth_token);
        return coder.decodetoResponsePeople(responder);
    }

    public ResponseEvents events(String auth_token) {
        String responder = client.getUrl("/event/", null, auth_token);
        return coder.decodetoResponseEvents(responder);
    }
}
