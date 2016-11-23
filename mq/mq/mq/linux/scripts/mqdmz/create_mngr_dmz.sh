#!/bin/sh

MQ_HOME=/opt/mqm/bin

QMGR=M00.DEMO.DMZ.SBOLDB
echo "************" $QMGR "**********"

crtmqm "$QMGR"

strmqm "$QMGR"

rm "./report_create_objects_dmz.log"

runmqsc "$QMGR" < "./create_objects_dmz.mqsc" >> "./report_create_objects_dmz.log"

echo "**** END ****"
