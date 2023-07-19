package io.aelf.utils;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

@Documented
@Retention(value = RetentionPolicy.CLASS)
@Target(value = {ElementType.METHOD})
public @interface AElfUrl {
    /**
     * "wa://" means the peer's url.
     * <p>
     * {@code @example} "wa://api/blockChain/chainStatus" =>
     * "http://www.my-aelf-node.com:8000/api/blockChain/chainStatus"
     */
    String url();
}
