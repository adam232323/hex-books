#!/usr/bin/env bash

TIMESTAMP=`date '+%Y%m%d%H%M%S'`

FILE_NAME=$TIMESTAMP"-"$1".xml"
DB_PATH=../src/main/resources/db
FILE_PATH=$DB_PATH/changelog/$FILE_NAME

cp empty_changeset.xml $FILE_PATH

sed -i '' 's/$TIME_STAMP/'$TIMESTAMP'/g' $FILE_PATH
sed -i '' 's/$FILE_NAME/'$FILE_NAME'/g' $FILE_PATH

echo "created new changelog $FILE_NAME"

INCLUDE="    <include file=\"changelog/"$FILE_NAME"\" relativeToChangelogFile=\"true\"/>"

sed -i '' s,'</databaseChangeLog>,'"$INCLUDE"'\
</databaseChangeLog>,g' $DB_PATH/liquibase-changelog.xml

git add $FILE_PATH