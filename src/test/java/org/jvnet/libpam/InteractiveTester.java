package org.jvnet.libpam;

import junit.framework.TestCase;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class InteractiveTester extends TestCase {
    public InteractiveTester(String testName) {
        super(testName);
    }

    public void testPositiveCase() throws Exception {
        for (int i=0; i<1000; i++)
            testOne();
    }

    public void testOne() throws Exception {
        UnixUser u = new PAM("sshd").authenticate(System.getProperty("user.name"), System.getProperty("password"));
        if(!printOnce) {
            System.out.println(u.getUID());
            System.out.println(u.getGroups());
            printOnce = true;
        }
    }

    public void testConcurrent() throws Exception {
        ExecutorService es = Executors.newFixedThreadPool(10);
        Set<Future<?>> result = new HashSet<Future<?>>();
        for( int i=0; i<1000; i++ ) {
            result.add(es.submit(new Callable<Object>() {
                public Object call() throws Exception {
                    testOne();
                    return null;
                }
            }));
        }
        // wait for completion
        for (Future<?> f : result) {
            f.get();
        }
        es.shutdown();
    }

    public void testNegative() throws Exception {
        try {
            new PAM("sshd").authenticate("bogus","bogus");
            fail("expected a failure");
        } catch (PAMException e) {
            // yep
        }
    }

    private boolean printOnce;
}
