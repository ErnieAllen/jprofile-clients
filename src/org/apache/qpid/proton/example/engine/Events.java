package org.apache.qpid.proton.example.engine;

import org.apache.qpid.proton.engine.Event;

/**
 * Events
 *
 */

public final class Events
{

    private Events() {}

    public static void dispatch(Event event, EventHandler handler) {
        switch (event.getType()) {
        case CONNECTION_INIT:
            handler.onInit(event.getConnection());
            break;
        case CONNECTION_OPEN:
            handler.onOpen(event.getConnection());
            break;
        case CONNECTION_REMOTE_OPEN:
            handler.onRemoteOpen(event.getConnection());
            break;
        case CONNECTION_CLOSE:
            handler.onClose(event.getConnection());
            break;
        case CONNECTION_REMOTE_CLOSE:
            handler.onRemoteClose(event.getConnection());
            break;
        case CONNECTION_FINAL:
            handler.onFinal(event.getConnection());
            break;
        case SESSION_INIT:
            handler.onInit(event.getSession());
            break;
        case SESSION_OPEN:
            handler.onOpen(event.getSession());
            break;
        case SESSION_REMOTE_OPEN:
            handler.onRemoteOpen(event.getSession());
            break;
        case SESSION_CLOSE:
            handler.onClose(event.getSession());
            break;
        case SESSION_REMOTE_CLOSE:
            handler.onRemoteClose(event.getSession());
            break;
        case SESSION_FINAL:
            handler.onFinal(event.getSession());
            break;
        case LINK_INIT:
            handler.onInit(event.getLink());
            break;
        case LINK_OPEN:
            handler.onOpen(event.getLink());
            break;
        case LINK_REMOTE_OPEN:
            handler.onRemoteOpen(event.getLink());
            break;
        case LINK_CLOSE:
            handler.onClose(event.getLink());
            break;
        case LINK_REMOTE_CLOSE:
            handler.onRemoteClose(event.getLink());
            break;
        case LINK_FLOW:
            handler.onFlow(event.getLink());
            break;
        case LINK_FINAL:
            handler.onFinal(event.getLink());
            break;
        case TRANSPORT:
            handler.onTransport(event.getTransport());
            break;
        case DELIVERY:
            handler.onDelivery(event.getDelivery());
            break;
        }
    }

}
