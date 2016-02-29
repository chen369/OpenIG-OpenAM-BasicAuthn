/*
 * The contents of this file are subject to the terms of the Common Development and
 * Distribution License (the License). You may not use this file except in compliance with the
 * License.
 *
 * You can obtain a copy of the License at legal/CDDLv1.0.txt. See the License for the
 * specific language governing permission and limitations under the License.
 *
 * When distributing Covered Software, include this CDDL Header Notice in each file and include
 * the License file at legal/CDDLv1.0.txt. If applicable, add the following below the CDDL
 * Header, with the fields enclosed by brackets [] replaced by your own identifying
 * information: "Portions copyright [year] [name of copyright owner]".
 *
 * Copyright 2016 Charan Mann
 */

/*
 * Groovy script for testing OpenAM basic authentication using groovyx.net.http.RESTClient.
 * Refer https://github.com/jgritman/httpbuilder/wiki/RESTClient
 */
import groovyx.net.http.RESTClient

def openAMREST = new RESTClient( 'http://openam13.sample.com:8080/openam/json/' )
def response = openAMREST.post(path : 'authenticate', headers: ['X-OpenAM-Username':'testUser1','X-OpenAM-Password':'passwod'])

assert response.status == 200  // HTTP response code; 404 means not found, etc.
tokenId = response.getData().get("tokenId")
println tokenId

response = openAMREST.post(path : 'sessions/'+ tokenId, query:['_action':'validate'])
isTokenValid = response.getData().get("valid")
println isTokenValid
assert isTokenValid
