package org.apache.qpid.proton.example.engine;

import org.apache.qpid.proton.engine.Connection;
import org.apache.qpid.proton.engine.Session;
import org.apache.qpid.proton.engine.Link;
import org.apache.qpid.proton.engine.EndpointState;

/**
 * Handshaker
 *
 */

public class Handshaker extends AbstractEventHandler
{

    public void onRemoteOpen(Connection conn) {
        if (conn.getLocalState() == EndpointState.UNINITIALIZED) {
            conn.open();
        }
    }

    public void onRemoteOpen(Session ssn) {
        if (ssn.getLocalState() == EndpointState.UNINITIALIZED) {
            ssn.open();
        }
    }

    public void onRemoteOpen(Link link) {
        if (link.getLocalState() == EndpointState.UNINITIALIZED) {
            link.setSource(link.getRemoteSource());
            link.setTarget(link.getRemoteTarget());
            link.open();
        }
    }

    public void onRemoteClose(Connection conn) {
        if (conn.getLocalState() != EndpointState.CLOSED) {
            conn.close();
        }
    }

    public void onRemoteClose(Session ssn) {
        if (ssn.getLocalState() != EndpointState.CLOSED) {
            ssn.close();
        }
    }

    public void onRemoteClose(Link link) {
        if (link.getLocalState() != EndpointState.CLOSED) {
            link.close();
        }
    }

}
