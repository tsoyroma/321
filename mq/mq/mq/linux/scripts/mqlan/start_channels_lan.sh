#!/bin/sh

MQ_HOME=/opt/mqm/bin

QMGR=M00.DEMO.LAN.SBOLDB
echo "************" $QMGR "**********"

rm "./report_start_chanels_lan.log"

runmqsc "$QMGR" < "./start_chanels_lan.mqsc" >> "./report_start_chanels_lan.log"

echo "**** END ****"
