package com.cambridgeassociates.bmc.config;

import org.apache.ignite.IgniteSpringBean;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.ignite.events.EventType;
import org.apache.ignite.marshaller.optimized.OptimizedMarshaller;
import org.apache.ignite.spi.discovery.tcp.TcpDiscoverySpi;
import org.apache.ignite.spi.discovery.tcp.ipfinder.multicast.TcpDiscoveryMulticastIpFinder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;


@Configuration
public class IgniteConfig {

    @Value("#{'${ignite.cfg.tcp.ipfinder.multicast.addresses}'.split(',')}")
    private List<String> ipAddresses;

    @Value("${ignite.cfg.peerClassLoadingEnabled:true}")
    private boolean isPeerClassLoadingEnabled;

    @Value("${ignite.cfg.optimizedMarshaller.requireSerializable:false}")
    private boolean requireSerializable;

    public static final int[] EVENTS = new int[]{
            EventType.EVT_TASK_STARTED, EventType.EVT_TASK_FINISHED, EventType.EVT_TASK_FAILED,
            EventType.EVT_TASK_TIMEDOUT, EventType.EVT_TASK_SESSION_ATTR_SET, EventType.EVT_TASK_REDUCED,
            EventType.EVT_CACHE_OBJECT_PUT, EventType.EVT_CACHE_OBJECT_READ, EventType.EVT_CACHE_OBJECT_REMOVED};

    @Bean
    public IgniteSpringBean ignite() {
        IgniteSpringBean igniteSpringBean = new IgniteSpringBean();

        IgniteConfiguration igniteConfiguration = new IgniteConfiguration();

        OptimizedMarshaller optimizedMarshaller = new OptimizedMarshaller();
        optimizedMarshaller.setRequireSerializable(requireSerializable);

        TcpDiscoveryMulticastIpFinder tcpDiscoveryMulticastIpFinder = new TcpDiscoveryMulticastIpFinder();
        tcpDiscoveryMulticastIpFinder.setAddresses(ipAddresses);

        igniteConfiguration.setPeerClassLoadingEnabled(isPeerClassLoadingEnabled);
        igniteConfiguration.setDiscoverySpi(new TcpDiscoverySpi().setIpFinder(tcpDiscoveryMulticastIpFinder));
        igniteConfiguration.setIncludeEventTypes(EVENTS);
        igniteConfiguration.setMarshaller(optimizedMarshaller);
        igniteSpringBean.setConfiguration(igniteConfiguration);
        return igniteSpringBean;
    }
}
