# DESCRIPTION
# This file contains the permissions for the node components in your flow.
# A node component is every component on Level 3 or higher. So every component
# that is contained in the components on the canvas you see after the login.
# [IMPORTANT] Those permissions are set using the UUID of the component.
# Thus the permissions can only be created if the component is already existing.
#
# The available access policy discriptors can be found here:
# https://nifi.apache.org/docs/nifi-docs/html/administration-guide.html#access-policies

package nifi_node_policies

node_policies := {
    "/processors": {
        "ef5":{
            "read": {
                "users": [],
                "groups": []
            },
            "write": {
                "users": [],
                "groups": []
            },
            "deny": {
                "users": [],
                "groups": ["Group-A"]
            }
        },

        "f3e":{
            "read": {
                "users": ["Carol"],
                "groups": []
            },
            "write": {
                "users": [],
                "groups": ["Group-B"]
            },
            "deny": {
                "users": [],
                "groups": []
            }
        },

        "bb4":{
            "read": {
                "users": [],
                "groups": []
            },
            "write": {
                "users": ["Alice"],
                "groups": []
            },
            "deny": {
                "users": ["Bob"],
                "groups": []
            }
        },
    },

    "/process-groups": {
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