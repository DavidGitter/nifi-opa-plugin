# DESCRIPTION
# This file contains the permissions for the root components in your flow.
# A root component is every component on Level 1 (NiFi Flow it self) and
# Level 2 which are all components located directly in the NiFi Flow (those you see
# when you log into the Web-UI).
# [IMPORTANT] Those permissions are set using the future name of the component.
# Thus the permissions can be created before the components even exist.
#
# The available access policy discriptors can be found here:
# https://nifi.apache.org/docs/nifi-docs/html/administration-guide.html#access-policies

package nifi_root_policies

root_policies := {
    "/processors": {
    },

    "/process-groups": {

        "NiFi Flow":{
            "read": {
                "users": ["Alice", "Bob", "Carol"],
                "groups": []
            },
            "write": {
                "users": ["Alice", "Bob"],
                "groups": []
            },
            "deny": {
                "users": [],
                "groups": []
            }
        },

        "Project 1":{
            "read": {
                "users": ["Alice"],
                "groups": ["Group-A"]
            },
            "write": {
                "users": ["Alice"],
                "groups": []
            },
            "deny": {
                "users": [],
                "groups": []
            }
        },

        "Project 2":{
            "read": {
                "users": ["Bob"],
                "groups": []
            },
            "write": {
                "users": ["Bob"],
                "groups": []
            },
            "deny": {
                "users": [],
                "groups": []
            }
        }
    },

    "/operation": {
    },

    "/provenance-data": {
    },

    "/data" : {
    },

    "/policies": {
    },

    "/data-transfer/input-ports": {
    },

    "/data-transfer/output-ports": {
    }
}