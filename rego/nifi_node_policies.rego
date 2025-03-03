package nifi_node_policies

node_policies := {
    "/processors": {
        "88bfd170-0193-1000-f96d-e7b2d9491f59":{
            "read": {
                "users": [],
                "groups": []
            },
            "write": {
                "users": [],
                "groups": []
            },
            "deny": {
                "users": ["User1"],
                "groups": []
            }
        }
    },

    "/process-groups": {
        "917f4d3b-0193-1000-15cd-ede4486f3677":{
            "read": {
                "users": [],
                "groups": []
            },
            "write": {
                "users": [],
                "groups": []
            },
            "deny": {
                "users": ["User1"],
                "groups": []
            }
        },

        "91b2a4e0-0193-1000-e4e4-8a807d160662":{ #User1Private
            "read": {
                "users": [],
                "groups": []
            },
            "write": {
                "users": [],
                "groups": []
            },
            "deny": {
                "users": ["User2"],
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