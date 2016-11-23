#!/bin/sh

MQ_HOME=/opt/mqm/bin

QMGR=M00.DEMO.DMZ.SBOLDB
echo "************" $QMGR "**********"

rm "./report_start_listener_dmz.log"

runmqsc "$QMGR" < "./start_listener_dmz.mqsc" >> "./report_start_listener_dmz.log"

echo "**** END ****"
