#!/bin/sh -x

TEMP=$(mktemp -d)

ZIP=test-1.0-SNAPSHOT.zip

cp ../target/universal/$ZIP $TEMP
cp Dockerfile $TEMP
cp docker-entrypoint.sh $TEMP

cd $TEMP
docker build -t testapp .

cd /
rm -rf $TEMP