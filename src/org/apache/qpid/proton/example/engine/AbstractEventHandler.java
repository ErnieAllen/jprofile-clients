package org.apache.qpid.proton.example.engine;

import org.apache.qpid.proton.engine.Connection;
import org.apache.qpid.proton.engine.Session;
import org.apache.qpid.proton.engine.Link;
import org.apache.qpid.proton.engine.Delivery;
import org.apache.qpid.proton.engine.Transport;
import org.apache.qpid.proton.engine.Event;

/**
 * AbstractEventHandler
 *
 */

public class AbstractEventHandler implements EventHandler
{

    public void onInit(Connection connection) {};
    public void onOpen(Connection connection) {};
    public void onRemoteOpen(Connection connection) {};
    public void onClose(Connection connection) {};
    public void onRemoteClose(Connection connection) {};
    public void onFinal(Connection connection) {};

    public void onInit(Session session) {};
    public void onOpen(Session session) {};
    public void onRemoteOpen(Session session) {};
    public void onClose(Session session) {};
    public void onRemoteClose(Session session) {};
    public void onFinal(Session session) {};

    public void onInit(Link link) {};
    public void onOpen(Link link) {};
    public void onRemoteOpen(Link link) {};
    public void onClose(Link link) {};
    public void onRemoteClose(Link link) {};
    public void onFlow(Link link) {};
    public void onFinal(Link link) {};

    public void onDelivery(Delivery delivery) {};
    public void onTransport(Transport transport) {};

}
