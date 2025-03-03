package nifi_root_policies

root_policies := {
    "/process-groups": {
        "Projekt1":{
            "read": {
                "users": ["User1", "User2", "User3"],
                "groups": []
            },
            "write": {
                "users": ["User1", "User2", "User3"],
                "groups": []
            },
            "deny": {
                "users": [],
                "groups": []
            }
        },

        "Peter":{
            "read": {
                "users": ["User1"],
                "groups": []
            },
            "write": {
                "users": ["User1"],
                "groups": []
            },
            "deny": {
                "users": [],
                "groups": []
            }
        },

        "Otto":{
            "read": {
                "users": ["User2"],
                "groups": []
            },
            "write": {
                "users": ["User2"],
                "groups": []
            },
            "deny": {
                "users": [],
                "groups": []
            }
        },

        "NiFi Flow":{
            "read": {
                "users": ["User1", "User2"],
                "groups": []
            },
            "write": {
                "users": ["User1", "User2"],
                "groups": []
            },
            "deny": {
                "users": [],
                "groups": []
            }
        }
    },

    "/operation": {
        "Peter":{
            "read": {
                "users": ["User1"],
                "groups": []
            },
            "write": {
                "users": ["User1"],
                "groups": []
            },
            "deny": {
                "users": [],
                "groups": []
            }
        },

        "Otto":{
            "read": {
                "users": ["User2"],
                "groups": []
            },
            "write": {
                "users": ["User2"],
                "groups": []
            },
            "deny": {
                "users": [],
                "groups": []
            }
        }
    },

    "/provenance-data": {
        "Peter":{
            "read": {
                "users": ["User1"],
                "groups": []
            },
            "write": {
                "users": ["User1"],
                "groups": []
            },
            "deny": {
                "users": [],
                "groups": []
            }
        },

        "Otto":{
            "read": {
                "users": ["User2"],
                "groups": []
            },
            "write": {
                "users": ["User2"],
                "groups": []
            },
            "deny": {
                "users": [],
                "groups": []
            }
        }
    },

    "/data" : {
        "Peter":{
            "read": {
                "users": ["User1"],
                "groups": []
            },
            "write": {
                "users": ["User1"],
                "groups": []
            },
            "deny": {
                "users": [],
                "groups": []
            }
        },

        "Otto":{
            "read": {
                "users": ["User2"],
                "groups": []
            },
            "write": {
                "users": ["User2"],
                "groups": []
            },
            "deny": {
                "users": [],
                "groups": []
            }
        }
    },

    "/policies": {
        "Peter":{
            "read": {
                "users": ["User1"],
                "groups": []
            },
            "write": {
                "users": ["User1"],
                "groups": []
            },
            "deny": {
                "users": [],
                "groups": []
            }
        },

        "Otto":{
            "read": {
                "users": ["User2"],
                "groups": []
            },
            "write": {
                "users": ["User2"],
                "groups": []
            },
            "deny": {
                "users": [],
                "groups": []
            }
        }
    },

    "/data-transfer/input-ports": {
        "Peter":{
            "read": {
                "users": ["User1"],
                "groups": []
            },
            "write": {
                "users": ["User1"],
                "groups": []
            },
            "deny": {
                "users": [],
                "groups": []
            }
        },

        "Otto":{
            "read": {
                "users": ["User2"],
                "groups": []
            },
            "write": {
                "users": ["User2"],
                "groups": []
            },
            "deny": {
                "users": [],
                "groups": []
            }
        }
    },

    "/data-transfer/output-ports": {
        "Peter":{
            "read": {
                "users": ["User1"],
                "groups": []
            },
            "write": {
                "users": ["User1"],
                "groups": []
            },
            "deny": {
                "users": [],
                "groups": []
            }
        },

        "Otto":{
            "read": {
                "users": ["User2"],
                "groups": []
            },
            "write": {
                "users": ["User2"],
                "groups": []
            },
            "deny": {
                "users": [],
                "groups": []
            }
        }
    }
}