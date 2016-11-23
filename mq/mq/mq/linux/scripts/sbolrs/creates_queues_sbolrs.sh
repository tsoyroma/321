#!/bin/sh

MQ_HOME=/opt/mqm/bin

QMGR=MKZ.ESB.SBOLRS
echo "************" $QMGR "**********"

rm "./report_create_queues_sbolrs.log"

runmqsc "$QMGR" < "./create_queues_sbolrs.mqsc" >> "./report_create_queues_sbolrs.log"

echo "**** END ****"
