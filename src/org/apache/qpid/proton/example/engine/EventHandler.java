package org.apache.qpid.proton.example.engine;

import org.apache.qpid.proton.engine.Connection;
import org.apache.qpid.proton.engine.Session;
import org.apache.qpid.proton.engine.Link;
import org.apache.qpid.proton.engine.Delivery;
import org.apache.qpid.proton.engine.Transport;
import org.apache.qpid.proton.engine.Event;

/**
 * EventHandler
 *
 */

public interface EventHandler
{

    void onInit(Connection connection);
    void onOpen(Connection connection);
    void onRemoteOpen(Connection connection);
    void onClose(Connection connection);
    void onRemoteClose(Connection connection);
    void onFinal(Connection connection);

    void onInit(Session session);
    void onOpen(Session session);
    void onRemoteOpen(Session session);
    void onClose(Session session);
    void onRemoteClose(Session session);
    void onFinal(Session session);

    void onInit(Link link);
    void onOpen(Link link);
    void onRemoteOpen(Link link);
    void onClose(Link link);
    void onRemoteClose(Link link);
    void onFlow(Link link);
    void onFinal(Link link);

    void onDelivery(Delivery delivery);
    void onTransport(Transport transport);

}
