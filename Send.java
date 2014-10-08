

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.qpid.proton.engine.Collector;
import org.apache.qpid.proton.engine.Connection;
import org.apache.qpid.proton.engine.Delivery;
import org.apache.qpid.proton.engine.Link;
import org.apache.qpid.proton.engine.Sender;

import org.apache.qpid.proton.example.engine.AbstractEventHandler;
import org.apache.qpid.proton.example.engine.Driver;
import org.apache.qpid.proton.example.engine.EventHandler;
import org.apache.qpid.proton.example.engine.Events;
import org.apache.qpid.proton.example.engine.FlowController;
import org.apache.qpid.proton.example.engine.Message;
import org.apache.qpid.proton.example.engine.Pool;
import org.apache.qpid.proton.example.engine.Router;


public class Send extends AbstractEventHandler {

    private int count;
    private int sent;
    private int settled;
    private long startMS;
    private Message msg;
    private byte[] bytes;

    public Send(int count) {
        this.count = count;
        this.msg = new Message("Hello World!");
        this.bytes = msg.getBytes();
    }
    @Override
    public void onFlow(Link link) {
        if (link instanceof Sender) {
            Sender sender = (Sender) link;
            if ((sent < count) && sender.getCredit() > 0) {
                Delivery dlv = sender.delivery(String.format("send-%s", sent).getBytes());

            	if (startMS == 0) {
            		startMS = System.currentTimeMillis();
            	}
                sender.send(bytes, 0, bytes.length);
                sender.advance();

                ++sent;
            }
        }
    }

    @Override
    public void onDelivery(Delivery dlv) {
        if (dlv.remotelySettled()) {
            dlv.settle();
            ++settled;
        }

        if (settled >= count) {
    		long endMS = System.currentTimeMillis();
    		System.out.println(String.format("Sent %d messages in %s", settled, formatInterval(endMS - startMS)));
            dlv.getLink().getSession().getConnection().close();
        }
    }

    private static String formatInterval(final long msElapsed)
    {
        final long hr = TimeUnit.MILLISECONDS.toHours(msElapsed);
        final long min = TimeUnit.MILLISECONDS.toMinutes(msElapsed - TimeUnit.HOURS.toMillis(hr));
        final long sec = TimeUnit.MILLISECONDS.toSeconds(msElapsed - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min));
        final long ms = TimeUnit.MILLISECONDS.toMillis(msElapsed - TimeUnit.HOURS.toMillis(hr) - TimeUnit.MINUTES.toMillis(min) - TimeUnit.SECONDS.toMillis(sec));
        return String.format("%02d:%02d:%02d.%03d", hr, min, sec, ms);
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

        // Send 0.0.0.0 100000
        String address = !args.isEmpty() ? args.remove(0) : "0.0.0.0";
        int count = !args.isEmpty() ? Integer.parseInt(args.remove(0)) : 100000;
        
        Collector collector = Collector.Factory.create();

        Send send = new Send(count);

        Driver driver = new Driver(collector, send);

        Pool pool = new Pool(collector);
        pool.outgoing(address, null);

        driver.run();
    }

}
