#!/bin/sh

MQ_HOME=/opt/mqm/bin

QMGR=M00.DEMO.LAN.SBOLDB
echo "************" $QMGR "**********"

crtmqm "$QMGR"

strmqm "$QMGR"

rm "./report_create_objects_lan.log"

runmqsc "$QMGR" < "./create_objects_lan.mqsc" >> "./report_create_objects_lan.log"

echo "**** END ****"
