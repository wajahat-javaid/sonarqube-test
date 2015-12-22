package com.cambridgeassociates.bmc.analytics.closures;

import org.apache.ignite.lang.IgniteClosure;

import java.util.Map;

public interface CAClosure extends IgniteClosure<Map<String, Object>, Map<String, Object>> {

    Map<String, Object> apply(Map<String, Object> e);
}
