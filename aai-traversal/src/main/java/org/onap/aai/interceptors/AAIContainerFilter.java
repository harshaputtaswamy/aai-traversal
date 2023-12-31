/**
 * ============LICENSE_START=======================================================
 * org.onap.aai
 * ================================================================================
 * Copyright © 2017-2018 AT&T Intellectual Property. All rights reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 */
package org.onap.aai.interceptors;

import java.util.UUID;

import org.onap.aai.util.FormatDate;

public abstract class AAIContainerFilter {

    protected String genDate() {
        FormatDate fd = new FormatDate("YYMMdd-HH:mm:ss:SSS");
        return fd.getDateTime();
    }

    protected boolean isValidUUID(String transId) {
        try {
            UUID.fromString(transId);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
