#!/bin/bash

PROFILE=$1

if [ "$PROFILE" = "" ]; then
  echo "Please choose which run profile you want to stop - [dev], prod, test:"
  read PROFILE
fi

if [ "$PROFILE" = "" ] || [ "$PROFILE" = "dev" ]; then
	docker-compose -f dev.yml stop
elif [ "$PROFILE" = "prod" ]; then
  docker-compose -f prod.yml stop
elif [ "$PROFILE" = "test" ]; then
  docker-compose -f test.yml stop
else
  echo "Wrong argument."
fi