#!/bin/sh

MQ_HOME=/opt/mqm/bin

QMGR=MKZ.ESB.SBOLRS
echo "************" $QMGR "**********"

crtmqm "$QMGR"

strmqm "$QMGR"

rm "./report_create_objects_sbolrs.log"

runmqsc "$QMGR" < "./create_objects_sbolrs.mqsc" >> "./report_create_objects_sbolrs.log"

echo "**** END ****"
