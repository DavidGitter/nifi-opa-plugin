# DESCRIPTION
# This file contains the permissions for the global policies
# that can be found in the NiFi Admin Guide:
# https://nifi.apache.org/docs/nifi-docs/html/administration-guide.html#access-policies

package nifi_global_policies

global_policies := {
    "/flow": {
        "users": {
            "Alice": "FULL",
            "Bob": "FULL",
            "Carol": "READ"
        },
        "groups": {
            "Group-A": "FULL",
            "Group-B": "WRITE"
        }
    },
    "/controller": {
        "users": {
            "Alice": "FULL",
            "Bob": "FULL",
            "Carol": "READ"
        },
        "groups": {
            "Group-A": "FULL",
            "Group-B": "WRITE"
        }
    },
    "/parameter-contexts": {
        "users": {},
        "groups": {}
    },
    "/provenance": {
        "users": {},
        "groups": {}
    },
    "/restricted-components": {
        "users": {},
        "groups": {}
    },
    "/policies": {
        "users": {},
        "groups": {}
    },
    "/tenants": {
        "users": {},
        "groups": {}
    },
    "/site-to-site": {
        "users": {},
        "groups": {}
    },
    "/system": {
        "users": {},
        "groups": {}
    },
    "/proxy": {
        "users": {},
        "groups": {}
    },
    "/counters": {
        "users": {},
        "groups": {}
    }
}