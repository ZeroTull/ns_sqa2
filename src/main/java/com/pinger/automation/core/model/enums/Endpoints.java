package com.pinger.automation.core.model.enums;

import lombok.Getter;

@Getter
public enum Endpoints {
    CLOUDFLARE("1.1.1.1", "Cloudflare DNS", false),
    GOOGLE_DNS("8.8.8.8", "Google DNS", false),
    GOOGLE_DNS_EDGE("8.8.8.8", "Google DNS EDGE", false),
    MY_ROUTER("192.168.0.1", "My Router", false),
    MY_GRANDMA("192.168.0.101", "My Grandma", false),
    MY_SO_LAN("192.168.0.102", "My S/O LAN", true),
    MY_SO_WAN("123.123.123.123", "My S/O WAN", false),
    UNREACHABLE("123.123.123.123", "Unreachable", false);

    private final String address;
    private final String description;
    private final boolean ignore;

    Endpoints(String address, String description, boolean ignore) {
        this.address = address;
        this.description = description;
        this.ignore = ignore;
    }
}
