package org.jvnet.libpam.impl;

import com.sun.jna.Pointer;
import com.sun.jna.Native;
import com.sun.jna.Library;
import com.sun.jna.Structure;
import com.sun.jna.Memory;
import com.sun.jna.ptr.IntByReference;

/**
 * @author Kohsuke Kawaguchi
 */
public interface CLibrary extends Library {
    /**
     * Comparing http://linux.die.net/man/3/getpwnam
     * and my Mac OS X reveals that the structure of this field isn't very portable.
     * In particular, we cannot read the real name reliably.
     */
    public class passwd extends Structure {
        /**
         * User name.
         */
        public String pw_name;
        /**
         * Encrypted password.
         */
        public String pw_passwd;
        public int pw_uid;
        public int pw_gid;

        // ... there are a lot more fields
    }

    public class group extends Structure {
        public String gr_name;
        // ... the rest of the field is not interesting for us
    }

    Pointer calloc(int count, int size);
    Pointer strdup(String s);
    passwd getpwnam(String username);

    int getgrouplist(String user, int/*gid_t*/ group, Memory groups, IntByReference ngroups);
    group getgrgid(int/*gid_t*/ gid);
    group getgrnam(String name);

    // other user/group related functions that are likely useful
    // see http://www.gnu.org/software/libc/manual/html_node/Users-and-Groups.html#Users-and-Groups


    public static final CLibrary libc = (CLibrary)Native.loadLibrary("c",CLibrary.class);
}
