/*
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 *
 */
package org.apache.qpid.proton.example.engine;

import java.util.ArrayList;
import java.util.List;

import org.apache.qpid.proton.engine.Collector;
import org.apache.qpid.proton.engine.Connection;
import org.apache.qpid.proton.engine.Delivery;
import org.apache.qpid.proton.engine.Link;
import org.apache.qpid.proton.engine.Receiver;

/**
 * Small app using the java Engine/Message API.
 * @since 9/30/2014
 */
public class Recv extends AbstractEventHandler {

	   private int count;
	   private int received;

    public Recv(int count) {
        this.count = count;
    }

    @Override
    public void onOpen(Link link) {
        if (link instanceof Receiver) {
            Receiver receiver = (Receiver) link;
            receiver.drain(count);
        }
    }

    @Override
    public void onDelivery(Delivery dlv) {
        if (dlv.getLink() instanceof Receiver) {
        	Receiver receiver = (Receiver) dlv.getLink();

            if (!dlv.isPartial()) {
                //byte[] bytes = new byte[dlv.pending()];
                //receiver.recv(bytes, 0, bytes.length);
                //Message msg = new Message(bytes);

                ++received;
                dlv.settle();
//if (received % 1000 == 0) {
//    System.out.print('*');
//}
            }
        }
    }

    @Override
    public void onRemoteClose(Connection conn) {
    	//long endMS = System.currentTimeMillis();
        //System.out.println("Got last message at: " + endMS);
//System.out.println("\nRemoteClosed");
System.exit(0);
        received = 0;
    }

    public static void main(String[] argv) throws Exception {
        List<String> switches = new ArrayList<String>();
        List<String> args = new ArrayList<String>();
        for (String s : argv) {
            if (s.startsWith("-")) {
                switches.add(s);
            } else {
                args.add(s);
            }
        }


        // Recv localhost 5672 100000
        String host = !args.isEmpty() ? args.remove(0) : "0.0.0.0";
        int port = !args.isEmpty() ? Integer.parseInt(args.remove(0)) : 5673;
        int count = args.isEmpty() ? 1000 : Integer.parseInt(args.remove(0));

        Collector collector = Collector.Factory.create();
        Router router = new Router();
        Driver driver = new Driver(collector, new Handshaker(),
                                   new FlowController(1024), router,
                                   new Recv(count));
        driver.listen(host, port);
        driver.run();
    }

}
