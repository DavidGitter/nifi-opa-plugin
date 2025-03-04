<h1 align="center"> OPA-Authorizer for Apache NiFi</h1> <br>

> [!IMPORTANT]  
> This plugin is currently under development and should not yet be used in a productive environment.

## Table of Contents

- [Introduction](#introduction)
- [Features](#features)
- [Requirements](#requirements)
- [Quick Start](#quick-start)
- [Testing](#testing)


## Introduction

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)
This repository contains a plugin for Apache NiFi in order to add support for the authorization of access in NiFi using the Open Policy Agent. It also includes suitable Rego Rules with abstraction layers aswell as a test environment to test the authorizer.

## Features
* supports authorization for global access policies 
* supports authorization for componenten access policies
* includes a cache which can be used optionally


## Requirements

### Plugin
For building the plugin in ``/authorizer``.

* [Java 8 SDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
* [Maven](https://maven.apache.org/download.cgi)


### Docker
For using the test environment in ``/test-env``.

* [Docker](https://www.docker.com/get-docker)


## Quick Start

### Building the plugin
To build the plugin go into the ``/authorizer`` folder and execute:
````mvn install````
This builds the ``.nar``-plugin in the ``/target`` folder.

### Using the plugin

In order to use the plugin in Apache NiFi the following steps must be performed:
1. Go to the ``/opt/nifi/nifi-current/conf/authorizers.xml`` in your Apache NiFi Instance / Container
2. Replace the ``managed-authorizer``-entry in the file with the following snippet:
````xml
<!-- Overriding the normal managed-authorizer entry -->
<authorizer>
    <identifier>managed-authorizer</identifier>
    <class>org.nifiopa.nifiopa.OpaAuthorizer</class>
    <property name="CACHE_TIME_SECS">30</property>
    <property name="CACHE_MAX_ENTRY_COUNT">100</property>
    <property name="OPA_URI">http://opa:8181/</property>
</authorizer>
````
3. Place the ``nar``-plugin you build aboth in the ``/opt/nifi/nifi-current/extensions/`` folder of your Apache NiFi Instance / Container
4. Restart Apache NiFi

5. Load the Rego files of the ``/rego`` folder to the OPA and change them according to your needs. Read Chapter [Rego Rules](#rego-rules) for more.

### Properties
The following properties can be configurated in the ``authorizers.xml`` or using the environment variables:

| Property Key | Example | Default | Description |
| --- | --- | --- | --- |
| `OPA_URI` | `http://opa:8181/` |  | Endpoint of the OPA policy to query. **required** |
| `CACHE_TIME_SECS` | `30` | `30` | Maximum time in seconds an entry in the decision cache exists. |
| `CACHE_MAX_ENTRY_COUNT` | `100` | `0` | Maximum entries of the decision cache. |

## Testing

### Setup
In order to use the docker test environment you need open the ``/test-env`` folder and execute the start script.
```bash
$ ./start.sh
```

After Apache NiFi is completly up, open the Apache NiFi UI: ``https://localhost:8443/nifi/``.

You might login using one of those credentials:
- Usernmane: *User1*, Password: *password01*
- Usernmane: *User2*, Password: *password02*
- Usernmane: *User3*, Password: *password03*

> [!NOTE]  
> To load a rebuild plugin you need to restart Apache NiFi.


## Rego Rules

### Input
The following structure shows an example of the JSON-input the opa receives.
````json
{
  "action": { 
    "name": "write" 
  },
  "properties": { 
    "isAccessAttempt": "false", 
    "isAnonymous": "false" 
  },
  "requestedResource": {
    "id": "/process-groups/5c3bea0a-0195-1000-720c-29d246009c2f",
    "name": "NiFi Flow",
    "safeDescription": "Process Group with ID 5c3bea0a-0195-1000-720c-29d246009c2f"
  },
  "resource": {
    "id": "/process-groups/5c3bea0a-0195-1000-720c-29d246009c2f",
    "name": "NiFi Flow",
    "safeDescription": "Process Group with ID 5c3bea0a-0195-1000-720c-29d246009c2f"
  },
  "user": { 
    "groups": "", 
    "name": "User1" 
  },
  "resourceContext": { "": "" },
  "userContext": { "": "" }
}
````
Note that this is only one of many authorization requests that are sent by Apache NiFi when accessing the Web-UI.

All requests that are sent can be seen in the OPA-Container Decision Logs when using the test environment.

### Files
In the ``/rego`` folder the different rules can be found. Apache NiFi has two different types of access rules which can be also found in the [Administrator Guide](https://nifi.apache.org/docs/nifi-docs/html/administration-guide.html#access-policies):
- Global Access Policies (example: "is **User1** allowed to **read** the **UI**)
- Component Access Policies (e.g. "is **User1** allowed to **write** on Processor **xyz**)

The global permissions can be set in the ``/nifi_global_policies.rego``.

The component permissions can be set in the
- ``/nifi_root_policies.rego`` for the first two component levels using the name of a component
- ``/nifi_node_policies.rego`` for all components from level 3 and higher using the UUID of a component

The following image is intended to illustrate the previously explained component authorizations
![alt](/docs/readme/component-logic.svg)

> [!NOTE]  
> This concept may be revised and simplified in the future.
