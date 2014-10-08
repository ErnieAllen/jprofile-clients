package org.apache.qpid.proton.example.engine;

import org.apache.qpid.proton.engine.Connection;
import org.apache.qpid.proton.engine.Delivery;
import org.apache.qpid.proton.engine.Link;
import org.apache.qpid.proton.engine.Session;
import org.apache.qpid.proton.engine.Transport;

import org.apache.qpid.proton.engine.Delivery;
import org.apache.qpid.proton.engine.Link;
import org.apache.qpid.proton.engine.Receiver;

/**
 * FlowController
 *
 */

public class FlowController extends AbstractEventHandler
{

    final private int window;

    public FlowController(int window) {
        this.window = window;
    }

    private void topUp(Receiver rcv) {
        int delta = window - rcv.getCredit();
        rcv.flow(delta);
    }

    public void onOpen(Link link) {
        if (link instanceof Receiver) {
            topUp((Receiver) link);
        }
    }

    public void onRemoteOpen(Link link) {
        if (link instanceof Receiver) {
            topUp((Receiver) link);
        }
    }

    public void onLinkFlow(Link link) {
        if (link instanceof Receiver) {
            topUp((Receiver) link);
        }
    }

    public void onDelivery(Delivery delivery) {
        Link link = delivery.getLink();
        if (link instanceof Receiver) {
            topUp((Receiver) link);
        }
    }

}
