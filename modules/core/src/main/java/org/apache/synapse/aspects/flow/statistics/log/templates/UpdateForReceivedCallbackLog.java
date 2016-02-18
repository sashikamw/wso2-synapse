/*
 *   Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 *   WSO2 Inc. licenses this file to you under the Apache License,
 *   Version 2.0 (the "License"); you may not use this file except
 *   in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */

package org.apache.synapse.aspects.flow.statistics.log.templates;

import org.apache.synapse.MessageContext;
import org.apache.synapse.SynapseConstants;
import org.apache.synapse.aspects.flow.statistics.collectors.RuntimeStatisticCollector;
import org.apache.synapse.aspects.flow.statistics.log.StatisticReportingLog;
import org.apache.synapse.aspects.flow.statistics.util.StatisticsConstants;
import org.apache.synapse.core.SynapseEnvironment;

public class UpdateForReceivedCallbackLog implements StatisticReportingLog {

	private final String statisticId;
	private String callbackId;
	private Long endTime;
	private SynapseEnvironment synapseEnvironment;
	private Boolean isContinuationCall;
	private boolean isOutOnlyFlow;

	public UpdateForReceivedCallbackLog(MessageContext messageContext, String callbackId, Long endTime) {
		statisticId = (String) messageContext.getProperty(StatisticsConstants.FLOW_STATISTICS_ID);
		boolean isOutOnly = Boolean.parseBoolean(String.valueOf(messageContext.getProperty(SynapseConstants.OUT_ONLY)));
		if (isOutOnly) {
			isOutOnlyFlow = true;
		}
		this.callbackId = callbackId;
		this.endTime = endTime;
		this.synapseEnvironment = messageContext.getEnvironment();
		isContinuationCall = (Boolean) messageContext.getProperty(SynapseConstants.CONTINUATION_CALL);

	}

	@Override public void process() {
		RuntimeStatisticCollector
				.updateForReceivedCallback(statisticId, callbackId, endTime, isContinuationCall, synapseEnvironment,
				                           isOutOnlyFlow);
	}
}
