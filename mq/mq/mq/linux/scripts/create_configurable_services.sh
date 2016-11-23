#!/bin/sh

mqsicreateconfigurableservice MKZ.ESB.ADP1_BRK -c Aggregation -o A_ADM_OUTPUT -n queuePrefix,timeoutSeconds -v ADM,600 < "./create_objects_dmz.mqsc" >> "./report_create_objects_dmz.log"
mqsicreateconfigurableservice MKZ.ESB.ADP1_BRK -c Aggregation -o A_CRM_INPUT -n queuePrefix,timeoutSeconds -v CRMR,60000 < "./create_objects_dmz.mqsc" >> "./report_create_objects_dmz.log"
mqsicreateconfigurableservice MKZ.ESB.ADP1_BRK -c Aggregation -o A_BILLING_SBOLRS_INPUT -n queuePrefix,timeoutSeconds -v SBOLRS,5 < "./create_objects_dmz.mqsc" >> "./report_create_objects_dmz.log"

echo "**** END ****"
