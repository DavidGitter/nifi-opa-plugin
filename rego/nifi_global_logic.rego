package nifi_glob

import rego.v1
import data.nifi_inp

import data.nifi_global_policies.global_policies

# Global Rules Logic
global_policy_types := [okey | okey := object.keys(global_policies)[_]]
res_is_global_type := nifi_inp.resource_id in global_policy_types
has_key(obj, key) := true if _ = obj[key]
global_policy_user_has_permissions(res_id, user_name, action) := true if {
    has_key(global_policies, res_id)
    has_key(global_policies[res_id]["users"], user_name)
    global_policies[res_id]["users"][user_name] == action
}
global_policy_user_read := true if {
    global_policy_user_has_permissions(
        nifi_inp.inherit_resource_id, 
        nifi_inp.user_name, 
        "READ")
}
global_policy_user_write := true if {
    global_policy_user_has_permissions(
        nifi_inp.inherit_resource_id, 
        nifi_inp.user_name, 
        "WRITE")
}
global_policy_user_full := true if {
    global_policy_user_has_permissions(
        nifi_inp.inherit_resource_id, 
        nifi_inp.user_name, 
        "FULL")
}
global_policy_user_denied := true if {
    global_policy_user_has_permissions(
        nifi_inp.inherit_resource_id, 
        nifi_inp.user_name, 
        "DENY")
}