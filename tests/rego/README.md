# Rego Tests
This directory contains rego-tests for the underlying rego functions (i.e. the *_logic.rego´s) used to authorize users and groups in Apache NiFi.

## Test scenario
The test scenario contains four different users
- Alice
- Bob
- Carol
- Mallory
and two groups:
- Group-A
- Group-B

### Global Policies
Testing the global permissions on ``/flow`` using the four users above.

### Component Policies
A test scenario is used to cover the individual test cases. The scenario is already available as policies in the /rego directory at the root of this repository. The corresponding test scenario is shown below in the form of a diagram.It is important to note that permissions are always explicitly specified for each resource, regardless of whether they are explicitly set or inherited. If a permission is not specified, the user does not have it.

<p align="center">
    <img src="../../docs/images/test-scenario.svg" alt="Test Scenario" width="600" />
</p>

The diagram shows a fictitious scenario that fictitious developer E wants to set up. In order to archive those permissions Developer E must implement the following steps for... 
*... the users:*
- Alice, Bob need read and write access in the global policies for /flow and /controller in order to be able to read and write to (potentially) available resources of the flow.
- Carol needs read-only access on the same policies.
- To access the ‘Project 1’ processor group and all underlying resources, Alice needs an entry with the same name in the root rules under ‘/process-groups’.
- In the same way Bob also needs read and write access for his processor-group "Project 2". Both of the permissions above set in the root policies are inherited by all lower component groups until explicitly interrupted.
- In addition to Carol has read access to the component group with the UUID f3e... Since this was not previously inherited by Carol, it must be set in the component policies using the UUID (f3e...).
- Bob permissions are inherited, so nothing needs to be done here for Bob.
- The same like Carol before must be done now for Alice for the processor with the UUID bb4..., only this time, it is a write permission.
- Important now is that Bob should be granted no access to UUID bb4... according to the diagram. This permission can be created by entering Bobs Name in the "deny" list that is available in the UUID entry at the component policies.
- Mallory has no access at all, therefore nothing needs to be done for her at all.

*... the groups:*
- Group-A needs read and write permissions-, Group-B needs write-only permissions in the global policies.
- Group-A needs to be allowed on the processor-group "Project 1" using the name in root policies.
- Group-A needs to be explicitly denied for the processor with the UUID ef5... in the component policies.
- Group-B needs to be explicitly allowed to write for the processor with the UUID f3e... in the component policies.